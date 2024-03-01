package com.backend.ingresso.application.dto.validations.movieValidationDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class MovieUpdateValidatorDTO {
    @NotNull(message = "Id should not be null")
    @NotEmpty(message = "Id should not be empty")
    @Size(min = 36, message = "Id should not be empty")
    @JsonProperty("id")
    private String Id;
    @NotEmpty(message = "base64Img should not be empty")
    @JsonProperty("base64Img")
    private String Base64Img;
    private String ImgUrl;
    private String PublicId;
    private String ImgUrlBackground;
    private String PublicIdImgBackground;

    public MovieUpdateValidatorDTO(String id, String base64Img, String imgUrl,
                                   String publicId, String imgUrlBackground,
                                   String publicIdImgBackground) {
        Id = id;
        Base64Img = base64Img;
        ImgUrl = imgUrl;
        PublicId = publicId;
        ImgUrlBackground = imgUrlBackground;
        PublicIdImgBackground = publicIdImgBackground;
    }

    public String getId() {
        return Id;
    }

    public String getBase64Img() {
        return Base64Img;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public String getPublicId() {
        return PublicId;
    }

    public String getImgUrlBackground() {
        return ImgUrlBackground;
    }

    public String getPublicIdImgBackground() {
        return PublicIdImgBackground;
    }

    public void setBase64Img(String base64Img) {
        Base64Img = base64Img;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public void setPublicId(String publicId) {
        PublicId = publicId;
    }

    public void setImgUrlBackground(String imgUrlBackground) {
        ImgUrlBackground = imgUrlBackground;
    }

    public void setPublicIdImgBackground(String publicIdImgBackground) {
        PublicIdImgBackground = publicIdImgBackground;
    }
}
