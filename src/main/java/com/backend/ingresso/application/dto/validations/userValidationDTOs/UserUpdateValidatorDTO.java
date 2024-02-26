package com.backend.ingresso.application.dto.validations.userValidationDTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserUpdateValidatorDTO {
    @NotEmpty(message = "id should not be empty")
    @Size(min = 36, message = "id should have at last 36 characters")
    @JsonProperty("id")
    private String Id;
    @NotEmpty(message = "name should not be empty")
    @Size(min = 1, message = "name should have at last 1 characters")
    @JsonProperty("name")
    private String Name;

    public UserUpdateValidatorDTO(String id, String name) {
        Id = id;
        Name = name;
    }

    private UUID UUIDUser;

    public String getName() {
        return Name;
    }

    public String getId() {
        return Id;
    }

    public UUID getUUIDUser() {
        return UUIDUser;
    }

    public void setUUIDUser(UUID UUIDUser) {
        this.UUIDUser = UUIDUser;
    }
}
