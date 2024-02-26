package com.backend.ingresso.applicationTest.userServiceTest;

import com.backend.ingresso.application.dto.UserDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IUserMapper;
import com.backend.ingresso.application.mappings.MappingClassInterface.IUserPermissionMapper;
import com.backend.ingresso.application.services.ResultService;
import com.backend.ingresso.application.services.UserAuthenticationService;
import com.backend.ingresso.application.services.interfaces.IUserPermissionService;
import com.backend.ingresso.application.util.interfaces.IDictionaryCode;
import com.backend.ingresso.data.utilityExternal.Interface.ISendEmailUser;
import com.backend.ingresso.domain.Authentication.ITokenGenerator;
import com.backend.ingresso.domain.Authentication.TokenOutValue;
import com.backend.ingresso.domain.InfoErrors.InfoErrors;
import com.backend.ingresso.domain.entities.User;
import com.backend.ingresso.domain.repositories.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserAuthenticationServiceTest {
    @Mock
    private IUserRepository userRepository;
    @Mock
    private IUserPermissionService userPermissionService;
    @Mock
    private IUserPermissionMapper userPermissionMapper;
    @Mock
    private ITokenGenerator tokenGenerator;
    @Mock
    private IUserMapper userMapper;
    @Mock
    private ISendEmailUser sendEmailUser;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private IDictionaryCode dictionaryCode;
    private Authentication authenticate;
    private UserAuthenticationService userAuthenticationService;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        userAuthenticationService = new UserAuthenticationService(userRepository, userPermissionService,
                userPermissionMapper, userMapper, authenticationManager,
                tokenGenerator, sendEmailUser, dictionaryCode);

        authenticate = mock(Authentication.class);
    }

    //login Email//
    @Test
    public void shouldLoginEmailWithoutErrors(){
        String cpfOrEmail = "augusto@gmail.com";
        String password = "augusto123456";
        var user = new User(UUID.fromString("c242ab48-5ac4-421f-8ba2-37d83abff30c"), null, null,
                null, null,null);
        var TokenOutValue = new TokenOutValue("access_token_random", null);

        when(userRepository.getUserByEmail(any())).thenReturn(new User());
        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authenticate.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any())).thenReturn(authenticate);
        when(userPermissionService.getAllPermissionUser(any()))
                .thenReturn(ResultService.Ok("tudo certo"));
        when(tokenGenerator.generatorByEmail(any(), any(), any()))
                .thenReturn(InfoErrors.Ok(TokenOutValue));
        when(userMapper.userToUserDto(any()))
                .thenReturn(new UserDTO());

        var result = userAuthenticationService.login(cpfOrEmail, password);

        assertTrue(result.IsSuccess);
    }

    @Test
    public void shouldLoginEmailWithErrorUserNotFound(){
        String cpfOrEmail = "augusto@gmail.com";
        String password = "augusto123456";

        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(authenticate);
        when(userRepository.getUserByEmail(any())).thenReturn(null);

        var result = userAuthenticationService.login(cpfOrEmail, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "user not found");
    }

    @Test
    public void shouldLoginEmailWithErrorPasswordInvalid(){
        String cpfOrEmail = "augusto@gmail.com";
        String password = "augusto123456";

        when(userRepository.getUserByEmail(any())).thenReturn(new User());
        when(authenticate.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Usuário inexistente ou senha inválida"));

        var result = userAuthenticationService.login(cpfOrEmail, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "Usuário inexistente ou senha inválida");
    }

    @Test
    public void shouldLoginEmailWithErrorUserNotHavePermissions(){
        String cpfOrEmail = "augusto@gmail.com";
        String password = "augusto123456";
        var user = new User(UUID.fromString("c242ab48-5ac4-421f-8ba2-37d83abff30c"), null, null,
                null, null,null);

        when(userRepository.getUserByEmail(any())).thenReturn(new User());
        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authenticate.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any())).thenReturn(authenticate);
        when(userPermissionService.getAllPermissionUser(any()))
                .thenReturn(ResultService.Fail("not have permission"));

        var result = userAuthenticationService.login(cpfOrEmail, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "user does not have permissions");
    }

    @Test
    public void shouldLoginEmailWithErrorGenerateToken(){
        String cpfOrEmail = "augusto@gmail.com";
        String password = "augusto123456";
        var user = new User(UUID.fromString("c242ab48-5ac4-421f-8ba2-37d83abff30c"), null, null,
                null, null,null);

        when(userRepository.getUserByEmail(any())).thenReturn(new User());
        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authenticate.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any())).thenReturn(authenticate);
        when(userPermissionService.getAllPermissionUser(any()))
                .thenReturn(ResultService.Ok("tudo certo"));
        when(tokenGenerator.generatorByEmail(any(), any(), any()))
                .thenReturn(InfoErrors.Fail("failed to generate token"));

        var result = userAuthenticationService.login(cpfOrEmail, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "failed to generate token");
    }

    @Test
    public void shouldLoginEmailWithErrorMappingClass(){
        String cpfOrEmail = "augusto@gmail.com";
        String password = "augusto123456";
        var user = new User(UUID.fromString("c242ab48-5ac4-421f-8ba2-37d83abff30c"), null, null,
                null, null,null);
        var TokenOutValue = new TokenOutValue("access_token_random", null);

        when(userRepository.getUserByEmail(any())).thenReturn(new User());
        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authenticate.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any())).thenReturn(authenticate);
        when(userPermissionService.getAllPermissionUser(any()))
                .thenReturn(ResultService.Ok("tudo certo"));
        when(tokenGenerator.generatorByEmail(any(), any(), any()))
                .thenReturn(InfoErrors.Ok(TokenOutValue));
        when(userMapper.userToUserDto(any()))
                .thenReturn(null);

        // NullPointerException

        var result = userAuthenticationService.login(cpfOrEmail, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error in null class mapping");
    }

    //login Cpf//
    @Test
    public void shouldLoginCpfWithoutErrors(){
        String cpfOrEmail = "282.852.170-27";
        String password = "augusto123456";
        var user = new User(UUID.fromString("c242ab48-5ac4-421f-8ba2-37d83abff30c"), null, null,
                null, null,null);
        var TokenOutValue = new TokenOutValue("access_token_random", null);

        when(userRepository.getUserByCpf(any())).thenReturn(new User());
        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authenticate.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any())).thenReturn(authenticate);
        when(userPermissionService.getAllPermissionUser(any()))
                .thenReturn(ResultService.Ok("tudo certo"));
        when(tokenGenerator.generatorByEmail(any(), any(), any()))
                .thenReturn(InfoErrors.Ok(TokenOutValue));
        when(userMapper.userToUserDto(any()))
                .thenReturn(new UserDTO());

        var result = userAuthenticationService.login(cpfOrEmail, password);

        assertTrue(result.IsSuccess);
    }

    @Test
    public void shouldLoginCpfWithErrorUserNotFound(){
        String cpfOrEmail = "282.852.170-27";
        String password = "augusto123456";

        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(authenticate);
        when(userRepository.getUserByEmail(any())).thenReturn(null);

        var result = userAuthenticationService.login(cpfOrEmail, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "user not found");
    }

    @Test
    public void shouldLoginCpfWithErrorPasswordInvalid(){
        String cpfOrEmail = "282.852.170-27";
        String password = "augusto123456";

        when(userRepository.getUserByCpf(any())).thenReturn(new User());
        when(authenticate.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Usuário inexistente ou senha inválida"));

        var result = userAuthenticationService.login(cpfOrEmail, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "Usuário inexistente ou senha inválida");
    }

    @Test
    public void shouldLoginCpfWithErrorUserNotHavePermissions(){
        String cpfOrEmail = "282.852.170-27";
        String password = "augusto123456";
        var user = new User(UUID.fromString("c242ab48-5ac4-421f-8ba2-37d83abff30c"), null, null,
                null, null,null);

        when(userRepository.getUserByCpf(any())).thenReturn(new User());
        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authenticate.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any())).thenReturn(authenticate);
        when(userPermissionService.getAllPermissionUser(any()))
                .thenReturn(ResultService.Fail("not have permission"));

        var result = userAuthenticationService.login(cpfOrEmail, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "user does not have permissions");
    }

    @Test
    public void shouldLoginCpfWithErrorGenerateToken(){
        String cpfOrEmail = "282.852.170-27";
        String password = "augusto123456";
        var user = new User(UUID.fromString("c242ab48-5ac4-421f-8ba2-37d83abff30c"), null, null,
                null, null,null);

        when(userRepository.getUserByCpf(any())).thenReturn(new User());
        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authenticate.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any())).thenReturn(authenticate);
        when(userPermissionService.getAllPermissionUser(any()))
                .thenReturn(ResultService.Ok("tudo certo"));
        when(tokenGenerator.generatorByEmail(any(), any(), any()))
                .thenReturn(InfoErrors.Fail("failed to generate token"));

        var result = userAuthenticationService.login(cpfOrEmail, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "failed to generate token");
    }

    @Test
    public void shouldLoginCpfWithErrorMappingClass(){
        String cpfOrEmail = "282.852.170-27";
        String password = "augusto123456";
        var user = new User(UUID.fromString("c242ab48-5ac4-421f-8ba2-37d83abff30c"), null, null,
                null, null,null);
        var TokenOutValue = new TokenOutValue("access_token_random", null);

        when(userRepository.getUserByCpf(any())).thenReturn(new User());
        when(authenticate.isAuthenticated()).thenReturn(true);
        when(authenticate.getPrincipal()).thenReturn(user);
        when(authenticationManager.authenticate(any())).thenReturn(authenticate);
        when(userPermissionService.getAllPermissionUser(any()))
                .thenReturn(ResultService.Ok("tudo certo"));
        when(tokenGenerator.generatorByEmail(any(), any(), any()))
                .thenReturn(InfoErrors.Ok(TokenOutValue));
        when(userMapper.userToUserDto(any()))
                .thenReturn(null);

        // NullPointerException

        var result = userAuthenticationService.login(cpfOrEmail, password);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error in null class mapping");
    }

    //verify//
    @Test
    public void shouldVerifyWithoutErrors(){
        int code = 12;
        String guidId = "augusto123456";

        when(dictionaryCode.getKeyDictionary(any())).thenReturn(12);

        var result = userAuthenticationService.verify(code, guidId);

        assertTrue(result.IsSuccess);
        assertEquals(result.Message, "ok confirmed");
    }

    @Test
    public void shouldVerifyWithErrorConfirmingCode(){
        int code = 12;
        String guidId = "augusto123456";

        when(dictionaryCode.getKeyDictionary(any())).thenReturn(1);

        var result = userAuthenticationService.verify(code, guidId);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error when confirming code");
    }

    //resendCode//
    @Test
    public void shouldResendCodeWithoutErrors(){
        var user = new UserDTO(UUID.fromString("c242ab48-5ac4-421f-8ba2-37d83abff30c"), "augusto",
                "augusto@gmail.com", null, null,null);

        when(dictionaryCode.getKeyDictionary(any())).thenReturn(null);
        when(sendEmailUser.sendCodeRandom(any(), anyInt()))
                .thenReturn(new InfoErrors<String>(true, "all very well", null));

        var result = userAuthenticationService.resendCode(user);

        assertTrue(result.IsSuccess);
        assertEquals(result.Message, "all very well");
    }

    @Test
    public void shouldResendCodeWithErrorIdNull(){
        var user = new UserDTO(null, "augusto",
                "augusto@gmail.com", null, null,null);

        var result = userAuthenticationService.resendCode(user);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "guidId null");
    }

    @Test
    public void shouldResendCodeWithErrorEmailNull(){
        var user = new UserDTO(UUID.fromString("c242ab48-5ac4-421f-8ba2-37d83abff30c"), "augusto",
                null, null, null,null);

        var result = userAuthenticationService.resendCode(user);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "email or name provided null");
    }

    @Test
    public void shouldResendCodeWithErrorCpfNull(){
        var user = new UserDTO(UUID.fromString("c242ab48-5ac4-421f-8ba2-37d83abff30c"), null,
                "augusto@gmail.com", null, null,null);

        var result = userAuthenticationService.resendCode(user);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "email or name provided null");
    }

    @Test
    public void shouldResendCodeWithErrorSendCodeEmail(){
        var user = new UserDTO(UUID.fromString("c242ab48-5ac4-421f-8ba2-37d83abff30c"), "augusto",
                "augusto@gmail.com", null, null,null);

        when(dictionaryCode.getKeyDictionary(any())).thenReturn(null);
        when(sendEmailUser.sendCodeRandom(any(), anyInt()))
                .thenReturn(new InfoErrors<String>(false, "error sending email", null));

        var result = userAuthenticationService.resendCode(user);

        assertFalse(result.IsSuccess);
        assertEquals(result.Message, "error sending email");
    }
}
