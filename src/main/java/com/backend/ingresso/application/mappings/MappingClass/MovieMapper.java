package com.backend.ingresso.application.mappings.MappingClass;

import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IMovieMapper;
import com.backend.ingresso.domain.entities.Movie;

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
}
