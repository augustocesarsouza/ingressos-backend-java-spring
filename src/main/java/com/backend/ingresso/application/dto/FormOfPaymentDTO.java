package com.backend.ingresso.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FormOfPaymentDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("formName")
    private String FormName;
    @JsonProperty("price")
    private String Price;
    @JsonProperty("movieId")
    private String MovieId;
    private MovieDTO movieDTO;

    public FormOfPaymentDTO(UUID id, String formName, String price, String movieId, MovieDTO movieDTO) {
        Id = id;
        FormName = formName;
        Price = price;
        MovieId = movieId;
        this.movieDTO = movieDTO;
    }

    public FormOfPaymentDTO() {
    }

    public UUID getId() {
        return Id;
    }

    public String getFormName() {
        return FormName;
    }

    public String getPrice() {
        return Price;
    }

    public String getMovieId() {
        return MovieId;
    }

    public MovieDTO getMovieDTO() {
        return movieDTO;
    }
}
