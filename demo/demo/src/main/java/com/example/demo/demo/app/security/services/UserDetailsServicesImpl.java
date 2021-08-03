package com.example.demo.demo.app.security.services;

import com.example.demo.demo.app.user.entity.UserDetailEntity;
import com.example.demo.demo.app.user.repo.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserDetailsServicesImpl implements UserDetailsService {



    @Autowired
    UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("load username "+ s);
      Optional<UserDetailEntity> appUserOptional =userDetailRepository.findByUsername(s);
      if(appUserOptional.isPresent()){
          System.out.println("App user is presetn ");
      }else{
          System.out.println("App user i snot found ");
      }

        appUserOptional.orElseThrow(()-> new UsernameNotFoundException(" Not Found "+ s));
        UserDetailEntity appUser= appUserOptional.get();
        if(appUser.getIsActivated() == false || appUser.getIsActive() == false){
            throw new UsernameNotFoundException("User either is non-active user or account is not activated");
        }

      return  new AppUserDetails(appUser.getFirstname(),
                appUser.getLastname(),
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.getIsActivated(),
                appUser.getIsActive(),
                Arrays.asList(new SimpleGrantedAuthority(appUser.getRole()))
                );
    }

}
