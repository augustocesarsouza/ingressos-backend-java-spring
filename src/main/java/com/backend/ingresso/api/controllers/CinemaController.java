package com.backend.ingresso.api.controllers;

import com.backend.ingresso.application.dto.CinemaDTO;
import com.backend.ingresso.application.dto.validations.cinemaDTOs.CinemaCreateDTO;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.interfaces.ICinemaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Component
@RestController
@CrossOrigin
@RequestMapping("/v1")
public class CinemaController {
    private final ICinemaService cinemaService;

    @Autowired
    public CinemaController(ICinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @PostMapping("/cinema/create")
    public ResponseEntity<ResultService<CinemaDTO>> create(@Valid @RequestBody CinemaCreateDTO cinemaCreateDTO, BindingResult resultValid){
        var result = cinemaService.create(cinemaCreateDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/cinema/delete/{cinemaId}")
    public ResponseEntity<ResultService<CinemaDTO>> create(@PathVariable String cinemaId){
        var result = cinemaService.delete(UUID.fromString(cinemaId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
