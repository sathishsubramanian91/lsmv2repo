package com.example.demo.demo.app.util;

import com.example.demo.demo.app.dto.CompanyDetails;
import com.example.demo.demo.app.company.entity.CompanyEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConvertToEntity {

    @Autowired
    ModelMapper modelMapper;

    public CompanyEntity convert(CompanyDetails companyDetails){
        CompanyEntity  companyEntity = modelMapper.map(companyDetails,CompanyEntity.class);
        return companyEntity;
    }


    public CompanyDetails convert(CompanyEntity companyEntity){
        CompanyDetails companyDetails= modelMapper.map(companyEntity,CompanyDetails.class);
        return companyDetails;
    }

}
