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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magicvault.card.ScryfallCard;
import com.magicvault.documents.Decks;
import com.magicvault.repository.DecksRepository;
import com.magicvault.requests.AddRemoveCardRequest;
import com.magicvault.services.ScryfallService;

// Controller class definition
@RestController
@RequestMapping("/decks") // Base path for all endpoints in this controller
@CrossOrigin(origins = "*") // Allowing requests from any origin
public class DecksController {

    // Autowired dependencies
    @Autowired
    private DecksRepository deckRepository;

    @Autowired
    private ScryfallService scryfallService;

    // GET endpoint to retrieve all decks
    @GetMapping
    public ResponseEntity<?> findAllDecks() {
        try {
            // Retrieve all decks from the repository
            List<Decks> decks = deckRepository.findAll();
            // Return decks with HTTP status OK if successful
            return new ResponseEntity<List<Decks>>(decks, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return INTERNAL_SERVER_ERROR if an error occurs
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET endpoint to find decks by user
    @GetMapping(value = "/user/{user}")
    public ResponseEntity<?> findDecksByUser(@PathVariable("user") String user) {
        try {
            // Find decks by user from the repository
            List<Decks> userDecks = deckRepository.findByUser(user);
            // Return user-specific decks with HTTP status OK if successful
            return new ResponseEntity<List<Decks>>(userDecks, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exceptions and return INTERNAL_SERVER_ERROR if an error occurs
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET endpoint to find cards in a deck
    @GetMapping("/cards")
    public List<ScryfallCard> findCardsInDeck(@RequestParam String user, @RequestParam String deckName) {
        try {
            // Find the deck by user and deck name
            Optional<Decks> deck = deckRepository.findByDecknameAndUser(deckName, user);
            if (deck.isPresent()) {
                // Retrieve decklist from the deck and fetch card details using Scryfall service
                List<String> cards = deck.get().getDecklist();
                return scryfallService.getCardList(cards);
            } else {
                return null; // Return null if the deck is not found
            }
        } catch (Exception e) {
            return null; // Return null if an exception occurs
        }
    }

    // PUT endpoint to add a card to a deck
    @PutMapping("/addCard")
    public ResponseEntity<?> addCardToDeck(@RequestBody AddRemoveCardRequest addCardRequest) {
        try {
            // Find the deck by deck name and user
            Optional<Decks> optionalDeck = deckRepository.findByDecknameAndUser(addCardRequest.getDeckname(),
                    addCardRequest.getUser());
            if (optionalDeck.isPresent()) {
                // If the deck exists, add the specified card to its decklist and save
                Decks deck = optionalDeck.get();
                deck.getDecklist().add(addCardRequest.getCardName());
                deckRepository.save(deck);
                return new ResponseEntity<Decks>(deck, HttpStatus.OK);
            } else {
                // Return NOT_FOUND if the deck is not found
                return new ResponseEntity<String>("No se encontró el mazo para el usuario especificado",
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle exceptions and return INTERNAL_SERVER_ERROR if an error occurs
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE endpoint to remove a card from a deck
    @DeleteMapping("/removeCard")
    public ResponseEntity<?> removeCardFromDeck(@RequestBody AddRemoveCardRequest removeCardRequest) {
        try {
            // Find the deck by deck name and user
            Optional<Decks> optionalDeck = deckRepository.findByDecknameAndUser(removeCardRequest.getDeckname(),
                    removeCardRequest.getUser());
            if (optionalDeck.isPresent()) {
                // If the deck exists, remove the specified card from its decklist and save
                Decks deck = optionalDeck.get();
                boolean removed = deck.getDecklist().remove(removeCardRequest.getCardName());
                if (removed) {
                    deckRepository.save(deck);
                    return new ResponseEntity<Decks>(deck, HttpStatus.OK);
                } else {
                    // Return NOT_FOUND if the card is not found in the deck
                    return new ResponseEntity<String>("La carta no existe en el mazo especificado",
                            HttpStatus.NOT_FOUND);
                }
            } else {
                // Return NOT_FOUND if the deck is not found
                return new ResponseEntity<String>("No se encontró el mazo para el usuario especificado",
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle exceptions and return INTERNAL_SERVER_ERROR if an error occurs
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET endpoint to find a deck by its ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findDeck(@PathVariable("id") String id) {
        try {
            // Convert the ID string to ObjectId
            ObjectId deckId = new ObjectId(id);
            // Find the deck by its ID
            Optional<Decks> _deck = deckRepository.findById(deckId);
            if (_deck.isPresent()) {
                // Return the deck with HTTP status OK if found
                Decks deck = _deck.get();
                return new ResponseEntity<Decks>(deck, HttpStatus.OK);
            } else {
                // Return INTERNAL_SERVER_ERROR if the deck is not found
                return new ResponseEntity<String>("No existe el mazo", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            // Handle exceptions and return INTERNAL_SERVER_ERROR if an error occurs
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE endpoint to delete a deck
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteDeck(@RequestBody AddRemoveCardRequest toBeDeleted) {
        try {
            // Find the deck by deck name and user
            Optional<Decks> deckOpt = deckRepository.findByDecknameAndUser(toBeDeleted.getDeckname(),
                    toBeDeleted.getUser());
            if (deckOpt.isPresent()) {
                // If the deck exists, delete it from the repository
                Decks deck = deckOpt.get();
                deckRepository.delete(deck);
                return new ResponseEntity<>(deck, HttpStatus.OK);
            } else {
                // Return NOT_FOUND if the deck is not found
                return new ResponseEntity<>("No se encontró el mazo para el usuario especificado",
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle exceptions and return INTERNAL_SERVER_ERROR if an error occurs
            return new ResponseEntity<>("Error interno del servidor: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


        // POST endpoint to save a new deck
    @PostMapping
    public ResponseEntity<?> saveDeck(@RequestBody Decks deck) {
        try {
            // Check if the commander card exists
            ScryfallCard commanderCard = scryfallService.getCommanderByName(deck.getCommander());
            if (commanderCard == null) {
                // If the commander card doesn't exist, return NOT_FOUND
                return new ResponseEntity<>("Comandante no encontrado", HttpStatus.NOT_FOUND);
            }

            // Set the commander card name and save the deck
            deck.setCommander(commanderCard.getName());
            Decks deckSaved = deckRepository.save(deck);
            // Return the saved deck with CREATED status if successful
            return new ResponseEntity<>(deckSaved, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle exceptions and return INTERNAL_SERVER_ERROR if an error occurs
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT endpoint to update an existing deck
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateDeck(@PathVariable("id") String id, @RequestBody Decks newDeck) {
        try {
            // Convert the ID string to ObjectId
            ObjectId deckId = new ObjectId(id);
            // Find the deck by its ID
            Optional<Decks> _deck = deckRepository.findById(deckId);
            if (_deck.isPresent()) {
                // If the deck exists, update its information and save
                Decks deck = _deck.get();
                deck.setUser(newDeck.getUser());
                deck.setDeckname(newDeck.getDeckname());
                deck.setDecklist(newDeck.getDecklist());
                deckRepository.save(deck);
                // Return the updated deck with OK status if successful
                return new ResponseEntity<Decks>(deck, HttpStatus.OK);
            } else {
                // Return INTERNAL_SERVER_ERROR if the deck is not found
                return new ResponseEntity<String>("No existe el mazo", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            // Handle exceptions and return INTERNAL_SERVER_ERROR if an error occurs
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
