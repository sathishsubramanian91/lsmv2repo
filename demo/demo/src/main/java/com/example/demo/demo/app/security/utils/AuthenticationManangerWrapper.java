package com.example.demo.demo.app.security.utils;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;


public class AuthenticationManangerWrapper {

    private final AuthenticationManager authenticationManager;

    public AuthenticationManangerWrapper(AuthenticationManager authenticationManager){
        this.authenticationManager =authenticationManager;
    }
    public Authentication authenticate(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken){
      return  authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
