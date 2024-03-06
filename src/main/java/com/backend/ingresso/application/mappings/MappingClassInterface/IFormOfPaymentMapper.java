package com.backend.ingresso.application.mappings.MappingClassInterface;

import com.backend.ingresso.application.dto.FormOfPaymentDTO;
import com.backend.ingresso.domain.entities.FormOfPayment;

public interface IFormOfPaymentMapper {
    FormOfPaymentDTO formOfPaymentToFormOfPaymentDto(FormOfPayment formOfPayment);
    FormOfPayment formOfPaymentDtoToFormOfPayment(FormOfPaymentDTO formOfPaymentDTO);
}
