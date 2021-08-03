package com.example.demo.demo.app.user.utils;


import com.example.demo.demo.app.security.utils.AuthenticationManangerWrapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Data
public class AuthenticationUtils {

    @Value("${emp.secret.key}")
    private String scretKey;


    @Autowired
    private AuthenticationManangerWrapper authenticationManangerWrapper;

    public Authentication authenticate(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken){
        Authentication authentication=null;
        try {
            authentication= authenticationManangerWrapper.authenticate(usernamePasswordAuthenticationToken);

        }catch(BadCredentialsException badCredentialsException) {
            throw new BadCredentialsException("INVALID CREDENTIALS ", badCredentialsException);
        }

        return  authentication;
    }


}
