package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.application.dto.FormOfPaymentDTO;
import com.backend.ingresso.domain.entities.FormOfPayment;

import java.util.List;
import java.util.UUID;

public interface IFormOfPaymentRepository {
    List<FormOfPaymentDTO> getMovieIDInfo(UUID movieId);
    FormOfPayment create(FormOfPayment formOfPayment);
}
