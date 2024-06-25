package com.ecom.AuthService.service;

import com.ecom.AuthService.dto.UserDTO;
import com.ecom.AuthService.entity.Session;
import com.ecom.AuthService.entity.SessionStatus;
import com.ecom.AuthService.entity.User;
import com.ecom.AuthService.repository.AuthRepository;
import com.ecom.AuthService.repository.SessionRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User signup(String email, String password){
        Optional<User> user = authRepository.findByEmail(email);
        if(user.isEmpty()){
            User us = new User();
            us.setEmail(email);
            us.setPassword(bCryptPasswordEncoder.encode(password));
            User savedUser = authRepository.save(us);
            return savedUser;
        }
        else{
            return user.get();
        }

    }

    public Pair<User, MultiValueMap<String, String>> login(String email, String password){
        Optional<User> fetchedUser = authRepository.findByEmail(email);
        User user = fetchedUser.get();
        if(fetchedUser.isEmpty()){
            return null;
        }

        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            return null;
        }

        Map<String, Object> hm = new HashMap<>();
        hm.put("email", user.getEmail());
        hm.put("role", user.getRoles());
        hm.put("expiryAt", new Date(System.currentTimeMillis()+10000));
        hm.put("createdAt", new Date(System.currentTimeMillis()));


        MacAlgorithm algorithm = Jwts.SIG.HS256;
        SecretKey key = algorithm.key().build();
        String token = Jwts.builder().claims(hm).signWith(key).compact();

        Session session = new Session();
        session.setExpiry(new Date(System.currentTimeMillis()+10000));
        session.setUser(user);
        session.setStatus(SessionStatus.ACTIVE);
        session.setToken(token);

        sessionRepository.save(session);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(HttpHeaders.SET_COOKIE,token);
        Pair<User,MultiValueMap<String,String>> result = new Pair<>(user,headers);
        return result;


    }
}
