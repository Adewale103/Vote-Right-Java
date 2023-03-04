package com.twinkles.simpoprojectjava.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    private String bvn;
    private String first_name;
    private String email;
    private String middle_name;
    private String last_name;
    private String date_of_birth;
    private String phone_number;
    private String registration_date;
    private String enrollment_bank;
    private String enrollment_branch;
    private String image_base_64;
    private String address;
    private String gender;
    private String watch_listed;
    private String nationality;
    private String marital_status;
    private String state_of_residence;
    private String lga_of_residence;
    private String image;
}
