package com.backend.ingresso.application.dto.validations.movieTheaterDTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

public class MovieTheaterCreate {
    @NotEmpty(message = "movieTitle should not be empty")
    @JsonProperty("movieTitle")
    private String MovieTitle;
    @NotEmpty(message = "regionState should not be empty")
    @JsonProperty("regionState")
    private String RegionState;

    public MovieTheaterCreate(String movieTitle, String regionState) {
        MovieTitle = movieTitle;
        RegionState = regionState;
    }

    public String getMovieTitle() {
        return MovieTitle;
    }

    public String getRegionState() {
        return RegionState;
    }
}
