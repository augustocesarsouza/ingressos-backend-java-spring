package com.backend.ingresso.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDTO {
    @JsonProperty("id")
    private UUID Id;
    @NotEmpty(message = "state should not be empty")
    @JsonProperty("state")
    private String State;
    @NotEmpty(message = "city should not be empty")
    @JsonProperty("city")
    private String City;

    public RegionDTO(UUID id, String state, String city) {
        Id = id;
        State = state;
        City = city;
    }

    public RegionDTO() {
    }

    public RegionDTO(UUID id) {
        Id = id;
    }

    public UUID getId() {
        return Id;
    }

    public String getState() {
        return State;
    }

    public String getCity() {
        return City;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public void setState(String state) {
        State = state;
    }

    public void setCity(String city) {
        City = city;
    }
}
