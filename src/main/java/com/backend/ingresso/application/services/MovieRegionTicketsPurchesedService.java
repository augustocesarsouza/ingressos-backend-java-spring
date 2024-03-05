package com.backend.ingresso.application.services;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.MovieRegionTicketsPurchesedDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.movieRegionTicketsPurchesedDTOs.MovieRegionTicketsPurchesedCreateDTO;
import com.backend.ingresso.application.dto.validations.movieRegionTicketsPurchesedDTOs.MovieRegionTicketsPurchesedUpdateDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IMovieRegionTicketsPurchesedMapper;
import com.backend.ingresso.application.services.interfaces.IMovieRegionTicketsPurchesedService;
import com.backend.ingresso.application.util.ValidateUUID;
import com.backend.ingresso.domain.entities.MovieRegionTicketsPurchesed;
import com.backend.ingresso.domain.repositories.IMovieRegionTicketsPurchesedRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

@Service
public class MovieRegionTicketsPurchesedService implements IMovieRegionTicketsPurchesedService {
    private final IMovieRegionTicketsPurchesedRepository movieRegionTicketsPurchesedRepository;
    private final IMovieRegionTicketsPurchesedMapper movieRegionTicketsPurchesedMapper;
    private final IValidateErrorsDTO validateErrorsDTO;

    @Autowired
    public MovieRegionTicketsPurchesedService(IMovieRegionTicketsPurchesedRepository movieRegionTicketsPurchesedRepository,
                                              IMovieRegionTicketsPurchesedMapper movieRegionTicketsPurchesedMapper,
                                              IValidateErrorsDTO validateErrorsDTO) {
        this.movieRegionTicketsPurchesedRepository = movieRegionTicketsPurchesedRepository;
        this.movieRegionTicketsPurchesedMapper = movieRegionTicketsPurchesedMapper;
        this.validateErrorsDTO = validateErrorsDTO;
    }

    @Override
    @Transactional
    public ResultService<MovieRegionTicketsPurchesedDTO> getByMovieIdAndCinemaId(UUID movieId, UUID cinemaId) {
        try {
            MovieRegionTicketsPurchesed regionTicketsPurchesed = movieRegionTicketsPurchesedRepository.
                    getByMovieIdAndCinemaId(movieId, cinemaId);

            if(regionTicketsPurchesed == null)
                return ResultService.Fail("error null get");

            return ResultService.Ok(movieRegionTicketsPurchesedMapper.
                    movieRegionTicketsPurchesedToMovieRegionTicketsPurchesedDTO(regionTicketsPurchesed));

        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }

    }

    @Override
    @Transactional
    public ResultService<MovieRegionTicketsPurchesedDTO> create(MovieRegionTicketsPurchesedCreateDTO movieRegionTicketsPurchesedCreateDTO, BindingResult resultValid) {
        if(movieRegionTicketsPurchesedCreateDTO == null)
            return ResultService.Fail("error DTO Null");

        if(resultValid.hasErrors()){
            var errorsDTO = resultValid.getAllErrors();
            List<ErrorValidation> errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            UUID id = UUID.randomUUID();

            MovieRegionTicketsPurchesed movieRegionTicketsPurchesed = new MovieRegionTicketsPurchesed();
            movieRegionTicketsPurchesed.setIdMovieIdCinemaId(id, UUID.fromString(movieRegionTicketsPurchesedCreateDTO.getMovieId()),
                    UUID.fromString(movieRegionTicketsPurchesedCreateDTO.getCinemaId()));

            MovieRegionTicketsPurchesed movieRegionTicketsPurchesedCreate = movieRegionTicketsPurchesedRepository.create(movieRegionTicketsPurchesed);

            if(movieRegionTicketsPurchesedCreate == null)
                return ResultService.Fail("error create repository movieRegionTicketsPurchesed");

            return ResultService.Ok(movieRegionTicketsPurchesedMapper.
                    movieRegionTicketsPurchesedToMovieRegionTicketsPurchesedDTO(movieRegionTicketsPurchesedCreate));

        } catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    @Transactional
    public ResultService<MovieRegionTicketsPurchesedDTO> updateTicketsSeats(MovieRegionTicketsPurchesedUpdateDTO movieRegionTicketsPurchesedUpdateDTO, BindingResult resultValid) {
        if(movieRegionTicketsPurchesedUpdateDTO == null)
            return ResultService.Fail("error DTO Null");

        if(resultValid.hasErrors()){
            var errorsDTO = resultValid.getAllErrors();
            List<ErrorValidation> errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            if(!ValidateUUID.Validate(movieRegionTicketsPurchesedUpdateDTO.getMovieId()))
                return ResultService.Fail("error movieId, not is UUID valid");

            if(!ValidateUUID.Validate(movieRegionTicketsPurchesedUpdateDTO.getCinemaId()))
                return ResultService.Fail("error cinemaId, not is UUID valid");

            MovieRegionTicketsPurchesed movieRegionTicketsPurchesed = new MovieRegionTicketsPurchesed();
            movieRegionTicketsPurchesed.setMovieIdCinemaIdTicketsSeats(UUID.fromString(movieRegionTicketsPurchesedUpdateDTO.getMovieId()),
                    UUID.fromString(movieRegionTicketsPurchesedUpdateDTO.getCinemaId()), movieRegionTicketsPurchesedUpdateDTO.getTicketsSeats());

            MovieRegionTicketsPurchesed movieRegionTicketsPurchesedUpdate = movieRegionTicketsPurchesedRepository.
                    updateTicketsSeats(movieRegionTicketsPurchesed);

            if(movieRegionTicketsPurchesedUpdate == null)
                return ResultService.Fail("error update repository");

            return ResultService.Ok(movieRegionTicketsPurchesedMapper.
                    movieRegionTicketsPurchesedToMovieRegionTicketsPurchesedDTO(movieRegionTicketsPurchesedUpdate));

        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }
}
