package com.backend.ingresso.application.services.interfaces;

import com.backend.ingresso.application.dto.MovieRegionTicketsPurchesedDTO;
import com.backend.ingresso.application.dto.validations.movieRegionTicketsPurchesedDTOs.MovieRegionTicketsPurchesedCreateDTO;
import com.backend.ingresso.application.dto.validations.movieRegionTicketsPurchesedDTOs.MovieRegionTicketsPurchesedUpdateDTO;
import com.backend.ingresso.application.services.ResultService;
import org.springframework.validation.BindingResult;

import java.util.UUID;

public interface IMovieRegionTicketsPurchesedService {
    ResultService<MovieRegionTicketsPurchesedDTO> getByMovieIdAndCinemaId(UUID movieId, UUID cinemaId);
    ResultService<MovieRegionTicketsPurchesedDTO> create(MovieRegionTicketsPurchesedCreateDTO movieRegionTicketsPurchesedCreateDTO, BindingResult resultValid);
    ResultService<MovieRegionTicketsPurchesedDTO> updateTicketsSeats(MovieRegionTicketsPurchesedUpdateDTO movieRegionTicketsPurchesedUpdateDTO, BindingResult resultValid);
}
