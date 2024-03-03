package com.backend.ingresso.application.mappings.MappingClass;

import com.backend.ingresso.application.dto.PermissionDTO;
import com.backend.ingresso.application.dto.UserPermissionDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IUserPermissionMapper;
import com.backend.ingresso.domain.entities.Permission;
import com.backend.ingresso.domain.entities.UserPermission;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserPermissionMapper implements IUserPermissionMapper {
    @Override
    public UserPermissionDTO userPermissionToUserPermissionDto(UserPermission userPermission) {
        return null;
    }

    @Override
    public UserPermission userPermissionDTOToUserPermission(UserPermissionDTO userPermissionDTO) {
        return null;
    }

    public List<UserPermission> UserPermissionDtoToUserPermission(List<UserPermissionDTO> userPermissionDTO) {
        if(userPermissionDTO == null)
            return null;

        List<UserPermission> listUserPermission = new ArrayList<>();

        userPermissionDTO.forEach((el) -> {
            Permission permission = new Permission(null, el.getPermissionDTO().getVisualName(), el.getPermissionDTO().getPermissionName());
            UserPermission userPermissionNew;
            if(el.getPermissionDTO() != null){
                userPermissionNew = new UserPermission(el.getId(), el.getUserId(), el.getPermissionId(), permission);
            }else {
                userPermissionNew = new UserPermission(el.getId(), el.getUserId(), el.getPermissionId(), null);
            }
            listUserPermission.add(userPermissionNew);
        });

        return listUserPermission;
    }

    @Override
    public List<UserPermissionDTO> userPermissionToUserPermissionDto(List<UserPermission> userPermission) {
        if(userPermission == null)
            return null;

        List<UserPermissionDTO> listUserPermissionDto = new ArrayList<>();

        userPermission.forEach((el) -> {
            PermissionDTO permissionDTO = new PermissionDTO(null, el.getPermission().getVisualName(), el.getPermission().getPermissionName());
            UserPermissionDTO userPermissionNew;
            if(el.getPermission() != null){
                userPermissionNew = new UserPermissionDTO(el.getId(), el.getUserId(), null, el.getPermissionId(), permissionDTO);
            }else {
                userPermissionNew = new UserPermissionDTO(el.getId(), el.getUserId(), null, el.getPermissionId(), null);
            }
            listUserPermissionDto.add(userPermissionNew);
        });

        return listUserPermissionDto;
    }
}
