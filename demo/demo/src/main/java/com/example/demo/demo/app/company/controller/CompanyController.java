package com.example.demo.demo.app.company.controller;


import com.example.demo.demo.app.dto.CompanyDetails;
import com.example.demo.demo.app.company.services.CompanyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comp")
public class CompanyController {

    @Autowired
    private CompanyServices companyServices;

    @GetMapping
    public ResponseEntity getIndex(){
        return new ResponseEntity("It worked!!",HttpStatus.ACCEPTED);
    }



    @PostMapping("/register")
    public ResponseEntity<CompanyDetails> register(@RequestBody CompanyDetails companyDetails){

       CompanyDetails registerd = companyServices.register(companyDetails);
       return ResponseEntity.status(HttpStatus.CREATED).body(registerd);

    }

}
