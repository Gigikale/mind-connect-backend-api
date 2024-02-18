package com.mindconnect.mindconnect.dtos;

public record LoginResponse(
        String firstName,
        String lastName,
        String token
        ) {
}
