package com.twinkles.simpoprojectjava.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "AppUser")
@Data
@Builder
public class AppUser {
    private String id;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String date_of_birth;
    private String BVN;
    private String username;
    private String address;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private String profileImageUrl;
    private String password;
    private String nationality;
    private boolean hasVotedForPresident;
    private boolean hasVotedForGovernor;
    private boolean hasVotedForHouseOfRepMember;
    private boolean hasVotedForSenateMember;
    private boolean hasVotedForHouseOfAssemblyMember;

}
