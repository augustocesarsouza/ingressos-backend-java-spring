package com.backend.ingresso.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_regions", schema = "public")
public class Region {
    @Id
    @Column(name = "region_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "state")
    @JsonProperty("state")
    private String State;
    @Column(name = "city")
    @JsonProperty("city")
    private String City;
    @OneToMany(mappedBy = "region", cascade = CascadeType.REMOVE)
    private List<CinemaMovie> cinemaMovies;

    public Region(UUID id, String state, String city) {
        Id = id;
        State = state;
        City = city;
    }

    public Region() {
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
