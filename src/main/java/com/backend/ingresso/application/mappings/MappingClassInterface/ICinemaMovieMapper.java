package com.backend.ingresso.application.mappings.MappingClassInterface;

import com.backend.ingresso.application.dto.CinemaMovieDTO;
import com.backend.ingresso.domain.entities.CinemaMovie;

import java.util.List;

public interface ICinemaMovieMapper {
    CinemaMovieDTO cinemaMovieToCinemaMovieDto(CinemaMovie cinemaMovie);
    CinemaMovie cinemaMovieDtoToCinemaMovie(CinemaMovieDTO cinemaMovieDTO);
    List<CinemaMovie> cinemaMovieListDtoToCinemaMovieListCinemaInclude(List<CinemaMovie> cinemaMovieList);
}
