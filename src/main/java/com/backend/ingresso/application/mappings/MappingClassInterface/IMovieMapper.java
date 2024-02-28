package com.backend.ingresso.application.mappings.MappingClassInterface;

import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.domain.entities.Movie;

public interface IMovieMapper {
    public MovieDTO movieToMovieDto(Movie movie);
    public Movie movieDtoToMovie(MovieDTO movieDTO);
}
