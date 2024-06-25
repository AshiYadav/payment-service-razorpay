package com.ecom.AuthService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class User extends BaseModel{

    private String email;
    private String password;
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;
    @ManyToMany
    private Set<Role> roles = new HashSet<>();

}
