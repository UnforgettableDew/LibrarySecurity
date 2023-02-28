package com.example.librarysecurity.service;

import com.example.librarysecurity.model.ApplicationUser;
import com.example.librarysecurity.entity.ApplicationUserEntity;
import com.example.librarysecurity.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    private final ApplicationUserRepository applicationUserRepository;

    @Autowired
    public ApplicationUserDetailsService(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return ApplicationUser.parseEntityUser(applicationUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username=" + username + " not found")));
    }

    public ApplicationUserEntity getUserByUsername(String username){
        return applicationUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username=" + username + " not found"));
    }
}
