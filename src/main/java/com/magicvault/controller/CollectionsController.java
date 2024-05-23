package com.magicvault.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magicvault.card.ScryfallCard;
import com.magicvault.documents.Collections;
import com.magicvault.repository.CollectionsRepository;
import com.magicvault.requests.AddRemoveCardRequest;
import com.magicvault.services.ScryfallService;

// Indicates that this class is a REST controller
@RestController
// Maps HTTP requests to /collections to methods in this controller
@RequestMapping("/collections")
// Enables Cross-Origin Resource Sharing (CORS) for all origins and headers
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CollectionsController {

    // Injects the CollectionsRepository dependency
    @Autowired
    public CollectionsRepository collectionsRepository;

    // Injects the ScryfallService dependency
    @Autowired
    public ScryfallService scryfallService;

    // Endpoint for saving a collection
    @PostMapping
    public ResponseEntity<?> saveCollection(@RequestBody Collections collection) {
        try {
            // Check if a collection with the same name exists for the user
            Optional<Collections> existingCollection = collectionsRepository
                    .findByCollectionnameAndUser(collection.getCollectionname(), collection.getUser());

            if (existingCollection.isPresent()) {
                // Return conflict status if collection already exists
                return new ResponseEntity<>("A collection with the same name already exists for this user",
                        HttpStatus.CONFLICT);
            }

            // Save the new collection
            Collections collectionsaved = collectionsRepository.save(collection);
            return new ResponseEntity<>(collectionsaved, HttpStatus.CREATED);
        } catch (Exception e) {
            // Return internal server error status if an exception occurs
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for retrieving all collections
    @GetMapping
    public ResponseEntity<?> findAllCollections() {
        try {
            // Retrieve all collections from the repository
            List<Collections> collections = collectionsRepository.findAll();
            return new ResponseEntity<>(collections, HttpStatus.OK);
        } catch (Exception e) {
            // Return internal server error status if an exception occurs
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for retrieving collections by user
    @GetMapping(value = "/user/{user}")
    public ResponseEntity<?> findDecksByUser(@PathVariable("user") String user) {
        try {
            // Retrieve collections by user from the repository
            List<Collections> usercollections = collectionsRepository.findByUser(user);
            return new ResponseEntity<>(usercollections, HttpStatus.OK);
        } catch (Exception e) {
            // Return internal server error status if an exception occurs
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for retrieving cards in a specific collection
    @GetMapping("/cards")
    public List<ScryfallCard> findCardsInCollection(@RequestParam String user, @RequestParam String collectionName) {
        try {
            // Retrieve the collection by name and user
            Optional<Collections> collection = collectionsRepository.findByCollectionnameAndUser(collectionName, user);
            if (collection.isPresent()) {
                // Get the list of card names and retrieve card details using ScryfallService
                List<String> cards = collection.get().getCollectionlist();
                return scryfallService.getCardList(cards);
            } else {
                // Return null if the collection is not found
                return null;
            }
        } catch (Exception e) {
            // Return null if an exception occurs
            return null;
        }
    }

    // Endpoint for adding a card to a collection
    @PutMapping("/addCard")
    public ResponseEntity<?> addCardToDeck(@RequestBody AddRemoveCardRequest addCardRequest) {
        try {
            // Retrieve the collection by name and user
            Optional<Collections> _collection = collectionsRepository
                    .findByCollectionnameAndUser(addCardRequest.getDeckname(), addCardRequest.getUser());
            if (_collection.isPresent()) {
                // Add the card to the collection's card list and save the collection
                Collections collection = _collection.get();
                collection.getCollectionlist().add(addCardRequest.getCardName());
                collectionsRepository.save(collection);
                return new ResponseEntity<>(collection, HttpStatus.OK);
            } else {
                // Return not found status if the collection is not found
                return new ResponseEntity<>("Collection not found for the specified user", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Return internal server error status if an exception occurs
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for removing a card from a collection
    @DeleteMapping("/removeCard")
    public ResponseEntity<?> removeCardFromDeck(@RequestBody AddRemoveCardRequest removeCardRequest) {
        try {
            // Retrieve the collection by name and user
            Optional<Collections> _collection = collectionsRepository
                    .findByCollectionnameAndUser(removeCardRequest.getDeckname(), removeCardRequest.getUser());
            if (_collection.isPresent()) {
                // Remove the card from the collection's card list
                Collections collection = _collection.get();
                boolean removed = collection.getCollectionlist().remove(removeCardRequest.getCardName());
                if (removed) {
                    // Save the updated collection
                    collectionsRepository.save(collection);
                    return new ResponseEntity<>(collection, HttpStatus.OK);
                } else {
                    // Return not found status if the card is not in the collection
                    return new ResponseEntity<>("The card does not exist in the specified collection", HttpStatus.NOT_FOUND);
                }
            } else {
                // Return not found status if the collection is not found
                return new ResponseEntity<>("Collection not found for the specified user", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Return internal server error status if an exception occurs
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for retrieving a collection by ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findDeck(@PathVariable("id") String id) {
        try {
            // Convert the ID to ObjectId and retrieve the collection by ID
            ObjectId collectionId = new ObjectId(id);
            Optional<Collections> _collection = collectionsRepository.findById(collectionId);
            if (_collection.isPresent()) {
                // Return the collection if found
                Collections collection = _collection.get();
                return new ResponseEntity<>(collection, HttpStatus.OK);
            } else {
                // Return internal server error status if the collection is not found
                return new ResponseEntity<>("Collection not found", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            // Return internal server error status if an exception occurs
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for deleting a collection
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCollection(@RequestBody AddRemoveCardRequest toBeDeleted) {
        try {
            // Retrieve the collection by name and user
            Optional<Collections> collectionOpt = collectionsRepository
                    .findByCollectionnameAndUser(toBeDeleted.getDeckname(), toBeDeleted.getUser());
            if (collectionOpt.isPresent()) {
                // Delete the collection
                Collections collection = collectionOpt.get();
                collectionsRepository.delete(collection);
                return new ResponseEntity<>(collection, HttpStatus.OK);
            } else {
                // Return not found status if the collection is not found
                return new ResponseEntity<>("Collection not found for the specified user", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Return internal server error status if an exception occurs
            return new ResponseEntity<>("Internal server error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint for updating a collection
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCollection(@PathVariable("id") String id, @RequestBody Collections newCollection) {
        try {
            // Convert the ID to ObjectId and retrieve the collection by ID
            ObjectId collectionId = new ObjectId(id);
            Optional<Collections> _collection = collectionsRepository.findById(collectionId);
            if (_collection.isPresent()) {
                // Update the collection details and save the collection
                Collections collection = _collection.get();
                collection.setUser(newCollection.getUser());
                collection.setCollectionname(newCollection.getCollectionname());
                collection.setCollectionlist(newCollection.getCollectionlist());
                collectionsRepository.save(collection);
                return new ResponseEntity<>(collection, HttpStatus.OK);
            } else {
                // Return internal server error status if the collection is not found
                return new ResponseEntity<>("Collection not found", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            // Return internal server error status if an exception occurs
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
