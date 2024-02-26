package com.backend.ingresso.application.services.interfaces;

import com.backend.ingresso.application.dto.UserDTO;
import com.backend.ingresso.application.dto.validations.userValidationDTOs.UserCreateValidatorDTO;
import com.backend.ingresso.application.dto.validations.userValidationDTOs.UserPasswordChangeDTO;
import com.backend.ingresso.application.dto.validations.userValidationDTOs.UserUpdateValidatorDTO;
import com.backend.ingresso.application.services.ResultService;
import org.springframework.validation.BindingResult;

public interface IUserManagementService {
    ResultService<UserCreateValidatorDTO> create(UserCreateValidatorDTO userCreateValidatorDTO, BindingResult result);
    public ResultService<UserDTO> update(UserUpdateValidatorDTO userUpdateValidatorDTO, BindingResult resultValid, String password);
    public ResultService<UserDTO> updateUserPassword(UserPasswordChangeDTO userPasswordChangeDTO, BindingResult resultValid);
}
