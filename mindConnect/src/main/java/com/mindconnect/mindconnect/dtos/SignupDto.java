package com.mindconnect.mindconnect.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

public record SignupDto(
        @NotEmpty(message = "First name cannot be empty")
        String firstName,
        @NotEmpty(message = "Last name cannot be empty")
        String lastName,
        @NotEmpty(message = "Email cannot be empty")
        @Email(message = "Email is not properly formatted")
        String emailAddress,
        String mentalCondition,
        @NotEmpty(message = "Country cannot be empty")
        String country,
        @NotEmpty(message = "State/Province cannot be empty")
        String state,
        @NotEmpty(message = "Password cannot be empty")
        String Password,
        @NotEmpty(message = "Confirm password cannot be empty")
        String repeatPassword,
        String gender
) {
}
