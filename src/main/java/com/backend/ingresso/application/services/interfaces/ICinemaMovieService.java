package com.backend.ingresso.application.services.interfaces;

import com.backend.ingresso.application.dto.CinemaMovieDTO;
import com.backend.ingresso.application.dto.validations.cinemaMovieDTOs.CinemaMovieCreate;
import com.backend.ingresso.application.services.ResultService;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

public interface ICinemaMovieService {
    ResultService<List<CinemaMovieDTO>> getByRegionCinemaIdAndMovieId(String region, UUID movieId);
    ResultService<CinemaMovieDTO> create(CinemaMovieCreate cinemaMovieDTO, BindingResult result);
}
