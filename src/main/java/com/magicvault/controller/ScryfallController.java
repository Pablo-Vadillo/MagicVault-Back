package com.magicvault.controller;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.magicvault.documents.Collections;
import com.magicvault.documents.Decks;
import com.magicvault.repository.CollectionsRepository;
import com.magicvault.repository.DecksRepository;
import com.magicvault.requests.CardListRequests;
import com.magicvault.requests.CardSearchFilter;
import com.magicvault.requests.CreatureTypesRequest;
import com.magicvault.requests.SetsDTO;
import com.magicvault.services.ScryfallService;


// Controller class definition
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") // Allowing requests from any origin with any headers
public class ScryfallController {

    // Autowired dependencies
    @Autowired
    private ScryfallService scryfallService;

    @Autowired
    private DecksRepository deckRepository;

    @Autowired
    private CollectionsRepository collectionsRepository;

    // GET endpoint to retrieve a random commander card
    @GetMapping("/random-commander")
    public Object getRandomCommander() {
        // Call the service to get a random commander card
        return scryfallService.getRandomCommander();
    }

    // GET endpoint to retrieve all cards
    @GetMapping("/all-cards")
    public Object getAllCards() {
        // Call the service to get all cards
        return scryfallService.getAllCards();
    }

    // GET endpoint to retrieve the card list of a specific deck
    @GetMapping(value = "/deck/{id}")
    public Object getDeckList(@PathVariable String id) {
        // Convert the ID string to ObjectId
        ObjectId deckId = new ObjectId(id);
        // Find the deck by its ID
        Optional<Decks> _deck = deckRepository.findById(deckId);
        if (_deck.isPresent()) {
            // If the deck exists, retrieve its card list using the service
            Decks deck = _deck.get();
            return scryfallService.getCardList(deck.getDecklist());
        }
        return null; // Return null if the deck is not found
    }

    // GET endpoint to retrieve the card list of a specific collection
    @GetMapping(value = "/collection/{id}")
    public Object getCollectionList(@PathVariable String id) {
        // Convert the ID string to ObjectId
        ObjectId collectionId = new ObjectId(id);
        // Find the collection by its ID
        Optional<Collections> _collection = collectionsRepository.findById(collectionId);
        if (_collection.isPresent()) {
            // If the collection exists, retrieve its card list using the service
            Collections collection = _collection.get();
            return scryfallService.getCardList(collection.getCollectionlist());
        }
        return null; // Return null if the collection is not found
    }

    // GET endpoint to retrieve available creature types
    @GetMapping("/creature-types")
    public CreatureTypesRequest getCreatureTypes() {
        // Call the service to get available creature types
        return scryfallService.getCreatureTypes();
    }

    // GET endpoint to retrieve all available sets
    @GetMapping("/sets")
    public SetsDTO getAllSets() {
        // Call the service to get all available sets
        return scryfallService.getAllSets();
    }

    // POST endpoint to search for cards based on filter criteria
    @PostMapping("/search-cards")
    public CardListRequests searchCards(@RequestBody CardSearchFilter filter) {
        // Call the service to search for cards based on the provided filter
        return scryfallService.searchCards(filter);
    }
}