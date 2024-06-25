package com.ecom.AuthService.controller;

import com.ecom.AuthService.dto.UserDTO;
import com.ecom.AuthService.dto.UserRequestDTO;
import com.ecom.AuthService.entity.User;
import com.ecom.AuthService.service.AuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("auth/signup")
    private ResponseEntity<UserDTO> signup(@RequestBody UserRequestDTO userRequestDTO){
        return new ResponseEntity(authService.signup(userRequestDTO.getEmail(), userRequestDTO.getPassword()), HttpStatus.OK);
    }

    @PostMapping("auth/login")
    private ResponseEntity<UserDTO> login(@RequestBody UserRequestDTO userRequestDTO){
        try {
            Pair<User, MultiValueMap<String, String>> pair = authService.login(userRequestDTO.getEmail(), userRequestDTO.getPassword());
            UserDTO dto = convertLoginResponseToDTO(pair.a);

            return new ResponseEntity<>(dto, pair.b, HttpStatus.OK);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    public UserDTO convertLoginResponseToDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setEmail(user.getEmail());
        //dto.setRoleList(user.getRoles());
        return dto;

    }
}
