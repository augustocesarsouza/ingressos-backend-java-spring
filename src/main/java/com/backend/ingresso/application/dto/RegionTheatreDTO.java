package com.backend.ingresso.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionTheatreDTO {
    @JsonProperty("id")
    private UUID Id;
    @JsonProperty("theatreId")
    private UUID TheatreId;
    @JsonProperty("regionId")
    private UUID RegionId;

    public RegionTheatreDTO(UUID id, UUID theatreId, UUID regionId) {
        Id = id;
        TheatreId = theatreId;
        RegionId = regionId;
    }

    public UUID getId() {
        return Id;
    }

    public UUID getTheatreId() {
        return TheatreId;
    }

    public UUID getRegionId() {
        return RegionId;
    }
}
