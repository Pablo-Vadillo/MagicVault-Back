package com.magicvault.documents;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Annotation to indicate that this class represents a MongoDB document and specifies the collection name
@Document(collection = "Decks")
public class Decks {
    // Annotation to mark the field as the primary key
    @Id
    private ObjectId id; // Unique identifier for the deck

    private String user; // User who owns the deck

    private String deckname; // Name of the deck

    private List<String> decklist; // List of cards in the deck

    private String commander; // Commander associated with the deck

    private String color; // Color associated with the deck

    // Constructor to initialize all fields
    public Decks(ObjectId id, String user, String deckname, List<String> decklist, String commander, String color) {
        super();
        this.id = id;
        this.user = user;
        this.deckname = deckname;
        this.decklist = decklist;
        this.commander = commander;
        this.color = color;
    }

    // Getter and setter methods for all fields
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDeckname() {
        return deckname;
    }

    public void setDeckname(String deckname) {
        this.deckname = deckname;
    }

    public List<String> getDecklist() {
        return decklist;
    }

    public void setDecklist(List<String> decklist) {
        this.decklist = decklist;
    }

    public String getCommander() {
        return commander;
    }

    public void setCommander(String commander) {
        this.commander = commander;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}