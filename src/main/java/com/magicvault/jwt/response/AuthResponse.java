package com.magicvault.jwt.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Lombok annotations to automatically generate getters, setters, constructors, and builder methods
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    String token; // JWT token returned in the response

    // No additional methods or logic in this class, as it's a simple data transfer object
}