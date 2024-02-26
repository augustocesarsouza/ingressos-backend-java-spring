package com.backend.ingresso.application.dto.validations.userValidationDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class UserPasswordChangeDTO {
    @NotEmpty(message = "passwordCurrent should not be empty")
    @JsonProperty("passwordCurrent")
    private String PasswordCurrent;
    @NotEmpty(message = "newPassword should not be empty")
    @Size(min = 8, message = "NewPassword should have at last 8 characters")
    @JsonProperty("newPassword")
    private String NewPassword;
    @NotEmpty(message = "idGuid should not be empty")
    @Size(min = 36, message = "idGuid should have at last 36 characters")
    @JsonProperty("idGuid")
    private String IdGuid;

    public UserPasswordChangeDTO(String passwordCurrent, String newPassword, String idGuid) {
        PasswordCurrent = passwordCurrent;
        NewPassword = newPassword;
        IdGuid = idGuid;
    }

    public UserPasswordChangeDTO() {
    }

    public String getPasswordCurrent() {
        return PasswordCurrent;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public String getIdGuid() {
        return IdGuid;
    }
}
