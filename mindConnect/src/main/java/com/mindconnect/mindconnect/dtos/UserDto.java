package com.mindconnect.mindconnect.dtos;

import com.mindconnect.mindconnect.Models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public record UserDto (
    @NotEmpty(message = "First name should not be empty") String firstName,
    @NotEmpty(message = "Last name should not be empty") String lastName,
    @NotEmpty(message = "Password should not be empty") String password,
    @Email(message = "Email must be properly formatted") String emailAddress
) implements Serializable {
}
