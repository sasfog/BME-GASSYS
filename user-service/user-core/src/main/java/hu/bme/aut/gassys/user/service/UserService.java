package hu.bme.aut.gassys.user.service;

import hu.bme.aut.gassys.user.UserRegistrationDTO;
import hu.bme.aut.gassys.user.data.UserRepository;
import hu.bme.aut.gassys.user.data.UserEntity;
import hu.bme.aut.gassys.user.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import hu.bme.aut.gassys.user.UserDTO;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<UserEntity> findOne(Integer id) {
        return userRepository.findById(id);
    }

    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }

    public UserEntity create(UserRegistrationDTO dto) {
        log.debug("Creating new User {}", dto);

        UserEntity userEntity = new UserEntity();
        userEntity.setBirthdate(dto.getBirthdate());
        userEntity.setEmail(dto.getEmail());
        userEntity.setFirstName(dto.getFirstName());
        userEntity.setLastName(dto.getLastName());
        return userRepository.save(userEntity);
    }

    public void deleteById(Integer id) {
        try {
            log.debug("Deleting user {}", id);
            userRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            log.warn("User with id {} not found.", id);
            throw new UserException();
        }
    }

    public UserEntity modify(Integer id, UserDTO dto) {
        log.debug("Updating user {} with data {}", id, dto);


        UserEntity entity = userRepository.findById(id).get();
        entity.setBirthdate(dto.getBirthdate());
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        return userRepository.save(entity);

    }
}
