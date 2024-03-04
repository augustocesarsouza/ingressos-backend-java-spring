package com.backend.ingresso.application.mappings.MappingClassInterface;

import com.backend.ingresso.application.dto.CinemaDTO;
import com.backend.ingresso.application.dto.validations.cinemaDTOs.CinemaCreateDTO;
import com.backend.ingresso.domain.entities.Cinema;

public interface ICinemaMapper {
    CinemaDTO cinemaToCinemaDto(Cinema cinema);
    Cinema cinemaDtoToCinema(CinemaDTO cinemaDTO);
    Cinema cinemaCreateDTOToCinema(CinemaCreateDTO cinemaCreateDTO);
}
