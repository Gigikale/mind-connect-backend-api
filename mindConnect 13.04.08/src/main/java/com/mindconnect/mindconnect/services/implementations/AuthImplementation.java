package com.mindconnect.mindconnect.services.implementations;

import com.mindconnect.mindconnect.Models.User;
import com.mindconnect.mindconnect.dtos.LoginDto;
import com.mindconnect.mindconnect.dtos.LoginResponse;
import com.mindconnect.mindconnect.dtos.SignupDto;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.UserRepository;
import com.mindconnect.mindconnect.services.AuthServices;
import com.mindconnect.mindconnect.services.EmailService;
import com.mindconnect.mindconnect.utils.SignupEmailTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthImplementation implements AuthServices {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtImplementation jwtImplementation;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override

    public ResponseEntity<ApiResponse<String>> signup(SignupDto signupDto) {
         var userExists = userRepository.findByEmailAddress(signupDto.emailAddress());
        if (userExists.isPresent()) {
            throw new MindConnectException("Email address already exists");
        } else {
            User newUser = new User();
            newUser.setEmailAddress(signupDto.emailAddress());
            newUser.setPassword(passwordEncoder.encode(signupDto.Password()));
            newUser.setFirstName(signupDto.firstName());
            newUser.setLastName(signupDto.lastName());
            newUser.setMentalCondition(signupDto.mentalCondition());
            newUser.setCountry(signupDto.country());
            newUser.setState(signupDto.state());
            newUser.setGender(signupDto.gender());

            var jwtToken = jwtImplementation.
                    generateToken(newUser);
            userRepository.save(newUser);

            emailService.sendEmail(
                    SignupEmailTemplate.signup(
                            newUser.getFirstName(),
                            jwtToken


                    ),
                    "Verify your email address",
                    newUser.getEmailAddress()
            );

            ApiResponse<String> response = new ApiResponse<>(
                    "Check your email for OTP verification",
                    "Successfully created account"
            );
            return new ResponseEntity<>(response, response.getStatus());
        }
    }
    public ResponseEntity<ApiResponse<LoginResponse>> login(LoginDto loginDto) {
       Authentication authenticationResult = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.emailAddress(), loginDto.Password()));
        var user = userRepository.findByEmailAddress(loginDto.emailAddress())
                .orElseThrow(() -> new MindConnectException("User doesn't exist"));
        var jwt = jwtImplementation.generateToken(user);

        LoginResponse loginResponse = new LoginResponse(user.getFirstName(), user.getLastName(), jwt);

        ApiResponse<LoginResponse> response = new ApiResponse<>(
                loginResponse,
                "Login successful"
        );
        return new ResponseEntity<>(response, response.getStatus()
        );
    }
}