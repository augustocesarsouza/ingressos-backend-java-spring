package com.backend.ingresso.application.services;

import com.backend.ingresso.application.dto.UserPermissionDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IUserPermissionMapper;
import com.backend.ingresso.application.services.interfaces.IUserPermissionService;
import com.backend.ingresso.domain.entities.UserPermission;
import com.backend.ingresso.domain.repositories.IUserPermissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserPermissionService implements IUserPermissionService {
    private final IUserPermissionRepository userPermissionRepository;
    private final IUserPermissionMapper userPermissionMapper;

    @Autowired
    public UserPermissionService(IUserPermissionRepository userPermissionRepository, IUserPermissionMapper userPermissionMapper) {
        this.userPermissionRepository = userPermissionRepository;
        this.userPermissionMapper = userPermissionMapper;
    }

    @Override
    @Transactional
    public ResultService<List<UserPermissionDTO>> getAllPermissionUser(UUID idUser) {
        List<UserPermissionDTO> userPermissions = userPermissionRepository.getAllPermissionUser(idUser);
        if(userPermissions == null || userPermissions.isEmpty()){
            return ResultService.Fail("not found");
        }

        return ResultService.Ok(userPermissions);
    }
}
