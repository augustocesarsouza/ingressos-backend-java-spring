package com.backend.ingresso.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_cinema_movies", schema = "public")
public class CinemaMovie {
    @Id
    @Column(name = "cinema_movie_id")
    @JsonProperty("id")
    private UUID Id;
    @Column(name = "cinema_id")
    @JsonProperty("cinemaId")
    private UUID CinemaId;
    @ManyToOne
    @JoinColumn(name = "cinema_id", insertable = false, updatable = false)
    private Cinema cinema;
    @Column(name = "movie_id")
    @JsonProperty("movieId")
    private UUID MovieId;
    @ManyToOne
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private Movie movie;
    @Column(name = "region_id")
    @JsonProperty("regionId")
    private UUID RegionId;
    @ManyToOne
    @JoinColumn(name = "region_id", insertable = false, updatable = false)
    private Region region;
    @Column(name = "screening_schedule")
    private String ScreeningSchedule;

    public CinemaMovie(UUID id, UUID cinemaId, Cinema cinema, UUID movieId, Movie movie, UUID regionId, Region region, String screeningSchedule) {
        Id = id;
        CinemaId = cinemaId;
        this.cinema = cinema;
        MovieId = movieId;
        this.movie = movie;
        RegionId = regionId;
        this.region = region;
        ScreeningSchedule = screeningSchedule;
    }

    public CinemaMovie() {
    }

    public UUID getId() {
        return Id;
    }

    public UUID getCinemaId() {
        return CinemaId;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public UUID getMovieId() {
        return MovieId;
    }

    public Movie getMovie() {
        return movie;
    }

    public UUID getRegionId() {
        return RegionId;
    }

    public Region getRegion() {
        return region;
    }

    public String getScreeningSchedule() {
        return ScreeningSchedule;
    }
}
