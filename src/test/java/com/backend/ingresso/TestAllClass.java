package com.backend.ingresso;

import com.backend.ingresso.applicationTest.userServiceTest.UserAuthenticationServiceTest;
import com.backend.ingresso.applicationTest.userServiceTest.UserConfirmationServiceTest;
import com.backend.ingresso.applicationTest.userServiceTest.UserManagementServiceTest;
import com.backend.ingresso.applicationTest.utilTest.BCryptPasswordEncoderUtilTest;
import com.backend.ingresso.applicationTest.utilTest.DictionaryCodeTest;
import com.backend.ingresso.dataTest.authenticationTest.TokenGeneratorTest;
import com.backend.ingresso.dataTest.repositories.UserRepositoryTest;
import com.backend.ingresso.dataTest.utilityExternalTest.SendEmailUserTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({UserConfirmationServiceTest.class, UserManagementServiceTest.class,
        UserAuthenticationServiceTest.class, DictionaryCodeTest.class, BCryptPasswordEncoderUtilTest.class,
        TokenGeneratorTest.class, SendEmailUserTest.class, UserRepositoryTest.class})
public class TestAllClass {
}
