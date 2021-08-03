package com.example.demo.demo.app.user.controller;


import com.example.demo.demo.app.dto.JwtToken;
import com.example.demo.demo.app.dto.SignUpRequest;
import com.example.demo.demo.app.security.services.AppUserDetails;
import com.example.demo.demo.app.user.utils.AuthenticationUtils;
import com.example.demo.demo.app.user.services.UserServices;
import com.example.demo.demo.app.security.utils.JwtTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private AuthenticationUtils authenticationUtils;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignUpRequest signUpRequest){

       Boolean isSucces= userServices.register(signUpRequest);
       if(isSucces)
           return ResponseEntity.status(HttpStatus.CREATED).body(isSucces);
       return ResponseEntity.status(HttpStatus.NO_CONTENT).body(isSucces);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
        System.out.println("Authorization "+header);
        if(header == null){
            throw new BadCredentialsException("No Header Found");
        }
        String[] credentials = decodeCredential(header);
        if(credentials.length != 2){
            throw new AuthenticationCredentialsNotFoundException("Authorization Header is not in proper format");
        }
        System.out.println("decoded credential "+ credentials[0] +" password "+ credentials[1]);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(credentials[0],
                credentials[1] );
        Authentication authentication=authenticationUtils.authenticate(usernamePasswordAuthenticationToken);
        System.out.println("#SS Authenticatd password "+authentication);
        System.out.println("#SS 1 "+(AppUserDetails)authentication.getPrincipal());
        System.out.println("#SS 2 "+ authentication.getAuthorities());

        String token=JwtTokenGenerator.generateToken(authentication,authenticationUtils.getScretKey(),60000L);

       return ResponseEntity.ok(new JwtToken(token));
    }



    private String[] decodeCredential(String authHeaderValue) {
        try {

            if (authHeaderValue.startsWith("Basic ")) {
                byte[] decode = Base64.getDecoder().decode(authHeaderValue.substring(6));
                String credentials = new String(decode);
                return credentials.split(":");
            }
            return new String[0];
        } catch (Throwable e) {
            System.out.println("Error parsing credentials from header: ");
            return new String[0];
        }
    }



}
