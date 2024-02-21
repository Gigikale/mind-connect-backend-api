package com.mindconnect.mindconnect.dtos;

import jakarta.persistence.Column;

public record GroupRequestDto(
        @Column(unique = true)
        String name,
        String about
) {
}
