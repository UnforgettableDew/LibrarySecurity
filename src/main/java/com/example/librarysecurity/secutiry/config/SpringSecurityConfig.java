package com.example.librarysecurity.secutiry.config;

import com.example.librarysecurity.jwt.exception_handler.CustomAccessDeniedHandler;
import com.example.librarysecurity.jwt.exception_handler.CustomAuthenticationEntryPoint;
import com.example.librarysecurity.jwt.JwtService;
import com.example.librarysecurity.jwt.JwtTokenProvider;
import com.example.librarysecurity.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.example.librarysecurity.jwt.JwtVerifierToken;
import com.example.librarysecurity.service.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.librarysecurity.secutiry.enums.ApplicationUserRole.ADMIN;
import static com.example.librarysecurity.secutiry.enums.ApplicationUserRole.MODERATOR;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserDetailsService applicationUserDetailsService;
    private final JwtService jwtService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SpringSecurityConfig(PasswordEncoder passwordEncoder,
                                ApplicationUserDetailsService applicationUserDetailsService,
                                JwtService jwtService,
                                JwtTokenProvider jwtTokenProvider) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserDetailsService = applicationUserDetailsService;
        this.jwtService = jwtService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, jwtTokenProvider))
//                .addFilterBefore(new JwtVerifierToken(jwtConfig), UsernamePasswordAuthenticationFilter.class)
//
//                .authorizeRequests()
//                .antMatchers("/api/v1/security/token/refresh").permitAll()
//                .antMatchers("/api/v1/security/**").hasRole(ADMIN.name())
//                .anyRequest()
//                .authenticated();
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtService, jwtTokenProvider))
                .addFilterBefore(new JwtVerifierToken(jwtService, applicationUserDetailsService), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/v1/security/token/refresh",
                        "/api/v1/security/user/register").permitAll()
//                .antMatchers(HttpMethod.DELETE, "/api/v1/library/students/delete/**")
//                    .hasRole(ADMIN.name())
//                .antMatchers(HttpMethod.POST, "/api/v1/library/students/add")
//                    .hasAnyRole(ADMIN.name(), MODERATOR.name())
//                .antMatchers(HttpMethod.PUT, "/api/v1/library/students/update")
//                    .hasRole(ADMIN.name())
//                .antMatchers(HttpMethod.GET, "/api/v1/library/students/list",
//                        "/api/v1/library/students/book/**",
//                        "/api/v1/library/students/get/**")
//                    .hasAnyRole(ADMIN.name(), MODERATOR.name())
                .antMatchers("/api/v1/security/**").hasRole(ADMIN.name())
                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(applicationUserDetailsService);
        return daoAuthenticationProvider;
    }

}
