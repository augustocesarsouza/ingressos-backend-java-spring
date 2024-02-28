package com.backend.ingresso.domain.repositories;

import com.backend.ingresso.domain.entities.AdditionalInfoUser;
import com.backend.ingresso.domain.entities.User;

import java.text.ParseException;
import java.util.UUID;

public interface IAdditionalInfoUserRepository {
    public AdditionalInfoUser getInfoUser(UUID idGuid) throws ParseException;
    public AdditionalInfoUser getByIdGuidUser(UUID idGuid);
    public AdditionalInfoUser create(AdditionalInfoUser additionalInfoUser);
    public AdditionalInfoUser update(AdditionalInfoUser additionalInfoUser);
}
