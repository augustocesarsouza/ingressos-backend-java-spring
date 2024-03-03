package com.backend.ingresso.data.context;

import com.backend.ingresso.application.dto.AdditionalInfoUserDTO;
import com.backend.ingresso.domain.entities.AdditionalInfoUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.UUID;

@Repository
public interface AdditionalInfoUserRepositoryJPA extends JpaRepository<AdditionalInfoUser, UUID> {
//    AdditionalInfoUserDTO(UUID id, Timestamp birthDate, String gender, String phone, String cep,
//                          String logradouro, String numero, String complemento,
//                          String referencia, String bairro, String estado, String cidade, UUID userId)

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "AdditionalInfoUser(null, a.BirthDate, a.Gender, a.Phone, a.Cep, a.Logradouro, a.Numero, a.Complemento," +
            "a.Referencia, a.Bairro, a.Estado, a.Cidade, null) " +
            "FROM AdditionalInfoUser AS a WHERE a.UserId = :idGuid")
    AdditionalInfoUser getInfoUser(UUID idGuid);

    @Query("SELECT new com.backend.ingresso.domain.entities." +
            "AdditionalInfoUser(a.Id, null, null, null, null, null, null," +
            "null, null, null, null, null, null) " +
            "FROM AdditionalInfoUser AS a WHERE a.UserId = :idGuid")
    AdditionalInfoUser getByIdGuidUser(UUID idGuid);
}
