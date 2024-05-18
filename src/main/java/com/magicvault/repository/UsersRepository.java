package com.magicvault.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.magicvault.documents.Users;

public interface UsersRepository extends MongoRepository<Users,ObjectId> {
	Users findByUsernameAndPass(String username, String pass);

}
