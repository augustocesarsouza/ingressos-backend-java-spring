package com.backend.ingresso.application.mappings.MappingClassInterface;

import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.validations.movieValidationDTOs.MovieCreateValidatorDTO;
import com.backend.ingresso.application.dto.validations.movieValidationDTOs.MovieUpdateValidatorDTO;
import com.backend.ingresso.domain.entities.Movie;

public interface IMovieMapper {
    public MovieDTO movieToMovieDto(Movie movie);
    public Movie movieDtoToMovie(MovieDTO movieDTO);
    public Movie movieCreateValidatorDTOToMovie(MovieCreateValidatorDTO movieCreateValidatorDTO);
    public MovieDTO movieCreateValidatorDTOToMovieDTO(MovieCreateValidatorDTO movieCreateValidatorDTO);
    public Movie movieUpdateValidatorDTOToMovie(MovieUpdateValidatorDTO movieUpdateValidatorDTO);
}
