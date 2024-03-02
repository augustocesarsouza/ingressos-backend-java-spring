package com.backend.ingresso.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class RegionTheatreDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("theatreId")
    private String TheatreId;
    @JsonProperty("regionId")
    private String RegionId;

    public RegionTheatreDTO(UUID id, String theatreId, String regionId) {
        Id = id;
        TheatreId = theatreId;
        RegionId = regionId;
    }

    public UUID getId() {
        return Id;
    }

    public String getTheatreId() {
        return TheatreId;
    }

    public String getRegionId() {
        return RegionId;
    }
}
