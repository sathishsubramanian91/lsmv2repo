package com.example.demo.demo.app.security.utils;

import com.example.demo.demo.app.security.services.AppUserDetails;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenGenerator {

    public static String generateToken(Authentication authResult, String secretKey, Long timeout) {
        String username = authResult.getName();

        List roles = authResult.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        byte[] signingKey = secretKey.getBytes();

        JwtBuilder builder = Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setHeaderParam("typ", "JWT")
                .setIssuer("emp-api")
                .setAudience("emp-app")
                .setSubject(username)
//                .setExpiration(new Date(System.currentTimeMillis() + timeout))
                .claim("rol", roles);

        if (timeout != null){
            // if no timeout is given, generated token never expires (use with caution)
            builder.setExpiration(new Date(System.currentTimeMillis() + timeout));
        }

        if ( authResult.getPrincipal() instanceof AppUserDetails){
            builder.claim("username" , ((AppUserDetails) authResult.getPrincipal()).getUsername());
            builder.claim("firstname" , ((AppUserDetails) authResult.getPrincipal()).getFirstname());
            builder.claim("lastname" , ((AppUserDetails) authResult.getPrincipal()).getLastname());
            builder.claim("isActivated" , ((AppUserDetails) authResult.getPrincipal()).getIsActivated());
            builder.claim("isActive" , ((AppUserDetails) authResult.getPrincipal()).getIsActive());
            builder.claim("password" , ((AppUserDetails) authResult.getPrincipal()).getPassword());

        }

        String token = builder.compact();
        System.out.println("Generated tocken "+ token);
        return "Bearer "+token;
    }
}
