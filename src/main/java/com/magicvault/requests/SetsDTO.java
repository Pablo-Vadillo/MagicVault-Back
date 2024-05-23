package com.magicvault.requests;

import java.util.List;

// Class representing a DTO for sets
public class SetsDTO {
    private List<SetsRequest> data; // List of sets

    // Getter method for retrieving the list of sets
    public List<SetsRequest> getData() {
        return data;
    }

    // Setter method for setting the list of sets
    public void setData(List<SetsRequest> data) {
        this.data = data;
    }
}
