package com.magicvault.requests;

import java.util.List;

// Class representing a request for filtering card search queries
public class CardSearchFilter {
    private List<String> colors; // List of colors to filter by
    private String type; // Type of card to filter by
    private String expansion; // Expansion set to filter by
    private String name; // Name of the card to filter by

    // Getter method for retrieving the list of colors
    public List<String> getColors() {
        return colors;
    }

    // Setter method for setting the list of colors
    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    // Getter method for retrieving the type of card
    public String getType() {
        return type;
    }

    // Setter method for setting the type of card
    public void setType(String type) {
        this.type = type;
    }

    // Getter method for retrieving the expansion set
    public String getExpansion() {
        return expansion;
    }

    // Setter method for setting the expansion set
    public void setExpansion(String expansion) {
        this.expansion = expansion;
    }

    // Getter method for retrieving the name of the card
    public String getName() {
        return name;
    }

    // Setter method for setting the name of the card
    public void setName(String name) {
        this.name = name;
    }
}
