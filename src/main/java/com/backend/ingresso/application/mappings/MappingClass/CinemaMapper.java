package com.backend.ingresso.application.mappings.MappingClass;

import com.backend.ingresso.application.dto.CinemaDTO;
import com.backend.ingresso.application.dto.validations.cinemaDTOs.CinemaCreateDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.ICinemaMapper;
import com.backend.ingresso.domain.entities.Cinema;
import org.springframework.stereotype.Component;

@Component
public class CinemaMapper implements ICinemaMapper {
    @Override
    public CinemaDTO cinemaToCinemaDto(Cinema cinema) {
        if(cinema == null)
            return null;

        return new CinemaDTO(cinema.getId(), cinema.getNameCinema(), cinema.getDistrict(),
                cinema.getRanking());
    }

    @Override
    public Cinema cinemaDtoToCinema(CinemaDTO cinemaDTO) {
        if(cinemaDTO == null)
            return null;

        return new Cinema(cinemaDTO.getId(), cinemaDTO.getNameCinema(), cinemaDTO.getDistrict(),
                cinemaDTO.getRanking());
    }

    @Override
    public Cinema cinemaCreateDTOToCinema(CinemaCreateDTO cinemaCreateDTO) {
        if(cinemaCreateDTO == null)
            return null;

        return new Cinema(cinemaCreateDTO.getId(), cinemaCreateDTO.getNameCinema(),
                cinemaCreateDTO.getDistrict(), cinemaCreateDTO.getRanking());
    }
}
