package com.backend.ingresso.application.dto.validateErrosDTOs;

import com.backend.ingresso.application.ErrorValidation;
import org.springframework.validation.ObjectError;

import java.util.List;

public interface IValidateErrorsDTO {
    List<ErrorValidation> ValidateDTO(List<ObjectError> errorsDTO);
}
