package com.backend.ingresso.application.dto.validations.theatreDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

public class TheatreUpdateValidatorDTO {
    @NotEmpty(message = "id should not be empty")
    @JsonProperty("id")
    private String Id;
    @NotEmpty(message = "base64Img should not be empty")
    @JsonProperty("base64Img")
    private String Base64Img;

    public TheatreUpdateValidatorDTO(String id, String base64Img) {
        Id = id;
        Base64Img = base64Img;
    }

    public TheatreUpdateValidatorDTO() {
    }

    public String getId() {
        return Id;
    }

    public String getBase64Img() {
        return Base64Img;
    }
}
