package client.models;

import java.io.File;
import java.io.Serializable;

public class Attachment implements Serializable {

	// Properties
	String name;
	int size;
	
	private static final long serialVersionUID = -3198466283149527635L;

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
