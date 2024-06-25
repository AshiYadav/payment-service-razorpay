package com.ecom.AuthService.dto;

import com.ecom.AuthService.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {
    private String email;
    private List<Role> roleList;
}
