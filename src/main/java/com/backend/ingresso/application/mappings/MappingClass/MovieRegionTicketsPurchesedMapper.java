package com.backend.ingresso.application.mappings.MappingClass;

import com.backend.ingresso.application.dto.MovieRegionTicketsPurchesedDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.ICinemaMapper;
import com.backend.ingresso.application.mappings.MappingClassInterface.IMovieMapper;
import com.backend.ingresso.application.mappings.MappingClassInterface.IMovieRegionTicketsPurchesedMapper;
import com.backend.ingresso.domain.entities.MovieRegionTicketsPurchesed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieRegionTicketsPurchesedMapper implements IMovieRegionTicketsPurchesedMapper {
    private final ICinemaMapper cinemaMapper;
    private final IMovieMapper movieMapper;

    @Autowired
    public MovieRegionTicketsPurchesedMapper(ICinemaMapper cinemaMapper, IMovieMapper movieMapper) {
        this.cinemaMapper = cinemaMapper;
        this.movieMapper = movieMapper;
    }

    @Override
    public MovieRegionTicketsPurchesedDTO movieRegionTicketsPurchesedToMovieRegionTicketsPurchesedDTO(MovieRegionTicketsPurchesed movieRegionTicketsPurchesed) {
        if(movieRegionTicketsPurchesed == null)
            return null;

        return new MovieRegionTicketsPurchesedDTO(movieRegionTicketsPurchesed.getId(),
                movieRegionTicketsPurchesed.getTicketsSeats(), movieRegionTicketsPurchesed.getMovieId(),
                movieMapper.movieToMovieDto(movieRegionTicketsPurchesed.getMovie()),
                movieRegionTicketsPurchesed.getCinemaId(),
                cinemaMapper.cinemaToCinemaDto(movieRegionTicketsPurchesed.getCinema()));
    }

    @Override
    public MovieRegionTicketsPurchesedDTO movieRegionTicketsPurchesedToMovieRegionTicketsPurchesedDtoUpdate(MovieRegionTicketsPurchesed movieRegionTicketsPurchesed) {
        if(movieRegionTicketsPurchesed == null)
            return null;

        return new MovieRegionTicketsPurchesedDTO(movieRegionTicketsPurchesed.getId(),
                movieRegionTicketsPurchesed.getTicketsSeats(), movieRegionTicketsPurchesed.getMovieId(),
                null,
                movieRegionTicketsPurchesed.getCinemaId(),
                null);
    }

    @Override
    public MovieRegionTicketsPurchesed movieRegionTicketsPurchesedDtoToMovieRegionTicketsPurchesed(MovieRegionTicketsPurchesedDTO movieRegionTicketsPurchesedDTO) {
        if(movieRegionTicketsPurchesedDTO == null)
            return null;

        return new MovieRegionTicketsPurchesed(movieRegionTicketsPurchesedDTO.getId(),
                movieRegionTicketsPurchesedDTO.getTicketsSeats(), movieRegionTicketsPurchesedDTO.getMovieId(),
                movieMapper.movieDtoToMovie(movieRegionTicketsPurchesedDTO.getMovieDTO()),
                movieRegionTicketsPurchesedDTO.getCinemaId(),
                cinemaMapper.cinemaDtoToCinema(movieRegionTicketsPurchesedDTO.getCinemaDTO()));
    }
}
