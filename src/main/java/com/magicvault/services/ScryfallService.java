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

    private static final String SCRYFALL_ENDPOINT = "https://api.scryfall.com/cards/";
    
    private final RestTemplate restTemplate;

    public ScryfallService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ScryfallCard getRandomCommander() {
        try {
            return restTemplate.getForObject(SCRYFALL_ENDPOINT.concat("random?q=type:legendary+type:creature"), ScryfallCard.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Log the error and handle it accordingly
            System.err.println("Error fetching commander: " + ex.getMessage());
            return null;
        }
    }
    public ScryfallCard[] getAllCards() {
    	try {
    		CardListRequests response = restTemplate.getForObject(SCRYFALL_ENDPOINT.concat("search?q=o:a+or+o:e+or+o:i+or+o:o+or+o:u"), CardListRequests.class);
    		ScryfallCard[] cards = response.getData();
    		return cards;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error fetching commander: " + ex.getMessage());
            return null;
        }
    }
    public List<ScryfallCard> getCardList(List<String> cardlist) {
    	try {
            List<ScryfallCard> cards = new ArrayList<>();
            for (String cardname : cardlist) {
                String url = SCRYFALL_ENDPOINT + "named?fuzzy=" + cardname;
                ScryfallCard card = restTemplate.getForObject(url, ScryfallCard.class);
                if (card != null) {
                    cards.add(card);
                }
            }
            return cards;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error fetching commander: " + ex.getMessage());
            return null;
        }
    }
    public CreatureTypesRequest getCreatureTypes() {
        String url = "https://api.scryfall.com/catalog/creature-types";
        return restTemplate.getForObject(url, CreatureTypesRequest.class);
    }

    public SetsDTO getAllSets() {
        String url = "https://api.scryfall.com/sets";
        return restTemplate.getForObject(url, SetsDTO.class);
    }
    public CardListRequests searchCards(CardSearchFilter filter) {
        try {
            String url = SCRYFALL_ENDPOINT + "search?q=" + filter;
            return restTemplate.getForObject(url, CardListRequests.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error searching cards: " + ex.getMessage());
            return null;
        }
    }
}