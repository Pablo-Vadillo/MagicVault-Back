package com.magicvault.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.magicvault.documents.Collections;

public interface CollectionsRepository extends MongoRepository<Collections,Integer> {
	List<Collections> findByUser(String user);
}
