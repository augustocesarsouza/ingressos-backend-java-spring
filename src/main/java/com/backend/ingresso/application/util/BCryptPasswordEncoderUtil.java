package com.backend.ingresso.application.util;

import com.backend.ingresso.application.util.interfaces.IBCryptPasswordEncoderUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderUtil implements IBCryptPasswordEncoderUtil {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BCryptPasswordEncoderUtil() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public String encodePassword(String password) {
        if(password == null)
            return null;
        return bCryptPasswordEncoder.encode(password);
    }
}
