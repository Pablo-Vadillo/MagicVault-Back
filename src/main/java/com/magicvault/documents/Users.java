package com.magicvault.documents;

import java.util.Collection;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Import statements

// Annotation to indicate that this class represents a MongoDB document and specifies the collection name
@Document(collection = "Users")
// Lombok annotations to automatically generate getters, setters, constructors, and builder methods
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users implements UserDetails {

    // Annotation to mark the field as the primary key
    @Id
    private ObjectId id; // Unique identifier for the user

    private String type_rol; // Type of role associated with the user

    private String username; // Username of the user

    private String pass; // Password of the user

    private String email; // Email address of the user

    // Method to retrieve authorities associated with the user (not implemented)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // Method to retrieve the password of the user
    @Override
    public String getPassword() {
        return pass;
    }

    // Method to check if the user account is non-expired (always returns true)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Method to check if the user account is non-locked (always returns true)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Method to check if the user credentials are non-expired (always returns true)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Method to check if the user account is enabled (always returns true)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
