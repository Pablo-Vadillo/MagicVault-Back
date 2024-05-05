package com.magicvault.documents;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Decks")
public class Decks {
	
	@Id
	private int id;
	
	private String user;
	
	private String deckname;
	
	private List<String> decklist;

	public Decks(int id, String user, String deckname, List<String> decklist) {
		super();
		this.id = id;
		this.user = user;
		this.deckname = deckname;
		this.decklist = decklist;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDeckname() {
		return deckname;
	}

	public void setDeckname(String deckname) {
		this.deckname = deckname;
	}

	public List<String> getDecklist() {
		return decklist;
	}

	public void setDecklist(List<String> decklist) {
		this.decklist = decklist;
	}
	
}
