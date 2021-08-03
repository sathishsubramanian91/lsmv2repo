package com.example.demo.demo.app.user.services;

import com.example.demo.demo.app.dto.SignUpRequest;
import com.example.demo.demo.app.repo.AppUserRepositry;
import com.example.demo.demo.app.user.entity.UserDetailEntity;
import com.example.demo.demo.app.user.repo.UserDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServicesImp implements UserServices {

    @Autowired
    private AppUserRepositry appUserRepositry;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public boolean register(SignUpRequest signUpRequest) {
        Optional<UserDetailEntity> userDetailOptional = userDetailRepository.findByUsername(signUpRequest.getUsername());
        if (userDetailOptional.isPresent())
            throw new RuntimeException("User exists already");

        UserDetailEntity userDetailEntity =
                userDetailRepository.saveAndFlush(
                        new UserDetailEntity(
                                signUpRequest.getFirstname(),
                                signUpRequest.getLastname(),
                                signUpRequest.getUsername(),
                                passwordEncoder.encode(signUpRequest.getPassword()),
                                signUpRequest.getRole(),
                                false,
                                true));

        return true;
    }
}



