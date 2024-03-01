package com.backend.ingresso.application.dto.validations.movieValidationDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public class MovieCreateValidatorDTO {
    private UUID Id;
    @NotEmpty(message = "title should not be empty")
    @JsonProperty("title")
    private String Title;
    @NotEmpty(message = "description should not be empty")
    @JsonProperty("description")
    private String Description;
    @NotEmpty(message = "gender should not be empty")
    @JsonProperty("gender")
    private String Gender;
    @NotEmpty(message = "duration should not be empty")
    @JsonProperty("duration")
    private String Duration;
    @JsonProperty("movieRating")
    private Integer MovieRating;
    @NotEmpty(message = "base64Img should not be empty")
    @JsonProperty("base64Img")
    private String Base64Img;
    private String ImgUrl;
    private String PublicId;
    private String ImgUrlBackground;
    private String PublicIdImgBackground;
    @JsonProperty("statusMovie")
    private String StatusMovie;

    public MovieCreateValidatorDTO(String title, String description, String gender,
                                   String duration, Integer movieRating,
                                   String base64Img, String statusMovie) {
        Title = title;
        Description = description;
        Gender = gender;
        Duration = duration;
        MovieRating = movieRating;
        Base64Img = base64Img;
        StatusMovie = statusMovie;
    }

    public UUID getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getGender() {
        return Gender;
    }

    public String getDuration() {
        return Duration;
    }

    public Integer getMovieRating() {
        return MovieRating;
    }

    public String getBase64Img() {
        return Base64Img;
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

    public String getStatusMovie() {
        return StatusMovie;
    }

    public void setId(UUID id) {
        Id = id;
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
