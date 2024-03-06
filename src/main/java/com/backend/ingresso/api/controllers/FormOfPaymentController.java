package com.backend.ingresso.api.controllers;

import com.backend.ingresso.application.dto.CinemaMovieDTO;
import com.backend.ingresso.application.dto.FormOfPaymentDTO;
import com.backend.ingresso.application.dto.validations.cinemaMovieDTOs.CinemaMovieCreate;
import com.backend.ingresso.application.dto.validations.formOfPaymentDTOs.FormOfPaymentCreate;
import com.backend.ingresso.application.services.FormOfPaymentService;
import com.backend.ingresso.application.services.ResultService;
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
@RequestMapping("/v1")
public class FormOfPaymentController {
    private final FormOfPaymentService formOfPaymentService;

    @Autowired
    public FormOfPaymentController(FormOfPaymentService formOfPaymentService) {
        this.formOfPaymentService = formOfPaymentService;
    }

    @GetMapping("/public/form-of-payment/get-form/{movieId}")
    public ResponseEntity<ResultService<List<FormOfPaymentDTO>>> getMovieIdInfo(@PathVariable String movieId){
        var result = formOfPaymentService.getMovieIdInfo(UUID.fromString(movieId));

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/public/form-of-payment/create")
    public ResponseEntity<ResultService<FormOfPaymentDTO>> create(@Valid @RequestBody FormOfPaymentCreate formOfPaymentCreate, BindingResult resultValid){
        var result = formOfPaymentService.create(formOfPaymentCreate, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
