package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.application.dto.MovieTheaterDTO;
import com.backend.ingresso.domain.entities.MovieTheater;

import java.util.UUID;

public interface IMovieTheaterRepository {
    MovieTheater getMovieTheaterIfThereIs(UUID movieId, UUID regionId);
    MovieTheater create(MovieTheater movieTheater);
}
