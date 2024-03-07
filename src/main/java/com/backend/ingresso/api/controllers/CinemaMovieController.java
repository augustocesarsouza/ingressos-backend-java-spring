package com.backend.ingresso.api.controllers;

import com.backend.ingresso.application.dto.CinemaMovieDTO;
import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.validations.cinemaMovieDTOs.CinemaMovieCreate;
import com.backend.ingresso.application.dto.validations.movieValidationDTOs.MovieCreateValidatorDTO;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.interfaces.ICinemaMovieService;
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
public class CinemaMovieController {
    private final ICinemaMovieService cinemaMovieService;

    @Autowired
    public CinemaMovieController(ICinemaMovieService cinemaMovieService) {
        this.cinemaMovieService = cinemaMovieService;
    }

    @GetMapping("/cinemaMovie/getAll/{region}/{movieId}")
    public ResponseEntity<ResultService<List<CinemaMovieDTO>>> getAllMovieByRegionId(@PathVariable String region, @PathVariable String movieId){
        var result = cinemaMovieService.getByRegionCinemaIdAndMovieId(region, UUID.fromString(movieId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/cinemaMovie/create")
    public ResponseEntity<ResultService<CinemaMovieDTO>> create(@Valid @RequestBody CinemaMovieCreate cinemaMovieDTO, BindingResult resultValid){
        var result = cinemaMovieService.create(cinemaMovieDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
