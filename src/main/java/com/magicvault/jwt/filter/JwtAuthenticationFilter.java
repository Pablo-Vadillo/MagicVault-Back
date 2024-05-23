package com.magicvault.jwt.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.magicvault.jwt.service.JWTService;

import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// Spring component annotation to indicate that this class is a bean managed by Spring
@Component
// Lombok annotation to generate constructor injection for required dependencies
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired
    private JWTService jwtService; // Service for JWT operations

    @Autowired
    private UserDetailsService userDetailsService; // Service for loading user details

    // Method to perform authentication logic for each incoming request
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
       
        final String token = getTokenFromRequest(request); // Extract JWT token from the request
        final String username;

        // If token is null, proceed with the filter chain
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract username from the token
        username = jwtService.getUsernameFromToken(token);

        // If username is not null and there is no existing authentication context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details by username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // If the token is valid for the user, create an authentication token
            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                // Set details for the authentication token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the authentication token in the security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        }
        
        // Proceed with the filter chain
        filterChain.doFilter(request, response);
    }

    // Method to extract JWT token from the request
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}