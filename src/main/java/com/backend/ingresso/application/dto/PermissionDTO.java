package com.backend.ingresso.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class PermissionDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("visualName")
    private String VisualName;
    @JsonProperty("permissionName")
    private String PermissionName;

    public PermissionDTO(UUID id, String visualName, String permissionName) {
        Id = id;
        VisualName = visualName;
        PermissionName = permissionName;
    }

    public UUID getId() {
        return Id;
    }

    public String getVisualName() {
        return VisualName;
    }

    public String getPermissionName() {
        return PermissionName;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public void setVisualName(String visualName) {
        VisualName = visualName;
    }

    public void setPermissionName(String permissionName) {
        PermissionName = permissionName;
    }
}
