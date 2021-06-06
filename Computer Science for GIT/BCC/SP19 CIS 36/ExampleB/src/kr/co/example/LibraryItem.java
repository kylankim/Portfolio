package kr.co.example;

import java.util.UUID;

public class LibraryItem {
	String ID = UUID.randomUUID().toString();
	private String description;
	
	void setDescription(String s) {
		this.description = s;
	}
	
	String getDescription() {
		return this.description;
	}
}
