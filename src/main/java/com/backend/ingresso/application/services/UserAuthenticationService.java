package com.backend.ingresso.application.services;

import com.backend.ingresso.application.dto.UserDTO;
import com.backend.ingresso.application.mappings.MappingClassInterface.IUserMapper;
import com.backend.ingresso.application.mappings.MappingClassInterface.IUserPermissionMapper;
import com.backend.ingresso.application.services.interfaces.IUserAuthenticationService;
import com.backend.ingresso.application.services.interfaces.IUserPermissionService;
import com.backend.ingresso.application.util.interfaces.IDictionaryCode;
import com.backend.ingresso.data.utilityExternal.Interface.ISendEmailUser;
import com.backend.ingresso.domain.Authentication.ITokenGenerator;
import com.backend.ingresso.domain.Authentication.TokenOutValue;
import com.backend.ingresso.domain.InfoErrors.InfoErrors;
import com.backend.ingresso.domain.entities.User;
import com.backend.ingresso.domain.entities.UserPermission;
import com.backend.ingresso.domain.repositories.IUserRepository;
import com.backend.ingresso.domain.validations.DomainValidationException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class UserAuthenticationService implements IUserAuthenticationService {
    private final IUserRepository userRepository;
    private final IUserPermissionService userPermissionService;
    private final IUserPermissionMapper userPermissionMapper;
    private final AuthenticationManager authenticationManager;
    private final ITokenGenerator tokenGenerator;
    private final IUserMapper userMapper;
    private final ISendEmailUser sendEmailUser;
//    private static Dictionary<String, Integer> dictionaryCode;
    private final IDictionaryCode dictionaryCode;
    @Autowired
    public UserAuthenticationService(IUserRepository userRepository, IUserPermissionService userPermissionService,
                                     IUserPermissionMapper userPermissionMapper, IUserMapper userMapper,
                                     AuthenticationManager authenticationManager, ITokenGenerator tokenGenerator,
                                     ISendEmailUser sendEmailUser, IDictionaryCode dictionaryCode) {
        this.userRepository = userRepository;
        this.userPermissionService = userPermissionService;
        this.authenticationManager = authenticationManager;
        this.tokenGenerator = tokenGenerator;
        this.userPermissionMapper = userPermissionMapper;
        this.userMapper = userMapper;
        this.sendEmailUser = sendEmailUser;
        this.dictionaryCode = dictionaryCode;
    }

    @Override
    @Transactional
    public ResultService<UserDTO> login(String cpfOrEmail, String password) {
//        String cpfOrEmailTest = "123.456.789";
        String regex = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";
        Pattern pattern = Pattern.compile(regex);

        try {
            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(cpfOrEmail, password);
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            if(cpfOrEmail.contains("@")){
                var user = userRepository.getUserByEmail(cpfOrEmail);
                if(user == null)
                    return ResultService.Fail("user not found");

                return loginEmailOrCpf(user, password, authenticate, "Email");

            }else if(pattern.matcher(cpfOrEmail).matches()){
                var user = userRepository.getUserByCpf(cpfOrEmail);
                if(user == null)
                    return ResultService.Fail("user not found");

                return loginEmailOrCpf(user, password, authenticate, "Cpf");

            }
        }catch (Exception ex){
            String message = ex.getMessage();
            return ResultService.Fail(message);
        }


        return ResultService.Fail("error email or cpf not informed");
    }

    private ResultService<UserDTO> loginEmailOrCpf(User user, String password, Authentication authenticate, String emailOrCpf) throws DomainValidationException {
        var resultUserPermissions = userPermissionService.getAllPermissionUser(user.getId());

        if(!resultUserPermissions.IsSuccess)
            return ResultService.Fail("user does not have permissions");

        List<UserPermission> userPermission = userPermissionMapper.UserPermissionDtoToUserPermission(resultUserPermissions.Data);

        User userAuth = (User) authenticate.getPrincipal();

        InfoErrors<TokenOutValue> tokenOut = tokenGenerator.generatorByEmail(userAuth, userPermission, emailOrCpf);

        if(!tokenOut.IsSuccess)
            return ResultService.Fail(tokenOut.Message);

        int randomCode = generateRandomNumber();
        dictionaryCode.putKeyValueDictionary(userAuth.getId().toString(), randomCode);
        //InfoErrors<String> resultSendCodeEmail = sendEmailUser.sendCodeRandom(userAuth, randomCode);

        userAuth.setName(user.getName());
        UserDTO userDTO = userMapper.userToUserDto(userAuth);

        if(userDTO == null)
            return ResultService.Fail("error in null class mapping");

        userDTO.setToken(tokenOut.Data.getAccess_Token());
        //userDTO.setCode(randomCode); for test
        //userDTO.setEmailSendSuccessfully(resultSendCodeEmail.IsSuccess);

        return ResultService.Ok(userDTO);
    }

    @Override
    @Transactional
    public ResultService<String> verify(int code, String guidId) {
        //Descomentar depois quando for mandar CODIGO para Email
//        if(dictionaryCode.getKeyDictionary(guidId) == code){
//            dictionaryCode.removeKeyDictionary(guidId);
//            return ResultService.Ok("ok confirmed");
//        }else {
//            return ResultService.Fail("error when confirming code");
//        }
        return ResultService.Ok("ok confirmed");
    }

    @Override
    @Transactional
    public ResultService<String> resendCode(UserDTO user) {
        if(user.getId() == null)
            return ResultService.Fail("guidId null");

        if(user.getEmail() == null || user.getName() == null)
            return ResultService.Fail("email or name provided null");

        var guidId = user.getId().toString();

        if(dictionaryCode.getKeyDictionary(guidId) != null)
            dictionaryCode.removeKeyDictionary(guidId);

        int randomCode = generateRandomNumber();
        dictionaryCode.putKeyValueDictionary(guidId, randomCode);

        InfoErrors<String> resultSendCodeEmail = sendEmailUser.sendCodeRandom(
                userMapper.userDtoToUser(user), randomCode);

        if(resultSendCodeEmail.IsSuccess){
            return ResultService.Ok(resultSendCodeEmail.Message);
        }else {
            return ResultService.Fail(resultSendCodeEmail.Message);
        }
    }

    private static int generateRandomNumber(){
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }
}
