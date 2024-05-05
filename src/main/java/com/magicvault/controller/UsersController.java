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

import com.magicvault.documents.Users;
import com.magicvault.repository.UsersRepository;

@RestController
@RequestMapping("/users")
public class UsersController {
	
	@Autowired
	private UsersRepository userRepository;
	
	@PostMapping
	public ResponseEntity<?> saveUser(@RequestBody Users user){
		try {
			Users usersaved = userRepository.save(user);
			return new ResponseEntity<Users>(usersaved,HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping
	public ResponseEntity<?> findAllUsers(){
		try {
			List<Users> users = userRepository.findAll();
			return new ResponseEntity<List<Users>>(users,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findUser(@PathVariable("id") Integer id){
		try {
			Optional<Users> _user = userRepository.findById(id);
			if(_user.isPresent()) 
			{
				Users user = _user.get();
				return new ResponseEntity<Users>(user,HttpStatus.OK);
			} else 
			{
				return new ResponseEntity<String>("No existe Usuario",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Integer id)
	{
		try {
			userRepository.deleteById(id);
			return new ResponseEntity<String>("Usuario Eliminado",HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateUser(@PathVariable("id")Integer id, @RequestBody Users newUser){
		try {
			Optional<Users> _user = userRepository.findById(id);
			if(_user.isPresent()) 
			{
				Users user = _user.get();
				user.setUsername(newUser.getUsername());
				user.setType_rol(newUser.getType_rol());
				user.setPass(newUser.getPass());
				user.setEmail(newUser.getEmail());
				userRepository.save(user);
				return new ResponseEntity<Users>(user,HttpStatus.OK);
			} else 
			{
				return new ResponseEntity<String>("No existe Usuario",HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
