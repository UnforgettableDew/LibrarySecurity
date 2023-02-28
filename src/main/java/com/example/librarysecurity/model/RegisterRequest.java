package com.example.librarysecurity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String confirmedPassword;
}
