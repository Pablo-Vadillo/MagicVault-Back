package com.magicvault.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.magicvault.documents.Users;
import com.magicvault.jwt.response.AuthResponse;
import com.magicvault.repository.UsersRepository;
import com.magicvault.requests.LoginRequest;
import com.magicvault.requests.RegisterRequest;

import lombok.RequiredArgsConstructor;

// Import statements

// Spring service annotation to indicate that this class contains business logic
@Service
// Lombok annotation to generate constructor injection for required dependencies
@RequiredArgsConstructor
public class AuthService {
    
    @Autowired
    private UsersRepository userRepository; // Repository for user data access

    @Autowired
    private JWTService jwtService; // Service for JWT operations

    @Autowired
    private PasswordEncoder passwordEncoder; // Encoder for password hashing

    @Autowired
    private AuthenticationManager authenticationManager; // Manager for user authentication

    // Method for user login
    public AuthResponse login(LoginRequest request) {
        // Authenticate user credentials using Spring Security's AuthenticationManager
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        // Retrieve UserDetails object for the authenticated user from the repository
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        // Generate JWT token for the user
        String token = jwtService.getToken(user);
        // Build and return the authentication response containing the JWT token
        return AuthResponse.builder()
            .token(token)
            .build();
    }

    // Method for user registration
    public AuthResponse register(RegisterRequest request) {
        // Create a new Users object with the provided registration details
        Users user = Users.builder()
            .username(request.getUsername())
            .pass(passwordEncoder.encode(request.getPassword())) // Hash the password before saving
            .email(request.getEmail())
            .build();

        // Save the user to the repository
        userRepository.save(user);

        // Generate JWT token for the registered user
        String token = jwtService.getToken(user);
        // Build and return the authentication response containing the JWT token
        return AuthResponse.builder()
            .token(token)
            .build();
    }
}
