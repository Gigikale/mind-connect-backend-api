package com.mindconnect.mindconnect.dtos;

public record ChangePasswordDto(String oldPassword, String newPassword, String confirmPassword) {
}
