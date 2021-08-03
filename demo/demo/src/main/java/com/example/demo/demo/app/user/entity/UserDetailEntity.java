package com.example.demo.demo.app.user.entity;


import com.example.demo.demo.app.repo.BaseId;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
public class UserDetailEntity extends BaseId {
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String role;
    private Boolean isActivated;
    private Boolean isActive;
}
