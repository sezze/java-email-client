package client.models;

import java.io.File;

public class Attachment {

	// Properties
	String name;
	int size;
	
	public Attachment(String name, int size) {
		// Set property values
		this.name = name;
		this.size = size;
	}
	
	/*
	 * Property getters and setters
	 */

	// Name
	public String getName() {
		return name;
	}
	
	// Size
	public int getSize() {
		return size;
	}
	
	// File (Deprecated)
	@Deprecated
	public File getFile() {
		return null;
	}

}
