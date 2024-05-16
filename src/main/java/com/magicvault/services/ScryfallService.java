package com.magicvault.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.magicvault.card.ScryfallCard;
import com.magicvault.requests.CardListRequests;

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
}