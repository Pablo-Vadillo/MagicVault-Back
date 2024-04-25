package com.magicvault.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.magicvault.documentos.Users;

public interface UsersRepository extends MongoRepository<Users,Integer> {

}
