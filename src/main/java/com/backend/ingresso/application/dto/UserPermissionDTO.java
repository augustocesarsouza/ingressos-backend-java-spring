package com.backend.ingresso.application.dto;

import com.backend.ingresso.domain.entities.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class UserPermissionDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("userId")
    private UUID UserId;
    private UserDTO userDTO;
    @JsonProperty("permissionId")
    private UUID PermissionId;
    private PermissionDTO permissionDTO;

    public UserPermissionDTO(UUID id, UUID userId, UUID permissionId) {
        Id = id;
        UserId = userId;
        PermissionId = permissionId;
    }

    public UserPermissionDTO(UUID id, UUID userId, UUID permissionId, UserDTO userDTO, PermissionDTO permissionDTO) {
        Id = id;
        UserId = userId;
        PermissionId = permissionId;
        this.userDTO = userDTO;
        this.permissionDTO = permissionDTO;
    }

    public UserPermissionDTO(UUID id, UUID userId, PermissionDTO permissionDTO) {
        Id = id;
        UserId = userId;
        this.permissionDTO = permissionDTO;
    }

    public UUID getId() {
        return Id;
    }

    public UUID getUserId() {
        return UserId;
    }

    public UUID getPermissionId() {
        return PermissionId;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public void setUserId(UUID userId) {
        UserId = userId;
    }

    public void setPermissionId(UUID permissionId) {
        PermissionId = permissionId;
    }

    public PermissionDTO getPermissionDTO() {
        return permissionDTO;
    }
}
