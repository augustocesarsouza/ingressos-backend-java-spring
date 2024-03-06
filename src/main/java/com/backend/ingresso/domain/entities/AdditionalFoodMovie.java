package com.backend.ingresso.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_additional_food_movies", schema = "public")
public class AdditionalFoodMovie {
    @Id
    @Column(name = "additional_food_movie_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "title")
    @JsonProperty("title")
    private String Title;
    @Column(name = "price")
    @JsonProperty("price")
    private String Price;
    @Column(name = "fee")
    @JsonProperty("fee")
    private String Fee;
    @Column(name = "img_url")
    @JsonProperty("imgUrl")
    private String ImgUrl;
    @Column(name = "public_id")
    @JsonProperty("publicId")
    private String PublicId;
    @Column(name = "movie_id")
    @JsonProperty("movieId")
    private UUID MovieId;
    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private Movie movie;

    public AdditionalFoodMovie(UUID id, String title, String price, String fee, String imgUrl, String publicId, UUID movieId, Movie movie) {
        Id = id;
        Title = title;
        Price = price;
        Fee = fee;
        ImgUrl = imgUrl;
        PublicId = publicId;
        MovieId = movieId;
        this.movie = movie;
    }

    public AdditionalFoodMovie() {
    }

    public UUID getId() {
        return Id;
    }

    public String getTitle() {
        return Title;
    }

    public String getPrice() {
        return Price;
    }

    public String getFee() {
        return Fee;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public String getPublicId() {
        return PublicId;
    }

    public UUID getMovieId() {
        return MovieId;
    }

    public Movie getMovie() {
        return movie;
    }
}
