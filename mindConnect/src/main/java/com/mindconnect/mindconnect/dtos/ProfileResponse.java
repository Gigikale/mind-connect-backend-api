package com.mindconnect.mindconnect.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponse {
    private String firstName;
    private String lastName;
    private String country;
    private String state;
    private String emailAddress;
    private String profilePicture;
    private String username;
    private String gender;
    private String mentalCondition;
}
