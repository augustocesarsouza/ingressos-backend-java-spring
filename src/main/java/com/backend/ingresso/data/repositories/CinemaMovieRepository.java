package com.backend.ingresso.data.repositories;

import com.backend.ingresso.data.context.CinemaMovieRepositoryJPA;
import com.backend.ingresso.domain.entities.CinemaMovie;
import com.backend.ingresso.domain.repositories.ICinemaMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class CinemaMovieRepository implements ICinemaMovieRepository {
    private final CinemaMovieRepositoryJPA cinemaMovieRepositoryJPA;

    @Autowired
    public CinemaMovieRepository(CinemaMovieRepositoryJPA cinemaMovieRepositoryJPA) {
        this.cinemaMovieRepositoryJPA = cinemaMovieRepositoryJPA;
    }

    @Override
    public List<CinemaMovie> getByRegionCinemaIdAndMovieId(UUID regionId, UUID movieId) {
        return cinemaMovieRepositoryJPA.getByRegionCinemaIdAndMovieId(regionId, movieId);
    }

    @Override
    public CinemaMovie create(CinemaMovie cinemaMovie) {
        if(cinemaMovie == null)
            return null;

        return cinemaMovieRepositoryJPA.save(cinemaMovie);
    }
}
