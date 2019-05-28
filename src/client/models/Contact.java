package client.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.InternetAddress;

public class Contact implements Serializable {

	private String name;
	private String address;
	private static final long serialVersionUID = -5288430883672755031L;
	
	public Contact(String name, String address) {
		this.name = name;
		this.address = address;
	}
	
	public Contact(String address) {
		this(null, address);
	}
	
	public InternetAddress asAddress() {
		try {
			if(name != null)
				return new InternetAddress(address, name);
			return new InternetAddress(address);
		} catch (Exception e) {		
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<InternetAddress> asAddresses(List<Contact> contacts) {
		List<InternetAddress> addresses = new ArrayList<InternetAddress>();
		for (Contact contact : contacts) {
			addresses.add(contact.asAddress());
		}
		return addresses;
	}
	
	public String getName() { return name; }
	public String getAddress() { return address; }	
	
}
