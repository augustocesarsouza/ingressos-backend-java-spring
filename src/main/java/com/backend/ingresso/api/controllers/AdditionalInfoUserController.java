package com.backend.ingresso.api.controllers;

import com.backend.ingresso.application.dto.AdditionalInfoUserDTO;
import com.backend.ingresso.application.dto.validations.AdditionalInfoUserValidationDTOs.AdditionalInfoUserCreateDTO;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.interfaces.IAdditionalInfoUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@Component
@RestController
@CrossOrigin
@RequestMapping("/v1")
public class AdditionalInfoUserController {
    private final IAdditionalInfoUserService additionalInfoUserService;

    public AdditionalInfoUserController(IAdditionalInfoUserService additionalInfoUserService) {
        this.additionalInfoUserService = additionalInfoUserService;
    }
    @GetMapping("/info-user/{idGuid}")
    public ResponseEntity<ResultService<AdditionalInfoUserDTO>> getInfoUser(@PathVariable String idGuid) throws ParseException {
        var result = additionalInfoUserService.getInfoUser(idGuid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
    @PostMapping("/additional/update-info-user/{password}")
    public ResponseEntity<ResultService<AdditionalInfoUserDTO>> update(@RequestBody AdditionalInfoUserCreateDTO additionalInfoUserCreateDTO, @PathVariable String password){
        var result = additionalInfoUserService.update(additionalInfoUserCreateDTO, password);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

}
