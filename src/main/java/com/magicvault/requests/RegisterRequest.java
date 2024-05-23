package com.magicvault.requests;

// Class representing a registration request
public class RegisterRequest {
    public String username; // User's username
    public String email; // User's email
    public String pass; // User's password

    // Constructor for creating a registration request with username and password
    public RegisterRequest(String name, String password) {
        super();
        username = name;
        pass = password;
    }

    // Getter method for retrieving the username
    public String getUsername() {
        return username;
    }

    // Setter method for setting the username
    public void setUsername(String name) {
        username = name;
    }

    // Getter method for retrieving the password
    public String getPassword() {
        return pass;
    }

    // Setter method for setting the password
    public void setPassword(String password) {
        pass = password;
    }

    // Getter method for retrieving the email
    public String getEmail() {
        return email;
    }

    // Setter method for setting the email
    public void setEmail(String email) {
        this.email = email;
    }
}
