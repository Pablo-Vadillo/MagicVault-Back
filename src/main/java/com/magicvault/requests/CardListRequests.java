package com.magicvault.requests;

import com.magicvault.card.ScryfallCard;

// Class representing a request containing a list of Scryfall cards
public class CardListRequests {
    private ScryfallCard[] data; // Array of Scryfall cards

    // Getter method for retrieving the array of Scryfall cards
    public ScryfallCard[] getData() {
        return data;
    }

    // Setter method for setting the array of Scryfall cards
    public void setData(ScryfallCard[] data) {
        this.data = data;
    }
}
