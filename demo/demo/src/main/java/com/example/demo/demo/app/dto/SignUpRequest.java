package com.example.demo.demo.app.dto;

import lombok.Data;

@Data
public class SignUpRequest {

    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String role;

}
