package com.magicvault.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.magicvault.documents.Decks;

public interface DecksRepository extends MongoRepository<Decks,Integer> {
	List<Decks> findByUser(String user);
	Optional<Decks> findByDecknameAndUser(String deckname,String user);
}
