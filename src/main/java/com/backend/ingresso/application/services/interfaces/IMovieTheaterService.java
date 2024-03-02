package com.backend.ingresso.application.services.interfaces;

import com.backend.ingresso.application.dto.MovieTheaterDTO;
import com.backend.ingresso.application.dto.validations.movieTheaterDTOs.MovieTheaterCreate;
import com.backend.ingresso.application.services.ResultService;
import org.springframework.validation.BindingResult;

public interface IMovieTheaterService {
    ResultService<MovieTheaterDTO> create(MovieTheaterCreate movieTheaterCreate, BindingResult resultValid);
}
