package com.backend.ingresso.data.context;

import com.backend.ingresso.domain.entities.AdditionalInfoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AdditionalInfoUserRepositoryJPA extends JpaRepository<AdditionalInfoUser, UUID> {
    @Query("SELECT a.BirthDate, a.Gender, a.Phone, a.Cep, a.Logradouro, a.Numero, a.Complemento, a.Referencia, a.Bairro, a.Estado, a.Cidade FROM AdditionalInfoUser AS a WHERE a.UserId = :idGuid")
    String getInfoUser(UUID idGuid);

    @Query("SELECT a.Id FROM AdditionalInfoUser AS a WHERE a.UserId = :idGuid")
    String getByIdGuidUser(UUID idGuid);
}
