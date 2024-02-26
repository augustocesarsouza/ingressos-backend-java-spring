package com.backend.ingresso.data.utilityExternal.Interface;

import com.backend.ingresso.domain.InfoErrors.InfoErrors;
import com.backend.ingresso.domain.entities.User;

public interface ISendEmailBrevo {
    InfoErrors<String> sendEmail(User user, String url);
    InfoErrors<String> sendCode(User user, int codeRandom);
}
