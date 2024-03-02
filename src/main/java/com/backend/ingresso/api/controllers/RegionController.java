package com.backend.ingresso.api.controllers;

import com.backend.ingresso.application.dto.RegionDTO;
import com.backend.ingresso.application.services.RegionService;
import com.backend.ingresso.application.services.ResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class RegionController {
    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping("/public/region/by-name-city/{state}")
    public ResponseEntity<ResultService<RegionDTO>> getIdByNameState(@PathVariable String state){
        var result = regionService.getIdByNameState(state);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
