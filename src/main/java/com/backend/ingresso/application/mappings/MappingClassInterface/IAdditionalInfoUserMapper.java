package com.backend.ingresso.application.mappings.MappingClassInterface;

import com.backend.ingresso.application.dto.AdditionalInfoUserDTO;
import com.backend.ingresso.application.dto.validations.AdditionalInfoUserValidationDTOs.AdditionalInfoUserCreateDTO;
import com.backend.ingresso.domain.entities.AdditionalInfoUser;

public interface IAdditionalInfoUserMapper {
    AdditionalInfoUserDTO additionalInfoUserToadditionalInfoUserDto(AdditionalInfoUser additionalInfoUser);
    AdditionalInfoUser additionalInfoUserDtoToadditionalInfoUser(AdditionalInfoUserDTO additionalInfoUserDTO);
    AdditionalInfoUser additionalInfoUserCreateDtoToadditionalInfoUser(AdditionalInfoUserCreateDTO additionalInfoUserCreateDTO);
    public AdditionalInfoUserCreateDTO additionalInfoUserDtoToAdditionalInfoUserCreateDto(AdditionalInfoUserDTO additionalInfoUserDTO);
}