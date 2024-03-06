package com.backend.ingresso.application.services;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.CinemaDTO;
import com.backend.ingresso.application.dto.CinemaMovieDTO;
import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.cinemaMovieDTOs.CinemaMovieCreate;
import com.backend.ingresso.application.mappings.MappingClassInterface.ICinemaMovieMapper;
import com.backend.ingresso.application.services.interfaces.ICinemaMovieService;
import com.backend.ingresso.application.services.interfaces.ICinemaService;
import com.backend.ingresso.application.services.interfaces.IMovieService;
import com.backend.ingresso.application.services.interfaces.IRegionService;
import com.backend.ingresso.application.util.ValidateUUID;
import com.backend.ingresso.domain.entities.CinemaMovie;
import com.backend.ingresso.domain.entities.Region;
import com.backend.ingresso.domain.repositories.ICinemaMovieRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

@Service
public class CinemaMovieService implements ICinemaMovieService {
    private final ICinemaMovieRepository cinemaMovieRepository;
    private final IRegionService regionService;
    private final IValidateErrorsDTO validateErrorsDTO;
    private final IMovieService movieService;
    private final ICinemaService cinemaService;
    private final ICinemaMovieMapper cinemaMovieMapper;

    @Autowired
    public CinemaMovieService(ICinemaMovieRepository cinemaMovieRepository, IRegionService regionService,
                              IValidateErrorsDTO validateErrorsDTO, IMovieService movieService, ICinemaService cinemaService,
                              ICinemaMovieMapper cinemaMovieMapper) {
        this.cinemaMovieRepository = cinemaMovieRepository;
        this.regionService = regionService;
        this.validateErrorsDTO = validateErrorsDTO;
        this.movieService = movieService;
        this.cinemaService = cinemaService;
        this.cinemaMovieMapper = cinemaMovieMapper;
    }

    @Override
    @Transactional
    public ResultService<List<CinemaMovieDTO>> getByRegionCinemaIdAndMovieId(String region, UUID movieId) {
        try {
            ResultService<RegionDTO> regionOnlyId = regionService.getRegionIdByCity(region);

            if(!regionOnlyId.IsSuccess)
                return ResultService.Fail("error region not found");

            RegionDTO regionDto = regionOnlyId.Data;

            List<CinemaMovieDTO> cinemaMovieDTOS = cinemaMovieRepository.getByRegionCinemaIdAndMovieId(regionDto.getId(), movieId);

            return ResultService.Ok(cinemaMovieDTOS);

        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<CinemaMovieDTO> create(CinemaMovieCreate cinemaMovieDTO, BindingResult result) {
        if(cinemaMovieDTO == null)
            return ResultService.Fail("error DTO Create Null");

        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            List<ErrorValidation> errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            UUID id = UUID.randomUUID();

            if(!ValidateUUID.Validate(cinemaMovieDTO.getMovieId()))
                return ResultService.Fail("error movieId, not is UUID valid");

            if(!ValidateUUID.Validate(cinemaMovieDTO.getCinemaId()))
                return ResultService.Fail("error cinemaId, not is UUID valid");

            if(!ValidateUUID.Validate(cinemaMovieDTO.getRegionId()))
                return ResultService.Fail("error regionId, not is UUID valid");

            UUID movieId = UUID.fromString(cinemaMovieDTO.getMovieId());
            UUID cinemaId = UUID.fromString(cinemaMovieDTO.getCinemaId());
            UUID regionId = UUID.fromString(cinemaMovieDTO.getRegionId());

            ResultService<MovieDTO> movieDTO = movieService.getCheckIfMovieExistsById(movieId);

            if(!movieDTO.IsSuccess)
                return ResultService.Fail("error movie not exist");

            ResultService<CinemaDTO> cinemaDTO = cinemaService.getCheckIfCinemaExistsById(cinemaId);

            if(!cinemaDTO.IsSuccess)
                return ResultService.Fail("error cinema not exist");

            ResultService<RegionDTO> regionDTO = regionService.getCheckIfRegionExistsById(regionId);

            if(!regionDTO.IsSuccess)
                return ResultService.Fail("error region not exist");

            CinemaMovie cinemaMovie = new CinemaMovie(id, cinemaId, null,
                    movieId, null, regionId, null, cinemaMovieDTO.getScreeningSchedule());

            CinemaMovie resultCreate = cinemaMovieRepository.create(cinemaMovie);

            if(resultCreate == null)
                return ResultService.Fail("error create repository");

            CinemaMovieDTO cinemaMovieDTO1 = cinemaMovieMapper.cinemaMovieToCinemaMovieDto(resultCreate);

            return ResultService.Ok(cinemaMovieDTO1);

        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }
}
