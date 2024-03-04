package com.mindconnect.mindconnect.services.implementations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mindconnect.mindconnect.Models.User;
import com.mindconnect.mindconnect.dtos.SignupDto;
import com.mindconnect.mindconnect.exceptions.MindConnectException;
import com.mindconnect.mindconnect.repositories.UserRepository;
import com.mindconnect.mindconnect.services.EmailService;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthImplementation.class})
@ExtendWith(SpringExtension.class)
class SignupTest {
    @Autowired
    private AuthImplementation authImplementation;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private EmailService emailService;

    @MockBean
    private JwtImplementation jwtImplementation;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testSignup() {
        User user = new User();
        user.setCountry("Nigeria");
        user.setEmailAddress("johnpaul@gmail.com");
        user.setFirstName("John");
        user.setGender("Male");
        user.setId(UUID.randomUUID());
        user.setLastName("Paul");
        user.setMentalCondition("Mental Condition");
        user.setPassword("Paul20");
        user.setState("Lagos");
        user.setUser(new User());


        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findByEmailAddress(Mockito.<String>any())).thenReturn(ofResult);
        assertThrows(MindConnectException.class, () -> authImplementation
                .signup(new SignupDto("Apostle", "Paul", "apostlepaul@gmail.com", "Mental Condition", "Niger", "Los", "loo2341", "loo2341","Mala")));
        verify(userRepository).findByEmailAddress(Mockito.<String>any());
    }
}
