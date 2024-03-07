package com.backend.ingresso.api.controllers;

import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.MovieRegionTicketsPurchesedDTO;
import com.backend.ingresso.application.dto.validations.movieRegionTicketsPurchesedDTOs.MovieRegionTicketsPurchesedCreateDTO;
import com.backend.ingresso.application.dto.validations.movieRegionTicketsPurchesedDTOs.MovieRegionTicketsPurchesedUpdateDTO;
import com.backend.ingresso.application.dto.validations.movieValidationDTOs.MovieCreateValidatorDTO;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.interfaces.IMovieRegionTicketsPurchesedService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Component
@RestController
@CrossOrigin
@RequestMapping("/v1")
public class MovieRegionTicketsPurchesedController {
    private final IMovieRegionTicketsPurchesedService movieRegionTicketsPurchesedService;

    @Autowired
    public MovieRegionTicketsPurchesedController(IMovieRegionTicketsPurchesedService movieRegionTicketsPurchesedService) {
        this.movieRegionTicketsPurchesedService = movieRegionTicketsPurchesedService;
    }

    @GetMapping("/movie-region-tickets/get-by-movieid-and-cinemaid/{movieId}/{cinemaId}")
    public ResponseEntity<ResultService<MovieRegionTicketsPurchesedDTO>> getAllMovieByRegionId(@PathVariable String movieId, @PathVariable String cinemaId){
        var result = movieRegionTicketsPurchesedService.getByMovieIdAndCinemaId(UUID.fromString(movieId), UUID.fromString(cinemaId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/movie-region-tickets/create")
    public ResponseEntity<ResultService<MovieRegionTicketsPurchesedDTO>> create(
            @Valid @RequestBody MovieRegionTicketsPurchesedCreateDTO movieRegionTicketsPurchesedCreateDTO, BindingResult resultValid){
        var result = movieRegionTicketsPurchesedService.create(movieRegionTicketsPurchesedCreateDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/movie-region-tickets/update")
    public ResponseEntity<ResultService<MovieRegionTicketsPurchesedDTO>> update(
            @Valid @RequestBody MovieRegionTicketsPurchesedUpdateDTO movieRegionTicketsPurchesedUpdateDTO, BindingResult resultValid){
        var result = movieRegionTicketsPurchesedService.updateTicketsSeats(movieRegionTicketsPurchesedUpdateDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
