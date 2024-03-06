package com.backend.ingresso.application.services;

import com.backend.ingresso.application.ErrorValidation;
import com.backend.ingresso.application.dto.FormOfPaymentDTO;
import com.backend.ingresso.application.dto.MovieDTO;
import com.backend.ingresso.application.dto.validateErrosDTOs.IValidateErrorsDTO;
import com.backend.ingresso.application.dto.validations.formOfPaymentDTOs.FormOfPaymentCreate;
import com.backend.ingresso.application.mappings.MappingClassInterface.IFormOfPaymentMapper;
import com.backend.ingresso.application.services.interfaces.IFormOfPaymentService;
import com.backend.ingresso.application.services.interfaces.IMovieService;
import com.backend.ingresso.application.util.ValidateUUID;
import com.backend.ingresso.domain.entities.FormOfPayment;
import com.backend.ingresso.domain.repositories.IFormOfPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.UUID;

@Service
public class FormOfPaymentService implements IFormOfPaymentService {
    private final IFormOfPaymentRepository formOfPaymentRepository;
    private final IFormOfPaymentMapper formOfPaymentMapper;
    private final IMovieService movieService;
    private final IValidateErrorsDTO validateErrorsDTO;

    @Autowired
    public FormOfPaymentService(IFormOfPaymentRepository formOfPaymentRepository, IFormOfPaymentMapper formOfPaymentMapper,
                                IMovieService movieService, IValidateErrorsDTO validateErrorsDTO) {
        this.formOfPaymentRepository = formOfPaymentRepository;
        this.formOfPaymentMapper = formOfPaymentMapper;
        this.movieService = movieService;
        this.validateErrorsDTO = validateErrorsDTO;
    }

    @Override
    public ResultService<List<FormOfPaymentDTO>> getMovieIdInfo(UUID movieId) {
        try {
            List<FormOfPaymentDTO> formOfPaymentDTOS = formOfPaymentRepository.getMovieIDInfo(movieId);

            return ResultService.Ok(formOfPaymentDTOS);
        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }

    @Override
    public ResultService<FormOfPaymentDTO> create(FormOfPaymentCreate formOfPaymentCreate, BindingResult result) {
        if(formOfPaymentCreate == null)
            return ResultService.Fail("error DTO Create null");

        if(result.hasErrors()){
            var errorsDTO = result.getAllErrors();
            List<ErrorValidation> errors = validateErrorsDTO.ValidateDTO(errorsDTO);

            return ResultService.RequestError("error validate DTO", errors);
        }

        try {
            if(!ValidateUUID.Validate(formOfPaymentCreate.getMovieId()))
                return ResultService.Fail("error movieId, not is UUID valid");

            UUID movieId = UUID.fromString(formOfPaymentCreate.getMovieId());

            ResultService<MovieDTO> movieDTO = movieService.getCheckIfMovieExistsById(movieId);

            if(!movieDTO.IsSuccess)
                return ResultService.Fail("error movie not exist");

            UUID id = UUID.randomUUID();
            FormOfPayment formOfPayment = new FormOfPayment(id, formOfPaymentCreate.getFormName(),
                    formOfPaymentCreate.getPrice(), UUID.fromString(formOfPaymentCreate.getMovieId()), null);

                FormOfPayment formOfPaymentRepoCreate = formOfPaymentRepository.create(formOfPayment);

            if(formOfPaymentRepoCreate == null)
                return ResultService.Fail("error create repository");

            return ResultService.Ok(formOfPaymentMapper.formOfPaymentToFormOfPaymentDto(formOfPaymentRepoCreate));

        }catch (Exception ex){
            return ResultService.Fail(ex.getMessage());
        }
    }
}
