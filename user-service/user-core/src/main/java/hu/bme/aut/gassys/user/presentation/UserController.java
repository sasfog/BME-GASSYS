package hu.bme.aut.gassys.user.presentation;


import hu.bme.aut.gassys.user.data.UserEntity;
import hu.bme.aut.gassys.user.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import hu.bme.aut.gassys.user.UserDTO;

@RestController
@RequestMapping("/api/user")
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
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO, UriComponentsBuilder builder){
        try{
            UserEntity userEntity = userService.create(userDTO);

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
            UserEntity userEntity = userService.modify(id, userDTO);

            UriComponents uriComponents = builder.path("/api/user/{id}").buildAndExpand(userEntity.getId());
            // Handle creation AND modification with different codes
            return ResponseEntity.created(uriComponents.toUri()).body(userMapper.userToUserDTO(userEntity));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer id) {
            userService.deleteById(id);
            return ResponseEntity.ok(HttpStatus.OK);
    }

}
