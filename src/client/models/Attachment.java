package client.models;

import java.io.File;

public class Attachment {

	String name;
	int size;
	
	public Attachment(String name, int size) {
		this.name = name;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public File getFile() {
		// TODO Auto-generated method stub
		return null;
	}

}
