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

import com.magicvault.documents.Collections;
import com.magicvault.repository.CollectionsRepository;

@RestController
@RequestMapping("/collections")
public class CollectionsController {
	
	@Autowired
	public CollectionsRepository collectionsRepository;
	
	@PostMapping
	public ResponseEntity<?> saveCollection(@RequestBody Collections collection){
		try {
			Collections collectionsaved = collectionsRepository.save(collection);
			return new ResponseEntity<Collections>(collectionsaved,HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping
	public ResponseEntity<?> findAllCollections(){
		try {
			List<Collections> collections = collectionsRepository.findAll();
			return new ResponseEntity<List<Collections>>(collections,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping(value = "/user/{user}")
	public ResponseEntity<?> findDecksByUser(@PathVariable("user") String user){
		try {
			List<Collections> usercollections = collectionsRepository.findByUser(user);
			return new ResponseEntity<List<Collections>>(usercollections,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findDeck(@PathVariable("id") Integer id){
		try {
			Optional<Collections> _collection = collectionsRepository.findById(id);
			if(_collection.isPresent()) 
			{
				Collections collection = _collection.get();
				return new ResponseEntity<Collections>(collection,HttpStatus.OK);
			} else 
			{
				return new ResponseEntity<String>("No existe la Colección",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteDeck(@PathVariable("id") Integer id)
	{
		try {
			collectionsRepository.deleteById(id);
			return new ResponseEntity<String>("Colección Eliminada",HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateCollection(@PathVariable("id")Integer id, @RequestBody Collections newCollection){
		try {
			Optional<Collections> _collection = collectionsRepository.findById(id);
			if(_collection.isPresent()) 
			{
				Collections collection = _collection.get();
				collection.setUser(newCollection.getUser());
				collection.setCollectionname(newCollection.getCollectionname());
				collection.setCollectionlist(newCollection.getCollectionlist());
				collectionsRepository.save(collection);
				return new ResponseEntity<Collections>(collection,HttpStatus.OK);
			} else 
			{
				return new ResponseEntity<String>("No existe el Deck",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
