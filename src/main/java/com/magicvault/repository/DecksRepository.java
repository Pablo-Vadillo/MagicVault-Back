package com.magicvault.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.magicvault.documents.Decks;

public interface DecksRepository extends MongoRepository<Decks,Integer> {
	List<Decks> findByUser(String user);
}
