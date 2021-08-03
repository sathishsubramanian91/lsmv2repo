package com.example.demo.demo.app.repo;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Data
@NoArgsConstructor
public class BaseId  implements Serializable {

    private static final long serialVersionUID = -3293902347995131773L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
}
