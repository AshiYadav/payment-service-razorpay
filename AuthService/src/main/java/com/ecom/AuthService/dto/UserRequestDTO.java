package com.ecom.AuthService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    private String email;
    private String password;
}
