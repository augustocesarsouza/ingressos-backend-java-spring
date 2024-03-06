package com.backend.ingresso.data.repositories;

import com.backend.ingresso.application.dto.FormOfPaymentDTO;
import com.backend.ingresso.data.context.FormOfPaymentRepositoryJPA;
import com.backend.ingresso.domain.entities.FormOfPayment;
import com.backend.ingresso.domain.repositories.IFormOfPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class FormOfPaymentRepository implements IFormOfPaymentRepository {
    private final FormOfPaymentRepositoryJPA formOfPaymentRepositoryJPA;

    @Autowired
    public FormOfPaymentRepository(FormOfPaymentRepositoryJPA formOfPaymentRepositoryJPA) {
        this.formOfPaymentRepositoryJPA = formOfPaymentRepositoryJPA;
    }

    @Override
    public List<FormOfPaymentDTO> getMovieIDInfo(UUID movieId) {
        return formOfPaymentRepositoryJPA.getMovieIDInfo(movieId);
    }

    @Override
    public FormOfPayment create(FormOfPayment formOfPayment) {
        if(formOfPayment == null)
            return null;

        return formOfPaymentRepositoryJPA.save(formOfPayment);
    }
}
