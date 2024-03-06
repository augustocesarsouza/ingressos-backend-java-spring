package com.backend.ingresso.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("visualName")
    private String VisualName;
    @JsonProperty("permissionName")
    private String PermissionName;

    //    SELECT up.Id, up.UserId, ps.VisualName, ps.PermissionName

    public PermissionDTO(UUID id, String visualName, String permissionName) {
        Id = id;
        VisualName = visualName;
        PermissionName = permissionName;
    }

    public PermissionDTO(String visualName, String permissionName) {
        VisualName = visualName;
        PermissionName = permissionName;
    }

    public PermissionDTO() {
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
