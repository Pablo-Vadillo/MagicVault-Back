package com.magicvault.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Users")
public class Users {
	
	@Id
	private int id;
	
	private String type_rol;
	
	private String username;
	
	private String pass;
	
	private String email;
	
	
	
	public Users(int id, String type_rol, String username, String pass, String email) {
		super();
		this.id = id;
		this.type_rol = type_rol;
		this.username = username;
		this.pass = pass;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType_rol() {
		return type_rol;
	}

	public void setType_rol(String type_rol) {
		this.type_rol = type_rol;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
