package com.backend.ingresso.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tb_movies", schema = "public")
public class Movie {
    @Id
    @Column(name = "movie_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "title")
    @JsonProperty("title")
    private String Title;
    @Column(name = "description")
    @JsonProperty("description")
    private String Description;
    @Column(name = "gender")
    @JsonProperty("gender")
    private String Gender;
    @Column(name = "duration")
    @JsonProperty("duration")
    private String Duration;
    @Column(name = "movieRating")
    @JsonProperty("movieRating")
    private Integer MovieRating;
    @Column(name = "imgUrl")
    @JsonProperty("imgUrl")
    private String ImgUrl;
    @Column(name = "publicId")
    @JsonProperty("publicId")
    private String PublicId;
    @Column(name = "imgUrlBackground")
    @JsonProperty("imgUrlBackground")
    private String ImgUrlBackground;
    @Column(name = "publicIdImgBackground")
    @JsonProperty("publicIdImgBackground")
    private String PublicIdImgBackground;
    @Column(name = "statusMovie")
    @JsonProperty("statusMovie")
    private String StatusMovie;

    public Movie(UUID id, String title, String description, String gender, String duration, Integer movieRating,
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

    public Movie() {
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

    public void setMovieUpdate(UUID id, String title, String description, String gender, String duration, Integer movieRating,
                               String imgUrl, String publicId, String imgUrlBackground, String publicIdImgBackground, String statusMovie){
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
}
