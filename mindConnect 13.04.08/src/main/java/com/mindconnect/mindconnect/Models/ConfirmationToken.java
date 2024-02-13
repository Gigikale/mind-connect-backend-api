package com.mindconnect.mindconnect.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "confirm_tokens")
@Table(name = "confirm_tokens")
@Getter
@Setter
public class ConfirmationToken extends BaseEntity{
    @Column(name = "token")
    @NotEmpty(message = "Token should not be empty")
    @Size(min = 6, message = "Token should not be less than 6 digits")
    private String token;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt = null;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @OneToOne
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "token_user_fkey")
    )
    private User user;
}
