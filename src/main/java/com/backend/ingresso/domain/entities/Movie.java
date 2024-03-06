package com.backend.ingresso.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
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
    @Column(name = "movie_rating")
    @JsonProperty("movieRating")
    private Integer MovieRating;
    @Column(name = "img_url")
    @JsonProperty("imgUrl")
    private String ImgUrl;
    @Column(name = "public_id")
    @JsonProperty("publicId")
    private String PublicId;
    @Column(name = "img_url_background")
    @JsonProperty("imgUrlBackground")
    private String ImgUrlBackground;
    @Column(name = "public_id_img_background")
    @JsonProperty("publicIdImgBackground")
    private String PublicIdImgBackground;
    @Column(name = "status_movie")
    @JsonProperty("statusMovie")
    private String StatusMovie;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<MovieTheater> movieTheaters;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<MovieRegionTicketsPurchesed> movieRegionTicketsPurcheseds;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<CinemaMovie> cinemaMovies;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<FormOfPayment> formOfPayments;
    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
    private List<AdditionalFoodMovie> additionalFoodMovies;
    //O parâmetro mappedBy = "movie" especifica que a propriedade movie na classe MovieTheater é responsável por mapear essa relação.

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

//    public List<MovieTheater> getMovieTheaters() {
//        return movieTheaters;
//    }


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

    public void setDataForCreateMovie(UUID id, String title, String description, String gender, String duration,
                                      Integer movieRating, String imgUrl, String publicId,
                                      String imgUrlBackground, String publicIdImgBackground, String statusMovie){
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

    public void setImgUpdate(String imgUrl, String publicId, String imgUrlBackground, String publicIdImgBackground){
        ImgUrl = imgUrl;
        PublicId = publicId;
        ImgUrlBackground = imgUrlBackground;
        PublicIdImgBackground = publicIdImgBackground;
    }
}
