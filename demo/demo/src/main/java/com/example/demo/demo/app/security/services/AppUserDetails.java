package com.example.demo.demo.app.security.services;

import com.example.demo.demo.app.repo.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public class AppUserDetails  implements UserDetails {

    private String username;
    private String password;
    private Boolean isActivated;
    private Boolean isActive;
    private String firstname;
    private String lastname;

    private List<SimpleGrantedAuthority> authorityList;

    public AppUserDetails(){

    }

    public AppUserDetails(String firstname,String lastname,String username, String password,Boolean isActivated,Boolean isActive, List<SimpleGrantedAuthority> authorityList){
        this.username = username;
        this.password =password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.isActivated = isActivated;
        this.isActive = isActive;
        this.authorityList = authorityList;
    }

    public Boolean getIsActivated() {
        return isActivated;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorityList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
