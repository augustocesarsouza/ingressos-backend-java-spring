package com.backend.ingresso.application.mappings.MappingClassInterface;

import com.backend.ingresso.application.dto.CinemaMovieDTO;
import com.backend.ingresso.domain.entities.CinemaMovie;

public interface ICinemaMovieMapper {
    CinemaMovieDTO cinemaMovieToCinemaMovieDto(CinemaMovie cinemaMovie);
    CinemaMovie cinemaMovieDtoToCinemaMovie(CinemaMovieDTO cinemaMovieDTO);
}
