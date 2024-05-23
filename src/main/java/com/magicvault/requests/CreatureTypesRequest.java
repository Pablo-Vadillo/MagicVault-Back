package com.magicvault.requests;

import java.util.List;

// Class representing a request containing a list of creature types
public class CreatureTypesRequest {
    private List<String> data; // List of creature types

    // Getter method for retrieving the list of creature types
    public List<String> getData() {
        return data;
    }

    // Setter method for setting the list of creature types
    public void setData(List<String> data) {
        this.data = data;
    }
}
