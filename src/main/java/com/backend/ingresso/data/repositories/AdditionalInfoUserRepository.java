package com.backend.ingresso.data.repositories;

import com.backend.ingresso.application.dto.AdditionalInfoUserDTO;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.data.context.AdditionalInfoUserRepositoryJPA;
import com.backend.ingresso.domain.entities.AdditionalInfoUser;
import com.backend.ingresso.domain.repositories.IAdditionalInfoUserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class AdditionalInfoUserRepository implements IAdditionalInfoUserRepository {
    private final AdditionalInfoUserRepositoryJPA additionalInfoUserRepositoryJPA;

    @Autowired
    public AdditionalInfoUserRepository(AdditionalInfoUserRepositoryJPA additionalInfoUserRepositoryJPA) {
        this.additionalInfoUserRepositoryJPA = additionalInfoUserRepositoryJPA;
    }

    @Override
    public AdditionalInfoUser getInfoUser(UUID idGuid) {
        return additionalInfoUserRepositoryJPA.getInfoUser(idGuid);
        //a.BirthDate, a.Gender, a.Phone, a.Cep, a.Logradouro, a.Numero, a.Complemento, a.Referencia, a.Bairro, a.Estado, a.Cidade
    }

    @Override
    public AdditionalInfoUser getByIdGuidUser(UUID idGuid) {
        return additionalInfoUserRepositoryJPA.getByIdGuidUser(idGuid);
        //@Query("SELECT a.Id FROM AdditionalInfoUser AS a WHERE a.UserId = :idGuid")
    }

    @Override
    public AdditionalInfoUser create(AdditionalInfoUser additionalInfoUser) {
        return additionalInfoUserRepositoryJPA.save(additionalInfoUser);
    }

    @Override
    public AdditionalInfoUser update(AdditionalInfoUser additionalInfoUser) {
        AdditionalInfoUser additionalInfoUser1 = additionalInfoUserRepositoryJPA.findById(additionalInfoUser.getId()).orElse(null);

        if(additionalInfoUser1 == null)
            return null;

        additionalInfoUser1.addData(additionalInfoUser.getBirthDate(), additionalInfoUser.getGender(), additionalInfoUser.getPhone(),
                additionalInfoUser.getCep(), additionalInfoUser.getLogradouro(), additionalInfoUser.getNumero(),
                additionalInfoUser.getComplemento(), additionalInfoUser.getReferencia(), additionalInfoUser.getBairro(),
                additionalInfoUser.getEstado(), additionalInfoUser.getCidade(), additionalInfoUser.getUserId());

        return additionalInfoUserRepositoryJPA.save(additionalInfoUser1);
    }
}
