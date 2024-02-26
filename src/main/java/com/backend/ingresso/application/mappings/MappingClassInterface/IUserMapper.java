package com.backend.ingresso.application.mappings.MappingClassInterface;

import com.backend.ingresso.application.dto.UserDTO;
import com.backend.ingresso.application.dto.validations.userValidationDTOs.UserCreateValidatorDTO;
import com.backend.ingresso.domain.entities.User;

public interface IUserMapper {
    public UserDTO userToUserDto(User user);
    public User userDtoToUser(UserDTO userDTO);
    UserCreateValidatorDTO userToUserCreateValidatorDto(User user);
    User userCreateValidatorDtoToUser(UserCreateValidatorDTO userCreateValidatorDTO);

}
