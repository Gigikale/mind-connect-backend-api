package com.mindconnect.mindconnect.services.implementations;

import com.mindconnect.mindconnect.Models.User;
import com.mindconnect.mindconnect.dtos.LoginDto;
import com.mindconnect.mindconnect.dtos.LoginResponse;
import com.mindconnect.mindconnect.dtos.SignupDto;
import com.mindconnect.mindconnect.enums.AccountStatus;
import com.mindconnect.mindconnect.enums.VerifyType;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.payloads.ApiResponse;
import com.mindconnect.mindconnect.repositories.UserRepository;
import com.mindconnect.mindconnect.services.AuthServices;
import com.mindconnect.mindconnect.utils.ForgotPasswordEmailTemplate;
import com.mindconnect.mindconnect.services.EmailService;
import com.mindconnect.mindconnect.utils.SignupEmailTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final EmailImplementation emailImplementation;
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
            System.out.println(jwtToken);

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
    @Override
    public ApiResponse<String> confirmEmail(String token) {
        String email = jwtImplementation.extractUsername(token);
        if(email != null) {
            if(jwtImplementation.isTokenExpired(token)) {
                throw new MindConnectException("Link has expired. Please request for a new link.") ;
            } else {
                var user = userRepository.findByEmailAddress(email);
                if(user.isPresent()) {
                    if(!user.get().isEnabled()) {
                        var update = user.get();
                        update.setStatus(AccountStatus.ACTIVE);
                        userRepository.save(update);
                        return new ApiResponse<>(
                                "Your email address is now verified. Please login.",
                                HttpStatus.OK
                        );
                    } else {
                        throw new MindConnectException("Email address is already verified.") ;
                    }
                } else {
                    throw new MindConnectException("User not found. Please check the link.");
                }
            }
        } else {
            throw new MindConnectException("Link is not properly formatted.");
        }
    }

    @Override
    public ResponseEntity<ApiResponse<String>> resendLink(String emailAddress, VerifyType type) {
        var user = userRepository.findByEmailAddress(emailAddress);
        if(user.isPresent()) {
            if(type == VerifyType.SIGNUP && user.get().isEnabled()) {
                throw new MindConnectException("This email is already verified");
            } else {
                if(type == VerifyType.SIGNUP && !user.get().isEnabled()) {
                    emailImplementation.sendEmail(
                            SignupEmailTemplate.signup(
                                    user.get().getFirstName(),
                                    jwtImplementation.generateToken(user.get())
                            ),
                            "Verify your email address",
                            user.get().getEmailAddress()
                    );
                } else {
                    emailImplementation.sendEmail(
                            ForgotPasswordEmailTemplate.password(
                                    jwtImplementation.generateToken(user.get())
                            ),
                            "Password Reset - Verify your email address",
                            user.get().getEmailAddress()
                    );
                }
                ApiResponse<String> response = new ApiResponse<>(
                        "Check your email for verification",
                        "Success in sending link"
                );
                return new ResponseEntity<>(response, response.getStatus());
            }
        } else {
            throw new MindConnectException("User does not exist");
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

    @Override
    public ResponseEntity<ApiResponse<String>> checkEmailForPasswordReset(String emailAddress) {
        var user = userRepository.findByEmailAddress(emailAddress);
        if (user.isPresent()) {
            emailImplementation.sendEmail(
                    ForgotPasswordEmailTemplate.password(
                            jwtImplementation.generateToken(user.get())
                    ),
                    "Password Reset - Verify your email address",
                    user.get().getEmailAddress()
            );
            user.get().setPasswordRecovery(true);
            userRepository.save(user.get());
            ApiResponse<String> response = new ApiResponse<>(
                    "Check your email for the password reset link",
                    "Success"
            );
            return new ResponseEntity<>(response, response.getStatus());
        } else {
            throw new MindConnectException("Email not found");
        }
    }

    @Override
    public ResponseEntity<ApiResponse<String>> resetPassword(
            String token, String password, String confirmPassword
    ) {
        String email = jwtImplementation.extractUsername(token);
        if(email != null) {
            if(jwtImplementation.isTokenExpired(token)) {
                throw new MindConnectException("Link has expired. Please request for a new link.");
            } else {
                var user = userRepository.findByEmailAddress(email);
                if(user.isPresent() && user.get().getPasswordRecovery()) {
                    if(password.equals(confirmPassword)) {
                        user.get().setPassword(passwordEncoder.encode(password));
                        user.get().setPasswordRecovery(false);
                        userRepository.save(user.get());
                        ApiResponse<String> response = new ApiResponse<>(
                                "Password changed for %s".formatted(user.get().getEmailAddress()),
                                "Successfully changed password",
                                HttpStatus.OK
                        );
                        return new ResponseEntity<>(response, response.getStatus());
                    } else {
                        throw new MindConnectException("Password does not match");
                    }
                } else {
                    throw new MindConnectException("User not found");
                }
            }
        } else {
            throw new MindConnectException("Link is not properly formatted.");
        }
    }
}