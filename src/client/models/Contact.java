package client.models;

import java.io.Serializable;

public class Contact implements Serializable {

	// Properties
	private String name;
	private String address;

	// Serialization ID
	private static final long serialVersionUID = -5288430883672755031L;

	public Contact(String name, String address) {
		// Set property values
		this.name = name;
		this.address = address;
	}

	public Contact(String address) {
		this(null, address);
	}

	public Contact(Account account) {
		this(account.getDisplayName(), account.getAddress());
	}

	/*
	 * Property getters and setters
	 */
	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

}
