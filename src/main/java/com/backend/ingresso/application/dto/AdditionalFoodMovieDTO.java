package com.backend.ingresso.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdditionalFoodMovieDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("title")
    private String Title;
    @JsonProperty("price")
    private String Price;
    @JsonProperty("fee")
    private String Fee;
    @JsonProperty("imgUrl")
    private String ImgUrl;
    @JsonProperty("publicId")
    private String PublicId;
    @JsonProperty("movieId")
    private UUID MovieId;
    private MovieDTO movieDTO;

    public AdditionalFoodMovieDTO(UUID id, String title, String price, String fee, String imgUrl, String publicId, UUID movieId, MovieDTO movieDTO) {
        Id = id;
        Title = title;
        Price = price;
        Fee = fee;
        ImgUrl = imgUrl;
        PublicId = publicId;
        MovieId = movieId;
        this.movieDTO = movieDTO;
    }

    public AdditionalFoodMovieDTO() {
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

    public MovieDTO getMovieDTO() {
        return movieDTO;
    }
}
