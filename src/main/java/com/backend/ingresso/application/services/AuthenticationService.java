package com.backend.ingresso.application.services;

import com.backend.ingresso.domain.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    private final IUserRepository userRepository;

    @Autowired
    public AuthenticationService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String cpfOrEmail) throws UsernameNotFoundException {
        if(cpfOrEmail.contains("@")){
            return userRepository.getUserByEmailInfoEmailPasswordHash(cpfOrEmail);
        }else{
            return userRepository.getUserByCpf(cpfOrEmail);
        }
    }
}