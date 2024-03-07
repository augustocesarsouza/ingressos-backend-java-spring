package com.backend.ingresso.api.controllers;

import com.backend.ingresso.application.dto.MovieTheaterDTO;
import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.dto.validations.movieTheaterDTOs.MovieTheaterCreate;
import com.backend.ingresso.application.services.RegionService;
import com.backend.ingresso.application.services.ResultService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@CrossOrigin
@RequestMapping("/v1")
public class RegionController {
    private final RegionService regionService;

    @Autowired
    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/region/by-name-city/{state}")
    public ResponseEntity<ResultService<RegionDTO>> getIdByNameState(@PathVariable String state){
        var result = regionService.getIdByNameState(state);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/region/create")
    public ResponseEntity<ResultService<RegionDTO>> create(@Valid @RequestBody RegionDTO regionDTO, BindingResult resultValid){
        var result = regionService.create(regionDTO, resultValid);//fazer

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
