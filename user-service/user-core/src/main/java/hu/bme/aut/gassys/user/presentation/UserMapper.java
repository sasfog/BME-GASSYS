package hu.bme.aut.gassys.user.presentation;

import hu.bme.aut.gassys.user.data.UserEntity;
import org.mapstruct.Mapper;
import hu.bme.aut.gassys.user.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO userToUserDTO(UserEntity userEntity);
}
