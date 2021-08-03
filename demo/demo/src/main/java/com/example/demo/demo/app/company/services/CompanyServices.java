package com.example.demo.demo.app.company.services;


import com.example.demo.demo.app.dto.CompanyDetails;
import com.example.demo.demo.app.company.repo.CompanyDetailsRepository;
import com.example.demo.demo.app.company.entity.CompanyEntity;
import com.example.demo.demo.app.user.entity.UserDetailEntity;
import com.example.demo.demo.app.user.repo.UserDetailRepository;
import com.example.demo.demo.app.security.services.AppUserDetails;
import com.example.demo.demo.app.util.ConvertToEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CompanyServices {

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ConvertToEntity convertToEntity;

    @Autowired
    CompanyDetailsRepository companyDetailsRepository;

    public CompanyDetails register(CompanyDetails companyDetails) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUserDetails user = (AppUserDetails) authentication.getPrincipal();

        UserDetailEntity userDetailEntity = userDetailRepository.findByUsername(user.getUsername()).get();
        CompanyEntity companyEntity = convertToEntity.convert(companyDetails);
        companyEntity.setUserDetailEntity(userDetailEntity);

        CompanyEntity companyEntity1 = companyDetailsRepository.saveAndFlush(companyEntity);

        return convertToEntity.convert(companyEntity1);
    }

}
