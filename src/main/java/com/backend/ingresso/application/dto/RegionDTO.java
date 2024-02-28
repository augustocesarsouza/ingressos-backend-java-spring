package com.backend.ingresso.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("state")
    private String State;
    @JsonProperty("city")
    private String City;

    public RegionDTO(UUID id, String state, String city) {
        Id = id;
        State = state;
        City = city;
    }

    public RegionDTO() {
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
}
