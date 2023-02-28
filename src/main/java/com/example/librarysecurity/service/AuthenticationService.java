package com.example.librarysecurity.service;

import com.example.librarysecurity.entity.ApplicationUserEntity;
import com.example.librarysecurity.exception.InvalidPasswordException;
import com.example.librarysecurity.exception.UsernameAlreadyExistsException;
import com.example.librarysecurity.model.ApplicationUser;
import com.example.librarysecurity.model.RegisterRequest;
import com.example.librarysecurity.repository.ApplicationUserRepository;
import com.example.librarysecurity.secutiry.enums.ApplicationUserRole;
import com.example.librarysecurity.secutiry.enums.ApplicationUserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ApplicationUser registerUser(RegisterRequest registerRequest){
        if(applicationUserRepository.existsByUsername(registerRequest.getUsername()))
            throw new UsernameAlreadyExistsException("Username: '" + registerRequest.getUsername() + "' already exists");

        if(!registerRequest.getPassword().equals(registerRequest.getConfirmedPassword()))
            throw new InvalidPasswordException("Password: '" + registerRequest.getPassword()
            + "' does not match with confirmed password: '" + registerRequest.getConfirmedPassword() + "'");

        ApplicationUserEntity applicationUserEntity = ApplicationUserEntity.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getConfirmedPassword()))
                .role(ApplicationUserRole.DEFAULT)
                .status(ApplicationUserStatus.ACTIVE)
                .build();

        applicationUserRepository.save(applicationUserEntity);
        return ApplicationUser.parseEntityUser(applicationUserEntity);
    }
}
