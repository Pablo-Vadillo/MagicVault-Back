package com.magicvault.requests;

// Class representing a request to add or remove a card from a deck
public class AddRemoveCardRequest {
    private String deckname; // Name of the deck
    private String cardName; // Name of the card to add or remove
    private String user; // User performing the action

    // Getter method for retrieving the deck name
    public String getDeckname() {
        return deckname;
    }

    // Setter method for setting the deck name
    public void setDeckname(String deckname) {
        this.deckname = deckname;
    }

    // Getter method for retrieving the card name
    public String getCardName() {
        return cardName;
    }

    // Setter method for setting the card name
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    // Getter method for retrieving the user performing the action
    public String getUser() {
        return user;
    }

    // Setter method for setting the user performing the action
    public void setUser(String user) {
        this.user = user;
    }
}