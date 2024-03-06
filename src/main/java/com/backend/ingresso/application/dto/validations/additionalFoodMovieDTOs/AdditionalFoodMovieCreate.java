package com.backend.ingresso.application.dto.validations.additionalFoodMovieDTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdditionalFoodMovieCreate {
    @NotEmpty(message = "title should be not empty")
    @JsonProperty("title")
    private String Title;
    @NotEmpty(message = "price should be not empty")
    @JsonProperty("price")
    private String Price;
    @NotEmpty(message = "fee should be not empty")
    @JsonProperty("fee")
    private String Fee;
    @NotEmpty(message = "movieId should be not empty")
    @Size(min = 36, message = "movieId must be greater than 35")
    @JsonProperty("movieId")
    private String MovieId;
    @NotEmpty(message = "base64Img should be not empty")
    @JsonProperty("base64Img")
    private String Base64Img;

    public AdditionalFoodMovieCreate(String title, String price, String fee, String movieId, String base64Img) {
        Title = title;
        Price = price;
        Fee = fee;
        MovieId = movieId;
        Base64Img = base64Img;
    }

    public AdditionalFoodMovieCreate() {
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

    public String getMovieId() {
        return MovieId;
    }

    public String getBase64Img() {
        return Base64Img;
    }
}
