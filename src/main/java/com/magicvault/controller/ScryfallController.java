package com.magicvault.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magicvault.services.ScryfallService;

@RestController
public class ScryfallController {

    private final ScryfallService scryfallService;

    public ScryfallController(ScryfallService scryfallService) {
        this.scryfallService = scryfallService;
    }

    @GetMapping("/random-commander")
    public Object getRandomCommander() {
        return scryfallService.getRandomCommander();
    }
    @GetMapping("/all-cards")
    public Object getAllCards() {
    	return scryfallService.getAllCards();
    }
}