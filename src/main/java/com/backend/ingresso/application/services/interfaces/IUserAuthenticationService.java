package com.backend.ingresso.application.services.interfaces;

import com.backend.ingresso.application.dto.UserDTO;
import com.backend.ingresso.application.services.ResultService;

public interface IUserAuthenticationService {
    ResultService<UserDTO> login(String cpfOrEmail, String password);
    ResultService<String> verify(int code, String guidId);
    ResultService<String> resendCode(UserDTO user);

}
