package com.ecom.AuthService.repository;

import com.ecom.AuthService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthRepository extends JpaRepository<User, UUID> {

    public Optional<User> findByEmail(String email);
}
