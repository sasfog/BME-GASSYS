package hu.bme.aut.gassys.user.presentation;


import hu.bme.aut.gassys.user.UserRegistrationDTO;
import hu.bme.aut.gassys.user.data.UserEntity;
import hu.bme.aut.gassys.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import hu.bme.aut.gassys.user.UserDTO;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@Slf4j
@AllArgsConstructor
public class UserController {


    private UserService userService;

    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable).map(userMapper::userToUserDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findOneUser(@PathVariable Integer id){
        return userService.findOne(id)
                .map(userEntity -> ResponseEntity.ok(userMapper.userToUserDTO(userEntity)))
                .orElseGet( () -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO, UriComponentsBuilder builder){
        try{
            UserEntity userEntity = userService.create(userRegistrationDTO);

            UriComponents uriComponents = builder.path("/api/user/{id}").buildAndExpand(userEntity.getId());
            return ResponseEntity.created(uriComponents.toUri()).body(userMapper.userToUserDTO(userEntity));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> modifyUser(@PathVariable Integer id, @RequestBody UserDTO userDTO, UriComponentsBuilder builder){
        try {


            UserEntity userEntity;
            if (!userService.existsById(id)){
                userEntity = userService.create(userMapper.userDTOToUserRegistrationDTO(userDTO));
                UriComponents uriComponents = builder.path("/api/user/{id}").buildAndExpand(userEntity.getId());
                log.debug("Created entity : {}", userEntity);
                return ResponseEntity.created(uriComponents.toUri()).body(userMapper.userToUserDTO(userEntity));
            }
            else {
                userEntity = userService.modify(id, userDTO);
                UriComponents uriComponents = builder.path("/api/user/{id}").buildAndExpand(userEntity.getId());
                log.debug("Updated entity: {}", userEntity);
                return ResponseEntity.ok(userMapper.userToUserDTO(userEntity));
            }
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer id) {
            userService.deleteById(id);
            return ResponseEntity.ok(HttpStatus.OK);
    }

}
