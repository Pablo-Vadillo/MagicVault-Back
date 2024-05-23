package com.magicvault.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.magicvault.card.ScryfallCard;
import com.magicvault.requests.CardListRequests;
import com.magicvault.requests.CardSearchFilter;
import com.magicvault.requests.CreatureTypesRequest;
import com.magicvault.requests.SetsDTO;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class ScryfallService {

    // Base URL of the Scryfall API for cards
    private static final String SCRYFALL_ENDPOINT = "https://api.scryfall.com/cards/";
    
    // Instance of RestTemplate used to make HTTP requests
    private final RestTemplate restTemplate;

    public ScryfallService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Get a random commander card from Scryfall
    public ScryfallCard getRandomCommander() {
        try {
            // Make a GET request to the Scryfall API to fetch a random commander card
            return restTemplate.getForObject(SCRYFALL_ENDPOINT.concat("random?q=type:legendary+type:creature"), ScryfallCard.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error fetching commander: " + ex.getMessage());
            return null;
        }
    }

    // Get a commander card by name from Scryfall
    public ScryfallCard getCommanderByName(String name) {
        try {
            // Build the query to search for a commander card by name
            String query = String.format("q=name:\"%s\" type:legendary type:creature", name);
            // Make a GET request to the Scryfall API to search for the card
            CardListRequests response = restTemplate.getForObject(SCRYFALL_ENDPOINT.concat("search?" + query), CardListRequests.class);
            // If a response is found and it has data, return the first card found
            if (response != null && response.getData() != null && response.getData().length > 0) {
                return response.getData()[0];
            }
            return null;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error fetching commander by name: " + ex.getMessage());
            return null;
        }
    }

    // Get all cards from Scryfall
    public ScryfallCard[] getAllCards() {
        try {
            // Make a GET request to the Scryfall API to get all cards
            CardListRequests response = restTemplate.getForObject(SCRYFALL_ENDPOINT.concat("search?q=o:a+or+o:e+or+o:i+or+o:o+or+o:u"), CardListRequests.class);
            return response.getData();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error fetching cards: " + ex.getMessage());
            return null;
        }
    }

    // Get card details for a list of card names
    public List<ScryfallCard> getCardList(List<String> cardlist) {
        try {
            List<ScryfallCard> cards = new ArrayList<>();
            // For each card name in the list, make a GET request to the Scryfall API to get card details
            for (String cardname : cardlist) {
                String url = SCRYFALL_ENDPOINT + "named?fuzzy=" + cardname;
                ScryfallCard card = restTemplate.getForObject(url, ScryfallCard.class);
                if (card != null) {
                    cards.add(card);
                }
            }
            return cards;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error fetching cards: " + ex.getMessage());
            return null;
        }
    }

    // Get creature types from Scryfall
    public CreatureTypesRequest getCreatureTypes() {
        String url = "https://api.scryfall.com/catalog/creature-types";
        // Make a GET request to the Scryfall API to get creature types
        return restTemplate.getForObject(url, CreatureTypesRequest.class);
    }

    // Get all sets from Scryfall
    public SetsDTO getAllSets() {
        String url = "https://api.scryfall.com/sets";
        // Make a GET request to the Scryfall API to get all sets
        return restTemplate.getForObject(url, SetsDTO.class);
    }

    // Search cards based on provided filter criteria
    public CardListRequests searchCards(CardSearchFilter filter) {
        try {
            // Build the query string based on filter criteria
            StringBuilder queryStringBuilder = new StringBuilder();
            // Add filter criteria to the query string
            
            // Construct the complete URL
            String url = SCRYFALL_ENDPOINT + "search?q=" + queryStringBuilder.toString();
            System.out.println("Url : " +  url);
            // Make the request to the Scryfall API endpoint
            return restTemplate.getForObject(url, CardListRequests.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error searching cards: " + ex.getMessage());
            return null;
        }
    }

}
