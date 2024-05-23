package com.magicvault.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.magicvault.documents.Decks;

// Spring Data MongoDB repository interface
public interface DecksRepository extends MongoRepository<Decks, ObjectId> {
    
    // Method to find decks by user
    List<Decks> findByUser(String user);
    
    // Method to find a deck by name and user
    Optional<Decks> findByDecknameAndUser(String deckname, String user);
}
