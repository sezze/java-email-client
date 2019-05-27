package client.models;

import java.io.Serializable;

public class Contact implements Serializable {

	private String name;
	private String address;
	private static final long serialVersionUID = -5288430883672755031L;
	
	public Contact(String address, String name) {
		this.address = address;
		this.name = name;
	}
	
	public Contact(String address) {
		this(address, null);
	}
	
	public String getName() { return name; }
	public String getAddress() { return address; }	

}
