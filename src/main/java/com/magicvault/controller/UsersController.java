package com.magicvault.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magicvault.documents.Users;
import com.magicvault.repository.UsersRepository;

// Controller class definition
@RestController
@RequestMapping("/users") // Base path for all endpoints in this controller
@CrossOrigin(origins = "*", allowedHeaders = "*") // Allowing requests from any origin with any headers
public class UsersController {
	
	@Autowired
	private UsersRepository userRepository; // Autowired dependency for accessing User repository
	
	// GET endpoint to retrieve all users
	@GetMapping
	public ResponseEntity<?> findAllUsers(){
		try {
			// Retrieve all users from the repository
			List<Users> users = userRepository.findAll();
			// Return users with HTTP status OK if successful
			return new ResponseEntity<List<Users>>(users, HttpStatus.OK);
		} catch (Exception e) {
			// Handle exceptions and return INTERNAL_SERVER_ERROR if an error occurs
			return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// GET endpoint to find a user by ID
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findUser(@PathVariable("id") String id){
		try {
			// Convert the ID string to ObjectId
			ObjectId userId = new ObjectId(id);
			// Find the user by ID
			Optional<Users> _user = userRepository.findById(userId);
			if(_user.isPresent()) 
			{
				// If the user exists, return the user with HTTP status OK
				Users user = _user.get();
				return new ResponseEntity<Users>(user, HttpStatus.OK);
			} else 
			{
				// Return INTERNAL_SERVER_ERROR if the user is not found
				return new ResponseEntity<String>("No existe Usuario", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			// Handle exceptions and return INTERNAL_SERVER_ERROR if an error occurs
			return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// DELETE endpoint to delete a user by ID
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") String id)
	{
		try {
			// Convert the ID string to ObjectId
			ObjectId userId = new ObjectId(id);
			// Delete the user by ID
			userRepository.deleteById(userId);
			// Return success message with HTTP status OK
			return new ResponseEntity<String>("Usuario Eliminado", HttpStatus.OK);
		} catch (Exception e) {
			// Handle exceptions and return INTERNAL_SERVER_ERROR if an error occurs
			return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// PUT endpoint to update a user by ID
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody Users newUser){
		try {
			// Convert the ID string to ObjectId
			ObjectId userId = new ObjectId(id);
			// Find the user by ID
			Optional<Users> _user = userRepository.findById(userId);
			if(_user.isPresent()) 
			{
				// If the user exists, update user information and save
				Users user = _user.get();
				user.setUsername(newUser.getUsername());
				user.setType_rol(newUser.getType_rol());
				user.setPass(newUser.getPass());
				user.setEmail(newUser.getEmail());
				userRepository.save(user);
				// Return the updated user with HTTP status OK
				return new ResponseEntity<Users>(user, HttpStatus.OK);
			} else 
			{
				// Return INTERNAL_SERVER_ERROR if the user is not found
				return new ResponseEntity<String>("No existe Usuario", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			// Handle exceptions and return INTERNAL_SERVER_ERROR if an error occurs
			return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}