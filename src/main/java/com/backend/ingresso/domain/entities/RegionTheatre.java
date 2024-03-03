package com.backend.ingresso.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_region_theatres", schema = "public")
public class RegionTheatre {
    @Id
    @Column(name = "region_theatre_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "theatre_id")
    @JsonProperty("theatreId")
    private UUID TheatreId;
    @Column(name = "region_id")
    @JsonProperty("regionId")
    private UUID RegionId;
    @ManyToOne
    @JoinColumn(name = "theatre_id", insertable = false, updatable = false)
    private Theatre theatre;

    public RegionTheatre(UUID id, UUID theatreId, UUID regionId) {
        Id = id;
        TheatreId = theatreId;
        RegionId = regionId;
    }

    public RegionTheatre() {
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
