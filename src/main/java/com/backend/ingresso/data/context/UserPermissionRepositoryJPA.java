package com.backend.ingresso.data.context;

import com.backend.ingresso.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserPermissionRepositoryJPA extends JpaRepository<User, UUID> {
    @Query("SELECT up.Id, up.UserId, ps.VisualName, ps.PermissionName FROM UserPermission AS up INNER JOIN Permission AS ps ON up.PermissionId = ps.Id WHERE up.UserId = :idUser")
    //@Query(value = queryPass, nativeQuery = true)
    List<Object[]> getAllPermissionUser(UUID idUser);
}