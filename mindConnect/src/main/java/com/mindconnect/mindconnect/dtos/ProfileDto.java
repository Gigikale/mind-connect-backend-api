package com.mindconnect.mindconnect.dtos;

public record ProfileDto(
        String firstName,
        String lastName,
        String country,
        String state,
        String gender,
        String mentalCondition,
        String username
) {
}
