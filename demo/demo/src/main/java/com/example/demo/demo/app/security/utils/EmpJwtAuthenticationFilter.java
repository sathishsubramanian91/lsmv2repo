package com.example.demo.demo.app.security.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;


public class EmpJwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private String secretKey;

    private final AuthenticationManager authenticationManager;

    String authenticationUrl ="/api/v1/login";
    public EmpJwtAuthenticationFilter(AuthenticationManager authenticationManager,String secretKey) {
        this.secretKey = secretKey;
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(authenticationUrl);
    }

    public EmpJwtAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl(authenticationUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

       String auth= request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("Authrization "+auth);
       String[] credentials = decodeCredential(auth);
       if(credentials.length != 2){
           throw new AuthenticationCredentialsNotFoundException("Not proper format");
       }
       System.out.println("decoded credential "+ credentials[0] +" password "+ credentials[1]);

    /*   String username= request.getParameter("username");
       String password= request.getParameter("password");
       System.out.println("Username "+username);
       System.out.println("Password "+ password);*/
        UsernamePasswordAuthenticationToken authenticationToken=
        new UsernamePasswordAuthenticationToken(credentials[0],credentials[1]);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        System.out.println("authResult " + authResult);
        System.out.println(" auth 1 "+ authResult.getAuthorities());
        System.out.println(" auth 2 "+ authResult.getPrincipal());
        System.out.println(" auth 3 "+ authResult.getDetails());
        System.out.println(" auth 3 "+ authResult.getName());
        System.out.println(" auth 3 "+ authResult.getCredentials());
       response.addHeader("Authorization" , JwtTokenGenerator.generateToken(authResult,secretKey,600000L));

        //        super.successfulAuthentication(request, response, chain, authResult);
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
