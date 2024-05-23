package com.magicvault.config;

// Import necessary classes for Spring configuration and security
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.magicvault.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

// Indicates that this class contains Spring configuration
@Configuration
// Lombok annotation to generate a constructor with required arguments
@RequiredArgsConstructor
public class AppConfig {

    // Autowires the UsersRepository dependency
    @Autowired
    private UsersRepository userRepository;

    // Defines a bean for RestTemplate, used to make REST calls
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // Defines a bean for AuthenticationManager, used for managing authentication
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Defines a bean for AuthenticationProvider, which will be used for authentication
    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Creates a DaoAuthenticationProvider instance
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        // Sets the UserDetailsService to be used by the authentication provider
        authenticationProvider.setUserDetailsService(userDetailService());
        // Sets the PasswordEncoder to be used by the authentication provider
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    // Defines a bean for PasswordEncoder, used to encode passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Defines a bean for UserDetailsService, used to load user-specific data
    @Bean
    public UserDetailsService userDetailService() {
        // Returns a lambda expression to find a user by username
        return username -> userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
