package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.domain.entities.Movie;

import java.util.List;
import java.util.UUID;

public interface IMovieRepository {
    public Movie getById(UUID movieId);
    public Movie getInfoMoviesById(UUID movieId);
    public Movie getStatusMovie(String statusMovie);
    public Movie getByIdAllProps(UUID movieId);
    public List<Movie> getAllMovieByRegionId(UUID regionId);
    public Movie create(Movie movie);
    public Movie update(Movie movie);
    public Movie delete(UUID movieId);
}
