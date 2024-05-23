package com.magicvault.documents;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Import statements

// Annotation to indicate that this class represents a MongoDB document and specifies the collection name
@Document(collection = "Collections")
public class Collections {
    // Annotation to mark the field as the primary key
    @Id
    private ObjectId id; // Unique identifier for the collection

    private String user; // User who owns the collection

    private String collectionname; // Name of the collection

    private List<String> collectionlist; // List of cards in the collection

    private String color; // Color associated with the collection

    // Constructor to initialize all fields
    public Collections(ObjectId id, String user, String collectionname, List<String> collectionlist, String color) {
        super();
        this.id = id;
        this.user = user;
        this.collectionname = collectionname;
        this.collectionlist = collectionlist;
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

    public String getCollectionname() {
        return collectionname;
    }

    public void setCollectionname(String collectionname) {
        this.collectionname = collectionname;
    }

    public List<String> getCollectionlist() {
        return collectionlist;
    }

    public void setCollectionlist(List<String> collectionlist) {
        this.collectionlist = collectionlist;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
