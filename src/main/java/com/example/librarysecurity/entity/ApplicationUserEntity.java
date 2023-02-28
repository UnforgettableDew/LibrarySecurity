package com.example.librarysecurity.entity;

import com.example.librarysecurity.secutiry.enums.ApplicationUserRole;
import com.example.librarysecurity.secutiry.enums.ApplicationUserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "application_users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApplicationUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private ApplicationUserRole role;
    @Enumerated(value = EnumType.STRING)
    private ApplicationUserStatus status;
}
