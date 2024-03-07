package com.backend.ingresso.api.controllers;

import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.validations.movieValidationDTOs.MovieCreateValidatorDTO;
import com.backend.ingresso.application.dto.validations.movieValidationDTOs.MovieUpdateValidatorDTO;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.interfaces.IMovieService;
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
public class MovieController {
    private final IMovieService movieService;

    @Autowired
    public MovieController(IMovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/public/movie/get-all-region/{region}")
    public ResponseEntity<ResultService<List<MovieDTO>>> getAllMovieByRegionId(@PathVariable String region){
        var result = movieService.getAllMovieByRegionId(region);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/movie/info/{movieId}")
    public ResponseEntity<ResultService<MovieDTO>> getInfoMoviesById(@PathVariable String movieId){
        var result = movieService.getInfoMoviesById(UUID.fromString(movieId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/public/movie/get-status-movie/{statusMovie}")
    public ResponseEntity<ResultService<MovieDTO>> getStatusMovie(@PathVariable String statusMovie){
        var result = movieService.getStatusMovie(statusMovie);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/movie/create")
    public ResponseEntity<ResultService<MovieDTO>> create(@Valid @RequestBody MovieCreateValidatorDTO movieCreateValidatorDTO, BindingResult resultValid){
        var result = movieService.create(movieCreateValidatorDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/movie/update-img")
    public ResponseEntity<ResultService<MovieDTO>> updateImg(@Valid @RequestBody MovieUpdateValidatorDTO movieUpdateValidatorDTO, BindingResult resultValid){
        var result = movieService.updateMovieImg(movieUpdateValidatorDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/movie/delete/{idMovie}")
    public ResponseEntity<ResultService<MovieDTO>> delete(@PathVariable String idMovie){
        var result = movieService.deleteMovie(UUID.fromString(idMovie));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
