package com.backend.ingresso.application.mappings.MappingClass;

import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.validations.movieValidationDTOs.MovieCreateValidatorDTO;
import com.backend.ingresso.application.dto.validations.movieValidationDTOs.MovieUpdateValidatorDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IMovieMapper;
import com.backend.ingresso.domain.entities.Movie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MovieMapper implements IMovieMapper {
    @Override
    public MovieDTO movieToMovieDto(Movie movie) {
        if(movie == null)
            return null;

        return new MovieDTO(movie.getId(), movie.getTitle(), movie.getDescription(), movie.getGender(),
                movie.getDuration(), movie.getMovieRating(), movie.getImgUrl(), movie.getPublicId(),
                movie.getImgUrlBackground(), movie.getPublicIdImgBackground(), movie.getStatusMovie());
    }

    @Override
    public Movie movieDtoToMovie(MovieDTO movieDTO) {
        if(movieDTO == null)
            return null;

        return new Movie(movieDTO.getId(), movieDTO.getTitle(), movieDTO.getDescription(), movieDTO.getGender(),
                movieDTO.getDuration(), movieDTO.getMovieRating(), movieDTO.getImgUrl(), movieDTO.getPublicId(),
                movieDTO.getImgUrlBackground(), movieDTO.getPublicIdImgBackground(), movieDTO.getStatusMovie());
    }

    @Override
    public Movie movieCreateValidatorDTOToMovie(MovieCreateValidatorDTO movieCreateValidatorDTO) {
        if(movieCreateValidatorDTO == null)
            return null;
        Movie movie = new Movie();
        movie.setDataForCreateMovie(movieCreateValidatorDTO.getId(), movieCreateValidatorDTO.getTitle(),
                movieCreateValidatorDTO.getDescription(), movieCreateValidatorDTO.getGender(),
                movieCreateValidatorDTO.getDuration(), movieCreateValidatorDTO.getMovieRating(),
                movieCreateValidatorDTO.getImgUrl(), movieCreateValidatorDTO.getPublicId(), movieCreateValidatorDTO.getImgUrlBackground(),
                movieCreateValidatorDTO.getPublicIdImgBackground(), movieCreateValidatorDTO.getStatusMovie());

        return movie;
    }

    @Override
    public MovieDTO movieCreateValidatorDTOToMovieDTO(MovieCreateValidatorDTO movieCreateValidatorDTO) {
        if(movieCreateValidatorDTO == null)
            return null;
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setDataForCreateMovie(movieCreateValidatorDTO.getId(), movieCreateValidatorDTO.getTitle(),
                movieCreateValidatorDTO.getDescription(), movieCreateValidatorDTO.getGender(),
                movieCreateValidatorDTO.getDuration(), movieCreateValidatorDTO.getMovieRating(), movieCreateValidatorDTO.getBase64Img(),
                movieCreateValidatorDTO.getImgUrl(), movieCreateValidatorDTO.getPublicId(), movieCreateValidatorDTO.getImgUrlBackground(),
                movieCreateValidatorDTO.getPublicIdImgBackground(), movieCreateValidatorDTO.getStatusMovie());

        return movieDTO;
    }

    @Override
    public Movie movieUpdateValidatorDTOToMovie(MovieUpdateValidatorDTO movieUpdateValidatorDTO) {
        if(movieUpdateValidatorDTO == null)
            return null;

        Movie movie = new Movie();
        movie.setDataForCreateMovie(UUID.fromString(movieUpdateValidatorDTO.getId()), null,
                null, null,
                null, null,
                movieUpdateValidatorDTO.getImgUrl(), movieUpdateValidatorDTO.getPublicId(), movieUpdateValidatorDTO.getImgUrlBackground(),
                movieUpdateValidatorDTO.getPublicIdImgBackground(), null);

        return movie;
    }
}
