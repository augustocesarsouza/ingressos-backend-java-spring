package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.domain.entities.Movie;

import java.util.List;
import java.util.UUID;

public interface IMovieRepository {
    public Movie getById(UUID movieId);
    public Movie getInfoMoviesById(UUID movieId);
    public Movie getStatusMovie(String statusMovie);
    public Movie getMovieByIdForDelete(UUID movieId);
    public List<MovieDTO> getAllMovieByRegionId(UUID regionId);
    Movie getMovieByTitle(String title);
    public Movie create(Movie movie);
    public Movie updateImg(Movie movie);
    public Movie delete(UUID movieId);
}
