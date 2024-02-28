package com.backend.ingresso.application.mappings.MappingClassInterface;

import com.backend.ingresso.application.dto.MovieTheaterDTO;
import com.backend.ingresso.domain.entities.MovieTheater;

public interface IMovieTheaterMapper {
    public MovieTheaterDTO movieTheaterToMovieTheaterDto(MovieTheater movieTheater);
    public MovieTheater movieTheaterDtoToMovieTheater(MovieTheaterDTO movieTheaterDTO);
}
