package com.backend.ingresso.data.context;

import com.backend.ingresso.application.dto.FormOfPaymentDTO;
import com.backend.ingresso.domain.entities.FormOfPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FormOfPaymentRepositoryJPA extends JpaRepository<FormOfPayment, UUID> {
    //FormOfPayment(UUID id, String formName, String price, String movieId, Movie movie)
    @Query("SELECT new com.backend.ingresso.application.dto." +
            "FormOfPaymentDTO(null, fp.FormName, fp.Price, null, null) " +
            "FROM FormOfPayment AS fp WHERE fp.MovieId = :movieId")
    List<FormOfPaymentDTO> getMovieIDInfo(UUID movieId);
}
