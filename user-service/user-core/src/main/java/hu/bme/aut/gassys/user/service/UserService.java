package hu.bme.aut.gassys.user.service;

import feign.FeignException;
import hu.bme.aut.gassys.event.EventServiceIF;
import hu.bme.aut.gassys.user.UserRegistrationDTO;
import hu.bme.aut.gassys.user.data.UserRepository;
import hu.bme.aut.gassys.user.data.UserEntity;
import hu.bme.aut.gassys.user.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import hu.bme.aut.gassys.user.UserDTO;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final EventServiceIF eventServiceClient;

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
        log.debug("Deleting user {}", id);
        if (userRepository.existsById(id)) {
            try {
                eventServiceClient.deleteEventByOrganiserId(id);
                userRepository.deleteById(id);
            } catch (FeignException e) {
                e.printStackTrace();
                log.error("Error during user deletion.");
                log.error("{}", e.getMessage());
                throw new UserException();
            }
        }
        else {
            log.warn("User with id {} not found.", id);
            throw new UserException();
        }
    }

    public UserEntity modify(Integer id, UserDTO dto) {
        log.debug("Updating user {} with data {}", id, dto);


        UserEntity entity = userRepository.findById(id).orElseThrow(UserException::new);
        entity.setBirthdate(dto.getBirthdate());
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        return userRepository.save(entity);

    }
}
