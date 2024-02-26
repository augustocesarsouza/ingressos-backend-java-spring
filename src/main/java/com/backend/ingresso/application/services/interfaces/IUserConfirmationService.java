package com.backend.ingresso.application.services.interfaces;

import com.backend.ingresso.application.dto.TokenAlreadyVisualizedDTO;
import com.backend.ingresso.application.services.ResultService;

public interface IUserConfirmationService {
    ResultService<TokenAlreadyVisualizedDTO> GetConfirmToken(String token);
}
