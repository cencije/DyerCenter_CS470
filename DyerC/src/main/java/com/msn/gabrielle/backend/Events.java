package com.msn.gabrielle.backend;

import java.util.Objects;

public class Events {

	private Long id = null;
	private String title = ""; 
	private String location = "";
	private String datetime = "";
	private String description = "";
	
	public Events() {}
	
	public void createEvent(String title, String location, 
			String datetime, String description) {
		this.title = title;
		this.location = location;
		this.datetime = datetime;
		this.description = description;
	}
	
}
