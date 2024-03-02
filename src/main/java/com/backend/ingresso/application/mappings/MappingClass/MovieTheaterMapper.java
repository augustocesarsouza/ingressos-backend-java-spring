package com.backend.ingresso.application.mappings.MappingClass;

import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.MovieTheaterDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IMovieTheaterMapper;
import com.backend.ingresso.domain.entities.MovieTheater;
import org.springframework.stereotype.Component;

@Component
public class MovieTheaterMapper implements IMovieTheaterMapper {
    @Override
    public MovieTheaterDTO movieTheaterToMovieTheaterDto(MovieTheater movieTheater) {
        if(movieTheater == null)
            return null;

        return new MovieTheaterDTO(movieTheater.getId(), movieTheater.getMovieId(), movieTheater.getRegionId());
    }

    @Override
    public MovieTheater movieTheaterDtoToMovieTheater(MovieTheaterDTO movieTheaterDTO) {
        if(movieTheaterDTO == null)
            return null;

        return new MovieTheater(movieTheaterDTO.getId(), movieTheaterDTO.getMovieId(), movieTheaterDTO.getRegionId());
    }
}
