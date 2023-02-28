package com.example.librarysecurity.controller;

import com.example.librarysecurity.entity.ApplicationUserEntity;
//import com.example.librarysecurity.feign.FeignInterface;
import com.example.librarysecurity.feign.StudentFeignInterface;
import com.example.librarysecurity.jwt.JwtTokenProvider;
import com.example.librarysecurity.model.ApplicationUser;
import com.example.librarysecurity.model.RegisterRequest;
import com.example.librarysecurity.service.ApplicationUserDetailsService;
import com.example.librarysecurity.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/security")
public class SecurityController {
    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationService authenticationService;
    private final StudentFeignInterface studentFeignInterface;

    @Autowired
    public SecurityController(ApplicationUserDetailsService applicationUserDetailsService, JwtTokenProvider jwtTokenProvider, AuthenticationService authenticationService, StudentFeignInterface studentFeignInterface) {
        this.applicationUserDetailsService = applicationUserDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationService = authenticationService;
        this.studentFeignInterface = studentFeignInterface;
    }

    @GetMapping("{username}")
    public ApplicationUserEntity getUserByUsername(@PathVariable String username){
        return applicationUserDetailsService.getUserByUsername(username);
    }

    @GetMapping("/token/refresh")
    public void getRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        jwtTokenProvider.provideNewAccessToken(request,response);
    }

    @PostMapping("/user/register")
    public ApplicationUser registerUser(@RequestBody RegisterRequest registerRequest){
        return authenticationService.registerUser(registerRequest);
    }

}
