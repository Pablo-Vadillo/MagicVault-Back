package com.magicvault.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magicvault.documents.Decks;
import com.magicvault.repository.DecksRepository;

@RestController
@RequestMapping("/decks")
public class DecksController {
	
	@Autowired
	private DecksRepository deckRepository;
	
	@PostMapping
	public ResponseEntity<?> saveDeck(@RequestBody Decks deck){
		try {
			Decks decksaved = deckRepository.save(deck);
			return new ResponseEntity<Decks>(decksaved,HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping
	public ResponseEntity<?> findAllDecks(){
		try {
			List<Decks> decks = deckRepository.findAll();
			return new ResponseEntity<List<Decks>>(decks,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping(value = "/user/{user}")
	public ResponseEntity<?> findDecksByUser(@PathVariable("user") String user){
		try {
			List<Decks> userdecks = deckRepository.findByUser(user);
			return new ResponseEntity<List<Decks>>(userdecks,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findDeck(@PathVariable("id") Integer id){
		try {
			Optional<Decks> _deck = deckRepository.findById(id);
			if(_deck.isPresent()) 
			{
				Decks deck = _deck.get();
				return new ResponseEntity<Decks>(deck,HttpStatus.OK);
			} else 
			{
				return new ResponseEntity<String>("No existe Usuario",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteDeck(@PathVariable("id") Integer id)
	{
		try {
			deckRepository.deleteById(id);
			return new ResponseEntity<String>("Deck Eliminado",HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id")Integer id, @RequestBody Decks newDeck){
		try {
			Optional<Decks> _decks = deckRepository.findById(id);
			if(_decks.isPresent()) 
			{
				Decks deck = _decks.get();
				deck.setUser(newDeck.getUser());
				deck.setDeckname(newDeck.getDeckname());
				deck.setDecklist(newDeck.getDecklist());
				deckRepository.save(deck);
				return new ResponseEntity<Decks>(deck,HttpStatus.OK);
			} else 
			{
				return new ResponseEntity<String>("No existe el Deck",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
