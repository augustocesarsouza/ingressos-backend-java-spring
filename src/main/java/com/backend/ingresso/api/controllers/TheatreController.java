package com.backend.ingresso.api.controllers;

import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.TheatreDTO;
import com.backend.ingresso.application.dto.validations.movieValidationDTOs.MovieCreateValidatorDTO;
import com.backend.ingresso.application.dto.validations.movieValidationDTOs.MovieUpdateValidatorDTO;
import com.backend.ingresso.application.dto.validations.theatreDTOs.TheatreCreateValidatorDTO;
import com.backend.ingresso.application.dto.validations.theatreDTOs.TheatreUpdateValidatorDTO;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.interfaces.ITheatreService;
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
public class TheatreController {
    private final ITheatreService theatreService;

    @Autowired
    public TheatreController(ITheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @GetMapping("/public/theatre/get-all-region/{region}")
    public ResponseEntity<ResultService<List<TheatreDTO>>> getAllMovieByRegionId(@PathVariable String region){
        var result = theatreService.getAllTheatreByRegionId(region);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/theatre/create")
    public ResponseEntity<ResultService<TheatreDTO>> create(@Valid @RequestBody TheatreCreateValidatorDTO theatreCreateValidatorDTO, BindingResult resultValid){
        var result = theatreService.create(theatreCreateValidatorDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @DeleteMapping("/theatre/delete/{idTheatre}")
    public ResponseEntity<ResultService<TheatreDTO>> delete(@PathVariable String idTheatre){
        var result = theatreService.deleteTheatre(idTheatre);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/theatre/update")
    public ResponseEntity<ResultService<TheatreDTO>> updateImg(@Valid @RequestBody TheatreUpdateValidatorDTO theatreUpdateValidatorDTO, BindingResult resultValid){
        var result = theatreService.updateImgTheatre(theatreUpdateValidatorDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
