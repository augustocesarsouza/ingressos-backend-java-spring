package com.backend.ingresso.application.mappings.MappingClass;

import com.backend.ingresso.application.dto.CinemaDTO;
import com.backend.ingresso.application.dto.FormOfPaymentDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IFormOfPaymentMapper;
import com.backend.ingresso.application.mappings.MappingClassInterface.IMovieMapper;
import com.backend.ingresso.domain.entities.Cinema;
import com.backend.ingresso.domain.entities.FormOfPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormOfPaymentMapper implements IFormOfPaymentMapper {
    private final IMovieMapper movieMapper;

    @Autowired
    public FormOfPaymentMapper(IMovieMapper movieMapper) {
        this.movieMapper = movieMapper;
    }

    @Override
    public FormOfPaymentDTO formOfPaymentToFormOfPaymentDto(FormOfPayment formOfPayment) {
        if(formOfPayment == null)
            return null;

        return new FormOfPaymentDTO(formOfPayment.getId(), formOfPayment.getFormName(), formOfPayment.getPrice(),
                formOfPayment.getMovieId(), movieMapper.movieToMovieDto(formOfPayment.getMovie()));
    }

    @Override
    public FormOfPayment formOfPaymentDtoToFormOfPayment(FormOfPaymentDTO formOfPaymentDTO) {
        if(formOfPaymentDTO == null)
            return null;

        return new FormOfPayment(formOfPaymentDTO.getId(), formOfPaymentDTO.getFormName(), formOfPaymentDTO.getPrice(),
                formOfPaymentDTO.getMovieId(), movieMapper.movieDtoToMovie(formOfPaymentDTO.getMovieDTO()));
    }
}
