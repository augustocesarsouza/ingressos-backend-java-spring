package com.backend.ingresso.application.dto.validations.formOfPaymentDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class FormOfPaymentCreate {
    @NotEmpty(message = " formName should not be empty")
    @JsonProperty("formName")
    private String FormName;
    @NotEmpty(message = " price should not be empty")
    @JsonProperty("price")
    private String Price;
    @NotEmpty(message = " movieId should not be empty")
    @Size(min = 36, message = "movieId must be at least 36 characters")
    @JsonProperty("movieId")
    private String MovieId;

    public FormOfPaymentCreate(String formName, String price, String movieId) {
        FormName = formName;
        Price = price;
        MovieId = movieId;
    }

    public FormOfPaymentCreate() {
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
}
