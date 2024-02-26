package com.backend.ingresso.data.utilityExternal.Interface;

import com.backend.ingresso.domain.InfoErrors.InfoErrors;
import com.backend.ingresso.domain.entities.User;

public interface ISendEmailUser {
    InfoErrors<String> sendEmail(User user);
    InfoErrors<String> sendCodeRandom(User user, int code);
}
