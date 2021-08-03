package com.example.demo.demo.app.company.entity;

import com.example.demo.demo.app.company.util.CompanyPrefixSequenceIdGenerator;
import com.example.demo.demo.app.repo.BaseId;
import com.example.demo.demo.app.user.entity.UserDetailEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@AllArgsConstructor
public class CompanyEntity extends BaseId {

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "org_seq")
    @GenericGenerator(
            name = "org_seq",
            strategy = "com.example.demo.demo.app.company.util.CompanyPrefixSequenceIdGenerator",
            parameters = {
                    @Parameter(name = CompanyPrefixSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = CompanyPrefixSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "ORG_"),
                    @Parameter(name = CompanyPrefixSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%05d") })
    @Column(columnDefinition = "Text")
    private String uniqueId;
    private String companyName;
    private String gstNumber;
    private String panNumber;
    private String address;
    private String city;
    private String district;
    private String state;
    private String country;
    private String contactPerson;
    private String contactNumber;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    private UserDetailEntity userDetailEntity;



}
