package com.backend.ingresso;

import com.backend.ingresso.applicationTest.userServiceTest.UserAuthenticationServiceTest;
import com.backend.ingresso.applicationTest.userServiceTest.UserConfirmationServiceTest;
import com.backend.ingresso.applicationTest.userServiceTest.UserManagementServiceTest;
import com.backend.ingresso.applicationTest.utilTest.BCryptPasswordEncoderUtilTest;
import com.backend.ingresso.applicationTest.utilTest.DictionaryCodeTest;
import com.backend.ingresso.dataTest.authenticationTest.TokenGeneratorTest;
import com.backend.ingresso.dataTest.repositories.AdditionalInfoUserRepositoryTest;
import com.backend.ingresso.dataTest.repositories.MovieRepositoryTest;
import com.backend.ingresso.dataTest.repositories.UserPermissionRepositoryTest;
import com.backend.ingresso.dataTest.repositories.UserRepositoryTest;
import com.backend.ingresso.dataTest.utilityExternalTest.SendEmailBrevoTest;
import com.backend.ingresso.dataTest.utilityExternalTest.SendEmailUserTest;
import domainTest.entitiesTest.UserTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({UserAuthenticationServiceTest.class, UserConfirmationServiceTest.class,
        UserManagementServiceTest.class, DictionaryCodeTest.class, BCryptPasswordEncoderUtilTest.class,
        DictionaryCodeTest.class,TokenGeneratorTest.class, AdditionalInfoUserRepositoryTest.class,
        MovieRepositoryTest.class, UserPermissionRepositoryTest.class, UserRepositoryTest.class,
        SendEmailBrevoTest.class,SendEmailUserTest.class, UserTest.class})
public class TestAllClass {
}
