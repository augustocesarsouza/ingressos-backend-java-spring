package com.backend.ingresso.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("title")
    private String Title;
    @JsonProperty("description")
    private String Description;
    @JsonProperty("gender")
    private String Gender;
    @JsonProperty("duration")
    private String Duration;
    @JsonProperty("movieRating")
    private Integer MovieRating;
    @JsonProperty("base64Img")
    private String Base64Img;
    @JsonProperty("imgUrl")
    private String ImgUrl;
    @JsonProperty("publicId")
    private String PublicId;
    @JsonProperty("imgUrlBackground")
    private String ImgUrlBackground;
    @JsonProperty("publicIdImgBackground")
    private String PublicIdImgBackground;
    @JsonProperty("statusMovie")
    private String StatusMovie;

    public MovieDTO(UUID id, String title, String description, String gender, String duration, Integer movieRating,
                    String imgUrl, String publicId, String imgUrlBackground, String publicIdImgBackground, String statusMovie) {
        Id = id;
        Title = title;
        Description = description;
        Gender = gender;
        Duration = duration;
        MovieRating = movieRating;
        ImgUrl = imgUrl;
        PublicId = publicId;
        ImgUrlBackground = imgUrlBackground;
        PublicIdImgBackground = publicIdImgBackground;
        StatusMovie = statusMovie;
    }

    public MovieDTO(UUID id, String title, String description, String gender, String duration, Integer movieRating, String imgUrl, String imgUrlBackground) {
        Id = id;
        Title = title;
        Description = description;
        Gender = gender;
        Duration = duration;
        MovieRating = movieRating;
        ImgUrl = imgUrl;
        ImgUrlBackground = imgUrlBackground;
    }

    public MovieDTO(String title, String description, String gender, Integer movieRating) {
        Title = title;
        Description = description;
        Gender = gender;
        MovieRating = movieRating;
    }

    public MovieDTO(UUID id, String title, String imgUrl, Integer movieRating) {
        Id = id;
        Title = title;
        ImgUrl = imgUrl;
        MovieRating = movieRating;
    }

    public MovieDTO(UUID id, String publicId, String publicIdImgBackground) {
        Id = id;
        PublicId = publicId;
        PublicIdImgBackground = publicIdImgBackground;
    }

    public MovieDTO(UUID id) {
        Id = id;
    }

    public void setDataForCreateMovie(UUID id, String title, String description, String gender, String duration,
                                      Integer movieRating, String base64Img, String imgUrl, String publicId,
                                      String imgUrlBackground, String publicIdImgBackground, String statusMovie){
        Id = id;
        Title = title;
        Description = description;
        Gender = gender;
        Duration = duration;
        MovieRating = movieRating;
        Base64Img = base64Img;
        ImgUrl = imgUrl;
        PublicId = publicId;
        ImgUrlBackground = imgUrlBackground;
        PublicIdImgBackground = publicIdImgBackground;
        StatusMovie = statusMovie;
    }

    //m.Id, m.Title, m.ImgUrl, m.MovieRating

    public MovieDTO(UUID id, String title) {
        Id = id;
        Title = title;
    }

    //m.Id, m.Title, m.ImgUrl, m.MovieRating

    public MovieDTO() {
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
}
