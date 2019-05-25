package client.models;

import java.io.Serializable;

public class Contact implements Serializable {

	private String name;
	private String address;
	private static final long serialVersionUID = -5288430883672755031L;
	
	public Contact(String name, String address) {
		this.name = name;
		this.address = address;
	}
	
	public String getName() { return name; }
	public String getAddress() { return address; }	

}
