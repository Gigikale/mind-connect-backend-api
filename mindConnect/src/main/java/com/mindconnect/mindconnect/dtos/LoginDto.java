package com.mindconnect.mindconnect.dtos;

import com.mindconnect.mindconnect.Models.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto{
    private String emailAddress;
    private String Password;
}
