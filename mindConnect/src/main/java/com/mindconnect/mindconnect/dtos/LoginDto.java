package com.mindconnect.mindconnect.dtos;

import com.mindconnect.mindconnect.Models.User;
import lombok.*;

public record LoginDto(String emailAddress, String Password){
}
