package com.backend.ingresso.applicationTest;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.MovieRegionTicketsPurchesedDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.movieRegionTicketsPurchesedDTOs.MovieRegionTicketsPurchesedCreateDTO;
import com.backend.ingresso.application.dto.validations.movieRegionTicketsPurchesedDTOs.MovieRegionTicketsPurchesedUpdateDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IMovieRegionTicketsPurchesedMapper;
import com.backend.ingresso.application.services.MovieRegionTicketsPurchesedService;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.interfaces.ICinemaService;
import com.backend.ingresso.application.services.interfaces.IMovieService;
import com.backend.ingresso.domain.entities.MovieRegionTicketsPurchesed;
import com.backend.ingresso.domain.repositories.IMovieRegionTicketsPurchesedRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class MovieRegionTicketsPurchesedServiceTest {
    @Mock
    private IMovieRegionTicketsPurchesedRepository movieRegionTicketsPurchesedRepository;
    @Mock
    private IMovieRegionTicketsPurchesedMapper movieRegionTicketsPurchesedMapper;
    @Mock
    private IMovieService movieService;
    @Mock
    private ICinemaService cinemaService;
    @Mock
    private IValidateErrorsDTO validateErrorsDTO;

    private MovieRegionTicketsPurchesedService movieRegionTicketsPurchesedService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        movieRegionTicketsPurchesedService = new MovieRegionTicketsPurchesedService(
                movieRegionTicketsPurchesedRepository,
                movieRegionTicketsPurchesedMapper,
                movieService, cinemaService,
                validateErrorsDTO
        );
    }

    @Test
    public void should_GetByMovieIdAndCinemaId_WithoutErrors(){
        String movieId = "25e96c07-4380-4c74-b43f-cd4281914e33";
        String cinemaId = "7a002069-c938-4e77-b808-3e31d9456cd6";

        var movieTickets = new MovieRegionTicketsPurchesedDTO(
                null, "A2,A3", UUID.fromString("1363bf0e-2104-434d-9271-6c9cb5a65d7c"),
                null, UUID.fromString("631a2488-6cc3-4761-9d3c-38b28d5c43d3"),
                null
        );

        when(movieRegionTicketsPurchesedRepository.
                getByMovieIdAndCinemaId(any(), any())).
                thenReturn(new MovieRegionTicketsPurchesed());

        when(movieRegionTicketsPurchesedMapper.
                movieRegionTicketsPurchesedToMovieRegionTicketsPurchesedDTO(any())).
                thenReturn(movieTickets);

        // Act
        var result = movieRegionTicketsPurchesedService.
                getByMovieIdAndCinemaId(UUID.fromString(movieId), UUID.fromString(cinemaId));

        // Assert
        assertTrue(result.IsSuccess);
        assertEquals(result.Data.getMovieId(), movieTickets.getMovieId());
        assertEquals(result.Data.getCinemaId(), movieTickets.getCinemaId());
        assertEquals(result.Data.getTicketsSeats(), movieTickets.getTicketsSeats());
    }

    @Test
    public void should_GetByMovieIdAndCinemaId_ErrorGetMovieIdAndCinemaIdNull(){
        String movieId = "25e96c07-4380-4c74-b43f-cd4281914e33";
        String cinemaId = "7a002069-c938-4e77-b808-3e31d9456cd6";

        when(movieRegionTicketsPurchesedRepository.
                getByMovieIdAndCinemaId(any(), any())).
                thenReturn(null);

        // Act
        var result = movieRegionTicketsPurchesedService.
                getByMovieIdAndCinemaId(UUID.fromString(movieId), UUID.fromString(cinemaId));

        // Assert
        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error null get");
    }

    @Test
    public void should_Create_WithoutErrors(){
        MovieRegionTicketsPurchesedCreateDTO movieRegionTicketsPurchesedCreateDTO =
                new MovieRegionTicketsPurchesedCreateDTO(
                        null, "25e96c07-4380-4c74-b43f-cd4281914e33",
                        "7a002069-c938-4e77-b808-3e31d9456cd6");

        var resultError = new BeanPropertyBindingResult(movieRegionTicketsPurchesedCreateDTO, "movieRegionTicketsPurchesedCreateDTO");

        MovieRegionTicketsPurchesed movieRegionTicketsPurchesed =
                new MovieRegionTicketsPurchesed(
                        null, null,
                        UUID.fromString("25e96c07-4380-4c74-b43f-cd4281914e33"), null,
                        UUID.fromString("7a002069-c938-4e77-b808-3e31d9456cd6"), null
                );

        MovieRegionTicketsPurchesedDTO movieRegionTicketsPurchesedDTO =
                new MovieRegionTicketsPurchesedDTO(
                        null, null,
                        UUID.fromString("25e96c07-4380-4c74-b43f-cd4281914e33"), null,
                        UUID.fromString("7a002069-c938-4e77-b808-3e31d9456cd6"), null
                );

        when(movieService.getCheckIfMovieExistsById(any())).
                thenReturn(ResultService.Ok("get movie successfully"));

        when(cinemaService.getCheckIfCinemaExistsById(any())).
                thenReturn(ResultService.Ok("get cinema successfully"));

        when(movieRegionTicketsPurchesedRepository.
                create(any())).
                thenReturn(movieRegionTicketsPurchesed);

        when(movieRegionTicketsPurchesedMapper.
                movieRegionTicketsPurchesedToMovieRegionTicketsPurchesedDTO(any())).
                thenReturn(movieRegionTicketsPurchesedDTO);

        var result = movieRegionTicketsPurchesedService.
                create(movieRegionTicketsPurchesedCreateDTO,
                        resultError);

        assertTrue(result.IsSuccess);
        assertEquals(result.Data.getMovieId(), movieRegionTicketsPurchesedDTO.getMovieId());
        assertEquals(result.Data.getCinemaId(), movieRegionTicketsPurchesedDTO.getCinemaId());
    }

    @Test
    public void should_Create_ErrorDtoCreateNull(){
        var result = movieRegionTicketsPurchesedService.
                create(null,
                        null);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error DTO Null");
    }

    @Test
    public void should_Create_ErrorValidateDtoCreate(){
        MovieRegionTicketsPurchesedCreateDTO movieRegionTicketsPurchesedCreateDTO =
                new MovieRegionTicketsPurchesedCreateDTO(
                        null, "25e96c07-4380-4c74-b43f-cd4281914e33",
                        "7a002069-c938-4e77-b808-3e31d9456cd6");

        var resultError = new BeanPropertyBindingResult(movieRegionTicketsPurchesedCreateDTO, "movieRegionTicketsPurchesedCreateDTO");
        resultError.rejectValue("movieId", "NotEmpty", "movieId should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("movieId", "movieId should not be empty");
        errors.add(errorValidation);

        when(validateErrorsDTO.
                ValidateDTO(any())).
                thenReturn(errors);

        var result = movieRegionTicketsPurchesedService.
                create(movieRegionTicketsPurchesedCreateDTO,
                        resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void should_Create_ErrorMovieIdDtoCreateNull(){
        MovieRegionTicketsPurchesedCreateDTO movieRegionTicketsPurchesedCreateDTO =
                new MovieRegionTicketsPurchesedCreateDTO(
                        null, null,
                        "7a002069-c938-4e77-b808-3e31d9456cd6");

        var resultError = new BeanPropertyBindingResult(movieRegionTicketsPurchesedCreateDTO, "movieRegionTicketsPurchesedCreateDTO");

        var result = movieRegionTicketsPurchesedService.
                create(movieRegionTicketsPurchesedCreateDTO,
                        resultError);

        assertFalse(result.IsSuccess);
    }

    @Test
    public void should_Create_ErrorCinemaIdDtoCreateNull(){
        MovieRegionTicketsPurchesedCreateDTO movieRegionTicketsPurchesedCreateDTO =
                new MovieRegionTicketsPurchesedCreateDTO(
                        null, "25e96c07-4380-4c74-b43f-cd4281914e33",
                        null);

        var resultError = new BeanPropertyBindingResult(movieRegionTicketsPurchesedCreateDTO, "movieRegionTicketsPurchesedCreateDTO");

        var result = movieRegionTicketsPurchesedService.
                create(movieRegionTicketsPurchesedCreateDTO,
                        resultError);

        assertFalse(result.IsSuccess);
    }

    @Test
    public void should_Create_ErrorValidateMovieId(){
        MovieRegionTicketsPurchesedCreateDTO movieRegionTicketsPurchesedCreateDTO =
                new MovieRegionTicketsPurchesedCreateDTO(
                        null, "25e96c07-4380-4c74-b43f-cd428114e33",
                        "7a002069-c938-4e77-b808-3e31d9456cd6");

        var resultError = new BeanPropertyBindingResult(movieRegionTicketsPurchesedCreateDTO, "movieRegionTicketsPurchesedCreateDTO");

        var result = movieRegionTicketsPurchesedService.
                create(movieRegionTicketsPurchesedCreateDTO,
                        resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error movieId, not is UUID valid");
    }

    @Test
    public void should_Create_ErrorValidateCinemaId(){
        MovieRegionTicketsPurchesedCreateDTO movieRegionTicketsPurchesedCreateDTO =
                new MovieRegionTicketsPurchesedCreateDTO(
                        null, "25e96c07-4380-4c74-b43f-cd4281914e33",
                        "7a002069-c938-4e77-b808-3e3d9456cd6");

        var resultError = new BeanPropertyBindingResult(movieRegionTicketsPurchesedCreateDTO, "movieRegionTicketsPurchesedCreateDTO");

        var result = movieRegionTicketsPurchesedService.
                create(movieRegionTicketsPurchesedCreateDTO,
                        resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error cinemaId, not is UUID valid");
    }

    @Test
    public void should_Create_ErrorMovieNotExist(){
        MovieRegionTicketsPurchesedCreateDTO movieRegionTicketsPurchesedCreateDTO =
                new MovieRegionTicketsPurchesedCreateDTO(
                        null, "25e96c07-4380-4c74-b43f-cd4281914e33",
                        "7a002069-c938-4e77-b808-3e31d9456cd6");

        var resultError = new BeanPropertyBindingResult(movieRegionTicketsPurchesedCreateDTO, "movieRegionTicketsPurchesedCreateDTO");

        when(movieService.getCheckIfMovieExistsById(any())).
                thenReturn(ResultService.Fail("error get movie"));

        var result = movieRegionTicketsPurchesedService.
                create(movieRegionTicketsPurchesedCreateDTO,
                        resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error movie not exist");
    }

    @Test
    public void should_Create_ErrorCinemaNotExist(){
        MovieRegionTicketsPurchesedCreateDTO movieRegionTicketsPurchesedCreateDTO =
                new MovieRegionTicketsPurchesedCreateDTO(
                        null, "25e96c07-4380-4c74-b43f-cd4281914e33",
                        "7a002069-c938-4e77-b808-3e31d9456cd6");

        var resultError = new BeanPropertyBindingResult(movieRegionTicketsPurchesedCreateDTO, "movieRegionTicketsPurchesedCreateDTO");

        when(movieService.getCheckIfMovieExistsById(any())).
                thenReturn(ResultService.Ok("get movie successfully"));

        when(cinemaService.getCheckIfCinemaExistsById(any())).
                thenReturn(ResultService.Fail("error get cinema"));

        var result = movieRegionTicketsPurchesedService.
                create(movieRegionTicketsPurchesedCreateDTO,
                        resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error cinema not exist");
    }

    @Test
    public void should_Create_ErrorCreateRepository(){
        MovieRegionTicketsPurchesedCreateDTO movieRegionTicketsPurchesedCreateDTO =
                new MovieRegionTicketsPurchesedCreateDTO(
                        null, "25e96c07-4380-4c74-b43f-cd4281914e33",
                        "7a002069-c938-4e77-b808-3e31d9456cd6");

        var resultError = new BeanPropertyBindingResult(movieRegionTicketsPurchesedCreateDTO, "movieRegionTicketsPurchesedCreateDTO");

        MovieRegionTicketsPurchesed movieRegionTicketsPurchesed =
                new MovieRegionTicketsPurchesed(
                        null, null,
                        UUID.fromString("25e96c07-4380-4c74-b43f-cd4281914e33"), null,
                        UUID.fromString("7a002069-c938-4e77-b808-3e31d9456cd6"), null
                );

        MovieRegionTicketsPurchesedDTO movieRegionTicketsPurchesedDTO =
                new MovieRegionTicketsPurchesedDTO(
                        null, null,
                        UUID.fromString("25e96c07-4380-4c74-b43f-cd4281914e33"), null,
                        UUID.fromString("7a002069-c938-4e77-b808-3e31d9456cd6"), null
                );

        when(movieService.getCheckIfMovieExistsById(any())).
                thenReturn(ResultService.Ok("get movie successfully"));

        when(cinemaService.getCheckIfCinemaExistsById(any())).
                thenReturn(ResultService.Ok("get cinema successfully"));

        when(movieRegionTicketsPurchesedRepository.create(any())).
                thenReturn(null);

        var result = movieRegionTicketsPurchesedService.
                create(movieRegionTicketsPurchesedCreateDTO,
                        resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error create repository movieRegionTicketsPurchesed");

    }

    @Test
    public void should_UpdateTicketsSeats_WithoutErrors(){
        MovieRegionTicketsPurchesedUpdateDTO movieRegionTicketsPurchesedUpdateDTO =
                new MovieRegionTicketsPurchesedUpdateDTO(
                        "A2", "25e96c07-4380-4c74-b43f-cd4281914e33",
                        "7a002069-c938-4e77-b808-3e31d9456cd6");

        var resultError = new BeanPropertyBindingResult(movieRegionTicketsPurchesedUpdateDTO, "movieRegionTicketsPurchesedUpdateDTO");

        MovieRegionTicketsPurchesedDTO movieRegionTicketsPurchesedDTO =
                new MovieRegionTicketsPurchesedDTO(
                        null,"A2,A3", UUID.fromString("25e96c07-4380-4c74-b43f-cd4281914e33"),
                        null,
                        UUID.fromString("7a002069-c938-4e77-b808-3e31d9456cd6"), null);

        when(movieRegionTicketsPurchesedRepository.
                getByMovieIdAndCinemaIdAndIdTicketsSeats(any(), any())).
                thenReturn(new MovieRegionTicketsPurchesed());

        when(movieRegionTicketsPurchesedRepository.
                updateTicketsSeats(any())).
                thenReturn(new MovieRegionTicketsPurchesed());

        when(movieRegionTicketsPurchesedMapper.
                movieRegionTicketsPurchesedToMovieRegionTicketsPurchesedDtoUpdate(any())).
                thenReturn(movieRegionTicketsPurchesedDTO);

        var result = movieRegionTicketsPurchesedService.
                updateTicketsSeats(movieRegionTicketsPurchesedUpdateDTO,
                        resultError);

        assertTrue(result.IsSuccess);
        assertEquals(result.Data.getTicketsSeats(), movieRegionTicketsPurchesedDTO.getTicketsSeats());
        assertEquals(result.Data.getMovieId(), movieRegionTicketsPurchesedDTO.getMovieId());
        assertEquals(result.Data.getCinemaId(), movieRegionTicketsPurchesedDTO.getCinemaId());
    }

    @Test
    public void should_UpdateTicketsSeats_DTOUpdateNull(){
        var result = movieRegionTicketsPurchesedService.
                updateTicketsSeats(null,
                        null);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error DTO Null");
    }

    @Test
    public void should_UpdateTicketsSeats_ErrorValidateDTOUpdate(){
        MovieRegionTicketsPurchesedUpdateDTO movieRegionTicketsPurchesedUpdateDTO =
                new MovieRegionTicketsPurchesedUpdateDTO(
                        null, "25e96c07-4380-4c74-b43f-cd4281914e33",
                        "7a002069-c938-4e77-b808-3e31d9456cd6");

        var resultError = new BeanPropertyBindingResult(movieRegionTicketsPurchesedUpdateDTO, "movieRegionTicketsPurchesedUpdateDTO");

        resultError.rejectValue("ticketsSeats", "NotEmpty", "ticketsSeats should not be empty");

        List<ErrorValidation> errors = new ArrayList<>();
        var errorValidation = new ErrorValidation("ticketsSeats", "ticketsSeats should not be empty");
        errors.add(errorValidation);

        when(validateErrorsDTO.ValidateDTO(any())).thenReturn(errors);

        var result = movieRegionTicketsPurchesedService.
                updateTicketsSeats(movieRegionTicketsPurchesedUpdateDTO,
                        resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error validate DTO");
        assertEquals(result.Errors, errors);
    }

    @Test
    public void should_UpdateTicketsSeats_ErrorValidateUUIDMovieId(){
        MovieRegionTicketsPurchesedUpdateDTO movieRegionTicketsPurchesedUpdateDTO =
                new MovieRegionTicketsPurchesedUpdateDTO(
                        "A2", "25e96c07-4380-474-b43f-cd4281914e33",
                        "7a002069-c938-4e77-b808-3e31d9456cd6");

        var resultError = new BeanPropertyBindingResult(movieRegionTicketsPurchesedUpdateDTO, "movieRegionTicketsPurchesedUpdateDTO");

        var result = movieRegionTicketsPurchesedService.
                updateTicketsSeats(movieRegionTicketsPurchesedUpdateDTO,
                        resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error movieId, not is UUID valid");
    }

    @Test
    public void should_UpdateTicketsSeats_ErrorValidateUUIDCinemaId(){
        MovieRegionTicketsPurchesedUpdateDTO movieRegionTicketsPurchesedUpdateDTO =
                new MovieRegionTicketsPurchesedUpdateDTO(
                        "A2", "25e96c07-4380-4c74-b43f-cd4281914e33",
                        "7a00269-c938-4e77-b808-3e31d9456cd6");

        var resultError = new BeanPropertyBindingResult(movieRegionTicketsPurchesedUpdateDTO, "movieRegionTicketsPurchesedUpdateDTO");

        var result = movieRegionTicketsPurchesedService.
                updateTicketsSeats(movieRegionTicketsPurchesedUpdateDTO,
                        resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error cinemaId, not is UUID valid");
    }

    @Test
    public void should_UpdateTicketsSeats_ErrorUpdateRepository(){
        MovieRegionTicketsPurchesedUpdateDTO movieRegionTicketsPurchesedUpdateDTO =
                new MovieRegionTicketsPurchesedUpdateDTO(
                        "A2", "25e96c07-4380-4c74-b43f-cd4281914e33",
                        "7a002069-c938-4e77-b808-3e31d9456cd6");

        var resultError = new BeanPropertyBindingResult(movieRegionTicketsPurchesedUpdateDTO, "movieRegionTicketsPurchesedUpdateDTO");

        when(movieRegionTicketsPurchesedRepository.
                getByMovieIdAndCinemaIdAndIdTicketsSeats(any(), any())).
                thenReturn(new MovieRegionTicketsPurchesed());

        when(movieRegionTicketsPurchesedRepository.
                updateTicketsSeats(any())).
                thenReturn(null);

        var result = movieRegionTicketsPurchesedService.
                updateTicketsSeats(movieRegionTicketsPurchesedUpdateDTO,
                        resultError);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error update repository");
    }
}
