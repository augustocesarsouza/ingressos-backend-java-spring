package com.backend.ingresso.application.services.interfaces;

import com.backend.ingresso.application.dto.AdditionalInfoUserDTO;
import com.backend.ingresso.application.dto.validations.AdditionalInfoUserValidationDTOs.AdditionalInfoUserCreateDTO;
import com.backend.ingresso.application.services.ResultService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.text.ParseException;

public interface IAdditionalInfoUserService {
    ResultService<AdditionalInfoUserDTO> getInfoUser(String idGuid) throws ParseException;
    ResultService<AdditionalInfoUserDTO> create(@Valid AdditionalInfoUserCreateDTO additionalInfoUserCreateDTO, BindingResult resultValid);
    ResultService<AdditionalInfoUserDTO> update(@Valid AdditionalInfoUserCreateDTO additionalInfoUserCreateDTO, String password);
}
