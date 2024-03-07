package com.backend.ingresso.data.repositories;

import com.backend.ingresso.application.dto.MovieTheaterDTO;
import com.backend.ingresso.data.context.MovieTheaterRepositoryJPA;
import com.backend.ingresso.domain.entities.MovieTheater;
import com.backend.ingresso.domain.repositories.IMovieTheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MovieTheaterRepository implements IMovieTheaterRepository {
    private final MovieTheaterRepositoryJPA movieTheaterRepositoryJPA;

    @Autowired
    public MovieTheaterRepository(MovieTheaterRepositoryJPA movieTheaterRepositoryJPA) {
        this.movieTheaterRepositoryJPA = movieTheaterRepositoryJPA;
    }

    public MovieTheater getMovieTheaterIfThereIs(UUID movieId, UUID regionId){
        return movieTheaterRepositoryJPA.getMovieTheaterIfThereIs(movieId, regionId).stream().findFirst().orElse(null);
    }

    public MovieTheater create(MovieTheater movieTheater){
        if(movieTheater == null)
            return null;

        return movieTheaterRepositoryJPA.save(movieTheater);
    }
}
