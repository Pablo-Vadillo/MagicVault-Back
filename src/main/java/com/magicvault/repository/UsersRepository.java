package com.magicvault.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.magicvault.documents.Users;

public interface UsersRepository extends MongoRepository<Users,Integer> {
	Users findByUsernameAndPass(String username, String pass);

}
