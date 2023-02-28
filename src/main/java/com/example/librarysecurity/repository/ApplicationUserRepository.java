package com.example.librarysecurity.repository;

import com.example.librarysecurity.entity.ApplicationUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUserEntity, Integer> {
    Optional<ApplicationUserEntity> findByUsername(String username);
    boolean existsByUsername(String username);
}
