package com.backend.ingresso.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_user_permissions", schema = "public")
public class UserPermission {
    @Id
    @Column(name = "user_permission_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "user_id", insertable = false, updatable = false)
    @JsonProperty("userId")
    private UUID UserId;
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonProperty("user")
    private User User;
    @Column(name = "permission_id", insertable = false, updatable = false)
    @JsonProperty("permissionId")
    private UUID PermissionId;
    @OneToOne
    @JoinColumn(name = "permission_id")
    @JsonProperty("permission")
    private Permission Permission;

    public UserPermission(UUID id, UUID userId, UUID permissionId, Permission permission) {
        Id = id;
        UserId = userId;
        PermissionId = permissionId;
        Permission = permission;
    }

    public UserPermission(UUID id, UUID userId, User user, UUID permissionId, Permission permission) {
        Id = id;
        UserId = userId;
        User = user;
        PermissionId = permissionId;
        Permission = permission;
    }

    public UserPermission() {
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

    public Permission getPermission() {
        return Permission;
    }
}
