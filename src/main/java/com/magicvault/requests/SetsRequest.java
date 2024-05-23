package com.magicvault.requests;

// Class representing a request object for sets
public class SetsRequest {
    private String name; // Name of the set
    private String code; // Code of the set

    // Getter method for retrieving the name of the set
    public String getName() {
        return name;
    }

    // Setter method for setting the name of the set
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for retrieving the code of the set
    public String getCode() {
        return code;
    }

    // Setter method for setting the code of the set
    public void setCode(String code) {
        this.code = code;
    }
}