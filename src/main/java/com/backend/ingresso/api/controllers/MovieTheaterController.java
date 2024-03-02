package com.backend.ingresso.api.controllers;

import com.backend.ingresso.application.dto.MovieTheaterDTO;
import com.backend.ingresso.application.dto.validations.movieTheaterDTOs.MovieTheaterCreate;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.interfaces.IMovieTheaterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping("/v1")
public class MovieTheaterController {
    private final IMovieTheaterService movieTheaterService;

    @Autowired
    public MovieTheaterController(IMovieTheaterService movieTheaterService) {
        this.movieTheaterService = movieTheaterService;
    }

    @PostMapping("/public/movie-theater/create")
    public ResponseEntity<ResultService<MovieTheaterDTO>> create(@Valid @RequestBody MovieTheaterCreate movieTheaterCreate, BindingResult resultValid){
        var result = movieTheaterService.create(movieTheaterCreate, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
