package com.backend.ingresso.api.controllers;

import com.backend.ingresso.application.dto.AdditionalFoodMovieDTO;
import com.backend.ingresso.application.dto.validations.additionalFoodMovieDTOs.AdditionalFoodMovieCreate;
import com.backend.ingresso.application.services.AdditionalFoodMovieService;
import com.backend.ingresso.application.services.ResultService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@Component
@RestController
@RequestMapping("/v1")
public class AdditionalFoodMovieController {
    private final AdditionalFoodMovieService additionalFoodMovieService;

    @Autowired
    public AdditionalFoodMovieController(AdditionalFoodMovieService additionalFoodMovieService) {
        this.additionalFoodMovieService = additionalFoodMovieService;
    }

    @GetMapping("/public/additionalfoodmovie/getallfood/{movieId}")
    public ResponseEntity<ResultService<List<AdditionalFoodMovieDTO>>> getAllFoodMovie(@PathVariable String movieId) {
        var result = additionalFoodMovieService.getAllFoodMovie(UUID.fromString(movieId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
    @PostMapping("/public/additionalfoodmovie/create")
    public ResponseEntity<ResultService<AdditionalFoodMovieDTO>> create(@Valid @RequestBody AdditionalFoodMovieCreate additionalFoodMovieCreate, BindingResult resultValid){
        var result = additionalFoodMovieService.create(additionalFoodMovieCreate, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
