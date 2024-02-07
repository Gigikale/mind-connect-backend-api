package com.mindconnect.mindconnect.services.implementations;

import com.mindconnect.mindconnect.dtos.LoginDto;
import com.mindconnect.mindconnect.dtos.LoginResponse;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.UserRepository;
import com.mindconnect.mindconnect.services.AuthServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthImplementation implements AuthServices {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtImplementation jwtImplementation;


    public ResponseEntity<ApiResponse<LoginResponse>> login(LoginDto loginDto){
        var user = userRepository.findByEmailAddress(loginDto.getEmailAddress()).orElseThrow(()-> new MindConnectException("User not found"));
        if (user == null){
            throw new UsernameNotFoundException("User wasn't found");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmailAddress(),
                        loginDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        var jwtToken = jwtImplementation.generateToken(user);

        LoginResponse loginResponse = new LoginResponse(
                user.getFirstName(),
                user.getLastName(),
                jwtToken
        );
        ApiResponse<LoginResponse> response = new ApiResponse<>(
                loginResponse,
                "Login successful"
        );
        return new ResponseEntity<>(
                response,
                response.getStatus()
        );
    }
}
