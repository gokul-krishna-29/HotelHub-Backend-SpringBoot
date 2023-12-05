package com.iamneo.hotelhub.service;

import com.iamneo.hotelhub.impl.AuthenticationRequest;
import com.iamneo.hotelhub.impl.AuthenticationResponse;
import com.iamneo.hotelhub.impl.RegisterRequest;
import com.iamneo.hotelhub.model.Role;
import com.iamneo.hotelhub.repository.UserRepository;
import com.iamneo.hotelhub.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<String> register(RegisterRequest request) {
        Optional<Users> isExistingUser = repository.findByEmail(request.getEmail());
        if(isExistingUser.isPresent())
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        else {
            var user = Users.builder()
                    .age(request.getAge())
                    .city(request.getCity())
                    .country(request.getCountry())
                    .firstName(request.getFirstName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .gender(request.getGender())
                    .phone(request.getPhone())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            repository.save(user);
//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
            return ResponseEntity.status(HttpStatus.OK).body("User Created");
        }
    }

    public ResponseEntity<?> authenticateUser(AuthenticationRequest request) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        var existingUser = repository.findByEmail(request.getEmail()).orElse(null);
        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        var token = jwtService.generateToken(existingUser);
        return ResponseEntity.ok(AuthenticationResponse.builder()
                .token(token)
                .phone(existingUser.getPhone())
                .country(existingUser.getCountry())
                .firstName(existingUser.getFirstName())
                .lastName(existingUser.getLastName())
                .id(existingUser.getId())
                .email(existingUser.getEmail())
                .gender(existingUser.getGender())
                .password(existingUser.getPassword())
                .build());
    }
}
