package com.backend.ingresso.application.mappings.MappingClass;

import com.backend.ingresso.application.dto.UserDTO;
import com.backend.ingresso.application.dto.validations.userValidationDTOs.UserCreateValidatorDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IUserMapper;
import com.backend.ingresso.domain.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements IUserMapper {
    @Override
    public UserDTO userToUserDto(User user) {
        if(user == null)
            return null;

        return new UserDTO(user.getId(),user.getName(), user.getEmail(), user.getCpf(), null, user.getConfirmEmail());
    }
    @Override
    public User userDtoToUser(UserDTO userDTO) {
        if(userDTO == null)
            return null;

        return new User(userDTO.getId(),userDTO.getName(), userDTO.getEmail(), userDTO.getCpf(), null, userDTO.getConfirmEmail());
    }
    @Override
    public UserCreateValidatorDTO userToUserCreateValidatorDto(User user) {
        if(user == null)
            return null;

        return new UserCreateValidatorDTO(user.getName(), user.getEmail(), user.getCpf(), null, null, user.getConfirmEmail());
    }

    @Override
    public User userCreateValidatorDtoToUser(UserCreateValidatorDTO userCreateValidatorDTO) {
        if(userCreateValidatorDTO == null)
            return null;

        return new User(userCreateValidatorDTO.getId(), userCreateValidatorDTO.getName(), userCreateValidatorDTO.getEmail(), userCreateValidatorDTO.getCpf(),
                userCreateValidatorDTO.getPasswordHash(), userCreateValidatorDTO.getConfirmEmail());
    }
}
