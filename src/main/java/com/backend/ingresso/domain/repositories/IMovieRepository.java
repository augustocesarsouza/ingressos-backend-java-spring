package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.domain.entities.Movie;

import java.util.List;
import java.util.UUID;

public interface IMovieRepository {
    public MovieDTO getById(UUID movieId);
    public MovieDTO getInfoMoviesById(UUID movieId);
    public MovieDTO getStatusMovie(String statusMovie);
    public MovieDTO getMovieByIdForDelete(UUID movieId);
    public List<MovieDTO> getAllMovieByRegionId(UUID regionId);
    MovieDTO getMovieByTitle(String title);
    public Movie create(Movie movie);
    public Movie updateImg(Movie movie);
    public Movie delete(UUID movieId);
}