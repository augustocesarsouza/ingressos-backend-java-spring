package com.backend.ingresso.application.mappings.MappingClassInterface;

import com.backend.ingresso.application.dto.UserPermissionDTO;
import com.backend.ingresso.application.dto.validations.userValidationDTOs.UserCreateValidatorDTO;
import com.backend.ingresso.domain.entities.User;
import com.backend.ingresso.domain.entities.UserPermission;

import java.util.List;

public interface IUserPermissionMapper {
    UserPermissionDTO userPermissionToUserPermissionDto(UserPermission userPermission);
    UserPermission userPermissionDTOToUserPermission(UserPermissionDTO userPermissionDTO);
    public List<UserPermission> UserPermissionDtoToUserPermission(List<UserPermissionDTO> userPermissionDTO);
    List<UserPermissionDTO> userPermissionToUserPermissionDto(List<UserPermission> userPermission);
}
