package com.backend.ingresso.api.controllers;

import com.backend.ingresso.application.dto.TokenAlreadyVisualizedDTO;
import com.backend.ingresso.application.dto.UserDTO;
import com.backend.ingresso.application.dto.UserPermissionDTO;
import com.backend.ingresso.application.dto.validations.userValidationDTOs.UserPasswordChangeDTO;
import com.backend.ingresso.application.dto.validations.userValidationDTOs.UserCreateValidatorDTO;
import com.backend.ingresso.application.dto.validations.userValidationDTOs.UserUpdateValidatorDTO;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.interfaces.IUserAuthenticationService;
import com.backend.ingresso.application.services.interfaces.IUserConfirmationService;
import com.backend.ingresso.application.services.interfaces.IUserManagementService;
import com.backend.ingresso.data.utilityExternal.Interface.ISendEmailUser;
import com.backend.ingresso.domain.entities.User;
import com.backend.ingresso.domain.repositories.IUserPermissionRepository;
import com.backend.ingresso.domain.repositories.IUserRepository;
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
public class UserController {
    private final IUserManagementService userManagementService;
    private final IUserRepository userRepository;
    private final IUserAuthenticationService userAuthenticationService;
    private final ISendEmailUser sendEmailUser;
    private final IUserConfirmationService userConfirmationService;

    @Autowired
    public UserController(IUserManagementService userManagementService, IUserAuthenticationService userAuthenticationService, ISendEmailUser sendEmailUser,
                          IUserConfirmationService userConfirmationService, IUserRepository userRepository) {
        this.userManagementService = userManagementService;
        this.userAuthenticationService = userAuthenticationService;
        this.sendEmailUser = sendEmailUser;
        this.userConfirmationService = userConfirmationService;
        this.userRepository = userRepository;
    }

//    @GetMapping("/public/user/getUsers/{email}")
//    public ResponseEntity<ResultService<UserDTO>> getUsers(@PathVariable String email){
//        var result = userRepository.getUserByEmailObj(email);
//        return ResponseEntity.ok(ResultService.Ok(result));
//    }

    @GetMapping("/public/user/login/{cpfOrEmail}/{password}")
    public ResponseEntity<ResultService<UserDTO>> login(@PathVariable String cpfOrEmail, @PathVariable String password){
        var result = userAuthenticationService.login(cpfOrEmail, password);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/public/user/confirm-token/{token}")
    public ResponseEntity<ResultService<TokenAlreadyVisualizedDTO>> getConfirmToken(@PathVariable String token){
        var result = userConfirmationService.GetConfirmToken(token);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @GetMapping("/public/user/verific/{code}/{guidId}")
    public ResponseEntity<ResultService<String>> verify(@PathVariable String code, @PathVariable String guidId){
        var result = userAuthenticationService.verify(Integer.parseInt(code), guidId);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/public/user/resend-code")
    public ResponseEntity<ResultService<String>> resendCode(@RequestBody UserDTO userDTO){
        var result = userAuthenticationService.resendCode(userDTO);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/public/user/create")
    public ResponseEntity<ResultService<UserCreateValidatorDTO>> create(@Valid @RequestBody UserCreateValidatorDTO userCreateValidatorDTO, BindingResult resultValid){
        var result = userManagementService.create(userCreateValidatorDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/user/update-user/{password}")
    public ResponseEntity<ResultService<UserDTO>> update(@Valid @RequestBody UserUpdateValidatorDTO userUpdateValidatorDTO,
                                                         BindingResult resultValid, @PathVariable String password){
        var result = userManagementService.update(userUpdateValidatorDTO, resultValid, password);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
    @PutMapping("/public/user/update-user-password")
    public ResponseEntity<ResultService<UserDTO>> updatePassword(@Valid @RequestBody UserPasswordChangeDTO userPasswordChangeDTO, BindingResult resultValid){
        var result = userManagementService.updateUserPassword(userPasswordChangeDTO, resultValid);

        if(result.IsSuccess){
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().body(result);
    }
}
