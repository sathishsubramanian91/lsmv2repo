package com.example.demo.demo.app.security.utils;

import com.example.demo.demo.app.security.services.AppUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class EmpJwtAuthorizationFilter extends BasicAuthenticationFilter {

    String secretKey;

    public EmpJwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public EmpJwtAuthorizationFilter(AuthenticationManager authenticationManager,String secretKey) {
        super(authenticationManager);
        this.secretKey = secretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        if (authentication == null) {
            chain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println(" SecurityContextHolder "+ SecurityContextHolder.getContext().getAuthentication());
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        System.out.println("#SS GetAuthentcation  "+ token);
        if (StringUtils.isNotEmpty(token) && token.startsWith("Bearer ")) {
            System.out.println("Signed version ");
            try {
                byte[] signingKey = secretKey.getBytes();

                Jws<Claims> parsedToken = Jwts.parser()
                        .setSigningKey(signingKey)
                        .parseClaimsJws(token.replace("Bearer ", ""));

                String username = parsedToken
                        .getBody()
                        .getSubject();

                System.out.println("#SS parsed token "+ parsedToken.getBody());

                String firstname="";
                String lastname="";
                String password="";
                Boolean isActivated=false;
                Boolean isActive=false;
                if(parsedToken.getBody().get("firstname") != null)
                    firstname = parsedToken.getBody().get("firstname").toString();
                if(parsedToken.getBody().get("lastname") != null)
                    lastname = parsedToken.getBody().get("lastname").toString();
                if(parsedToken.getBody().get("password") != null)
                    password = parsedToken.getBody().get("password").toString();
                if(parsedToken.getBody().get("isActivated") != null)
                    isActivated = (Boolean) parsedToken.getBody().get("isActivated");
                if(parsedToken.getBody().get("isActive") != null)
                    isActive = (Boolean) parsedToken.getBody().get("isActive");

                List authorities = ((List<?>) parsedToken.getBody()
                        .get("rol")).stream()
                        .map(authority -> new SimpleGrantedAuthority((String) authority))
                        .collect(Collectors.toList());


                if (StringUtils.isNotEmpty(username)) {
                    System.out.println("Returning app user details ");
                    AppUserDetails principal = new AppUserDetails(
                            firstname,lastname,username,password,isActivated,isActive,authorities);
                    return new UsernamePasswordAuthenticationToken(principal, null, authorities);
                }
            } catch (ExpiredJwtException exception) {
                log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
            } catch (UnsupportedJwtException exception) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
            } catch (MalformedJwtException exception) {
                log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
            } catch (SignatureException exception) {
                log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
            } catch (IllegalArgumentException exception) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
            }


        }
        return null;
    }
}
