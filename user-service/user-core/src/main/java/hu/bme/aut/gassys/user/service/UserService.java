package hu.bme.aut.gassys.user.service;

import hu.bme.aut.gassys.user.data.UserRepository;
import hu.bme.aut.gassys.user.data.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import hu.bme.aut.gassys.user.UserDTO;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private UserRepository userRepository;

    public Page<UserEntity> findAll(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public Optional<UserEntity> findOne(Integer id) {
        return userRepository.findById(id);
    }

    public UserEntity create(UserDTO dto){
        log.debug("Creating new User {}", dto);

        UserEntity userEntity = new UserEntity();
        userEntity.setBirthdate(dto.getBirthdate());
        userEntity.setEmail(dto.getEmail());
        userEntity.setFirstName(dto.getFirstName());
        userEntity.setLastName(dto.getLastName());
        return userRepository.save(userEntity);
    }

    public void deleteById(Integer id) {
        log.debug("Deleting user {}", id);
        userRepository.deleteById(id);
    }

    public UserEntity modify(Integer id, UserDTO dto) {
        log.debug("Updating user {} with data {}", id, dto);

        Optional<UserEntity> entity = userRepository.findById(id);
        UserEntity userEntity;
        if (entity.isPresent()){
            userEntity = entity.get();
            userEntity.setBirthdate(dto.getBirthdate());
            userEntity.setEmail(dto.getEmail());
            userEntity.setFirstName(dto.getFirstName());
            userEntity.setLastName(dto.getLastName());
            return userRepository.save(userEntity);
        }
        else {
            return create(dto);
        }
    }
}
