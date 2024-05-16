package com.magicvault.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.magicvault.card.ScryfallCard;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class ScryfallService {

    private static final String SCRYFALL_RANDOM_ENDPOINT = "https://api.scryfall.com/cards/random?q=type:legendary+type:creature";

    private final RestTemplate restTemplate;

    public ScryfallService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ScryfallCard getRandomCommander() {
        try {
            return restTemplate.getForObject(SCRYFALL_RANDOM_ENDPOINT, ScryfallCard.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Log the error and handle it accordingly
            System.err.println("Error fetching commander: " + ex.getMessage());
            return null;
        }
    }
}