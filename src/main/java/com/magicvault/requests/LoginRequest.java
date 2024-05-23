package com.magicvault.requests;

// Class representing a login request
public class LoginRequest {
    public String username; // User's username
    public String pass; // User's password

    // Constructor for creating a login request with username and password
    public LoginRequest(String name, String password) {
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
}
