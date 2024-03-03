package com.backend.ingresso.application.services;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.MovieTheaterDTO;
import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.movieTheaterDTOs.MovieTheaterCreate;
import com.backend.ingresso.application.mappings.MappingClassInterface.IMovieTheaterMapper;
import com.backend.ingresso.application.services.interfaces.IMovieService;
import com.backend.ingresso.application.services.interfaces.IMovieTheaterService;
import com.backend.ingresso.application.services.interfaces.IRegionService;
import com.backend.ingresso.domain.entities.MovieTheater;
import com.backend.ingresso.domain.repositories.IMovieTheaterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

@Service
public class MovieTheaterService implements IMovieTheaterService {
    private final IMovieTheaterRepository movieTheaterRepository;
    private final IValidateErrorsDTO validateErrorsDTO;
    private final IMovieService movieService;
    private final IRegionService regionService;
    private final IMovieTheaterMapper movieTheaterMapper;

    @Autowired
    public MovieTheaterService(IMovieTheaterRepository movieTheaterRepository, IMovieTheaterMapper movieTheaterMapper,
                               IValidateErrorsDTO validateErrorsDTO, IMovieService movieService, IRegionService regionService) {
        this.movieTheaterRepository = movieTheaterRepository;
        this.movieTheaterMapper = movieTheaterMapper;
        this.validateErrorsDTO = validateErrorsDTO;
        this.movieService = movieService;
        this.regionService = regionService;
    }

    @Override
    @Transactional
    public ResultService<MovieTheaterDTO> create(MovieTheaterCreate movieTheaterCreate, BindingResult resultValid) {
        if(movieTheaterCreate == null)
            return ResultService.Fail("error DTO Null");

        if(resultValid.hasErrors()){
            var errorsDTO = resultValid.getAllErrors();
            List<ErrorValidation> errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            ResultService<MovieDTO> resultMovieDTO = movieService.getIdMovieByTitle(movieTheaterCreate.getMovieTitle());

            if(!resultMovieDTO.IsSuccess)
                return ResultService.Fail(resultMovieDTO.Message);

            ResultService<RegionDTO> resultRegionDTOId = regionService.getIdRegionByState(movieTheaterCreate.getRegionState());

            if(!resultRegionDTOId.IsSuccess)
                return ResultService.Fail(resultRegionDTOId.Message);

            MovieDTO movieDTO = resultMovieDTO.Data;
            RegionDTO regionDTO = resultRegionDTOId.Data;

            MovieTheater movieTheaterIfThereIs = movieTheaterRepository.getMovieTheaterIfThereIs(movieDTO.getId(), regionDTO.getId());

            if(movieTheaterIfThereIs != null)
                return ResultService.Fail("error this junction already exists");

            MovieTheater movieTheater = new MovieTheater(UUID.randomUUID(), movieDTO.getId(), regionDTO.getId());

            var resultCreate = movieTheaterRepository.create(movieTheater);

            if(resultCreate == null)
                return ResultService.Fail("error when create");

            return ResultService.Ok(movieTheaterMapper.movieTheaterToMovieTheaterDto(resultCreate));

        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }
}
