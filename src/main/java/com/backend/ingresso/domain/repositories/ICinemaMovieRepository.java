package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.domain.entities.CinemaMovie;

import java.util.List;
import java.util.UUID;

public interface ICinemaMovieRepository {
    List<CinemaMovie> getByRegionCinemaIdAndMovieId(UUID regionId, UUID movieId);
    CinemaMovie create(CinemaMovie cinemaMovie);
}
