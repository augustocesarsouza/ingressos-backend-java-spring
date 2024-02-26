package com.backend.ingresso.application.mappings.MappingClass;

import com.backend.ingresso.application.dto.AdditionalInfoUserDTO;
import com.backend.ingresso.application.dto.validations.AdditionalInfoUserValidationDTOs.AdditionalInfoUserCreateDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IAdditionalInfoUserMapper;
import com.backend.ingresso.domain.entities.AdditionalInfoUser;
import org.springframework.stereotype.Component;

@Component
public class AdditionalInfoUserMapper implements IAdditionalInfoUserMapper {
    @Override
    public AdditionalInfoUserDTO additionalInfoUserToadditionalInfoUserDto(AdditionalInfoUser additionalInfoUser) {
        if(additionalInfoUser == null)
            return null;

        return new AdditionalInfoUserDTO(additionalInfoUser.getId(), additionalInfoUser.getBirthDate(), additionalInfoUser.getGender(),
                additionalInfoUser.getPhone(), additionalInfoUser.getCep(), additionalInfoUser.getLogradouro(), additionalInfoUser.getNumero(),
                additionalInfoUser.getComplemento(), additionalInfoUser.getReferencia(), additionalInfoUser.getBairro(), additionalInfoUser.getEstado(),
                additionalInfoUser.getCidade(), additionalInfoUser.getUserId());
    }

    @Override
    public AdditionalInfoUser additionalInfoUserDtoToadditionalInfoUser(AdditionalInfoUserDTO additionalInfoUserDTO) {
        if(additionalInfoUserDTO == null)
            return null;

        return new AdditionalInfoUser(additionalInfoUserDTO.getId(), additionalInfoUserDTO.getUserId(), additionalInfoUserDTO.getBirthDate(), additionalInfoUserDTO.getGender(),
                additionalInfoUserDTO.getPhone(), additionalInfoUserDTO.getCep(), additionalInfoUserDTO.getLogradouro(), additionalInfoUserDTO.getNumero(),
                additionalInfoUserDTO.getComplemento(), additionalInfoUserDTO.getReferencia(), additionalInfoUserDTO.getBairro(), additionalInfoUserDTO.getEstado(),
                additionalInfoUserDTO.getCidade());
    }
    @Override
    public AdditionalInfoUserCreateDTO additionalInfoUserDtoToAdditionalInfoUserCreateDto(AdditionalInfoUserDTO additionalInfoUserDTO) {
        if(additionalInfoUserDTO == null)
            return null;

        return new AdditionalInfoUserCreateDTO(additionalInfoUserDTO.getId(), additionalInfoUserDTO.getUserId(), additionalInfoUserDTO.getBirthDate(), additionalInfoUserDTO.getGender(),
                additionalInfoUserDTO.getPhone(), additionalInfoUserDTO.getCep(), additionalInfoUserDTO.getLogradouro(), additionalInfoUserDTO.getNumero(),
                additionalInfoUserDTO.getComplemento(), additionalInfoUserDTO.getReferencia(), additionalInfoUserDTO.getBairro(), additionalInfoUserDTO.getEstado(),
                additionalInfoUserDTO.getCidade());
    }

    @Override
    public AdditionalInfoUser additionalInfoUserCreateDtoToadditionalInfoUser(AdditionalInfoUserCreateDTO additionalInfoUserCreateDTO) {
        if(additionalInfoUserCreateDTO == null)
            return null;

        return new AdditionalInfoUser(additionalInfoUserCreateDTO.getId(), additionalInfoUserCreateDTO.getUserId(), additionalInfoUserCreateDTO.getBirthDate(), additionalInfoUserCreateDTO.getGender(),
                additionalInfoUserCreateDTO.getPhone(), additionalInfoUserCreateDTO.getCep(), additionalInfoUserCreateDTO.getLogradouro(), additionalInfoUserCreateDTO.getNumero(),
                additionalInfoUserCreateDTO.getComplemento(), additionalInfoUserCreateDTO.getReferencia(), additionalInfoUserCreateDTO.getBairro(), additionalInfoUserCreateDTO.getEstado(),
                additionalInfoUserCreateDTO.getCidade());
    }
}
