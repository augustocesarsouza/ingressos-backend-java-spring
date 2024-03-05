package com.backend.ingresso.application.services;

import com.backend.ingresso.application.dto.CinemaDTO;
import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.cinemaDTOs.CinemaCreateDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.ICinemaMapper;
import com.backend.ingresso.application.services.interfaces.ICinemaService;
import com.backend.ingresso.domain.entities.Cinema;
import com.backend.ingresso.domain.entities.Movie;
import com.backend.ingresso.domain.repositories.ICinemaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.UUID;

@Service
public class CinemaService implements ICinemaService {
    private final ICinemaRepository cinemaRepository;
    private final ICinemaMapper cinemaMapper;
    private final IValidateErrorsDTO validateErrorsDTO;

    @Autowired
    public CinemaService(ICinemaRepository cinemaRepository, ICinemaMapper cinemaMapper,
                         IValidateErrorsDTO validateErrorsDTO) {
        this.cinemaRepository = cinemaRepository;
        this.cinemaMapper = cinemaMapper;
        this.validateErrorsDTO = validateErrorsDTO;
    }

    @Override
    public ResultService<CinemaDTO> getCheckIfCinemaExistsById(UUID cinemaId) {
        if(cinemaId == null)
            return ResultService.Fail("error cinemaId null");

        try {
            Cinema cinema = cinemaRepository.getById(cinemaId);

            if(cinema == null)
                return ResultService.Fail("error cinema not exist");

            return ResultService.Ok(cinemaMapper.cinemaToCinemaDto(cinema));

        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<CinemaDTO> create(CinemaCreateDTO cinemaCreateDTO, BindingResult result) {
        if(cinemaCreateDTO == null)
            return null;

        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            var errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            UUID movieId = UUID.randomUUID();
            cinemaCreateDTO.setId(movieId);

            Cinema cinemaCreate = cinemaRepository.create(cinemaMapper.cinemaCreateDTOToCinema(cinemaCreateDTO));

            if(cinemaCreate == null)
                return ResultService.Fail("error create cinema repository");

            return ResultService.Ok(cinemaMapper.cinemaToCinemaDto(cinemaCreate));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<CinemaDTO> delete(UUID cinemaId) {
        if(cinemaId == null)
            return ResultService.Fail("error cinemaId NULL");

        try {
            Cinema cinemaCreate = cinemaRepository.delete(cinemaId);

            if(cinemaCreate == null)
                return ResultService.Fail("error delete cinema repository");

            return ResultService.Ok(cinemaMapper.cinemaToCinemaDto(cinemaCreate));
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }
}
