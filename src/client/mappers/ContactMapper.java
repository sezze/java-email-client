package client.mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

import client.Main;
import client.models.Contact;

public class ContactMapper {
	
	/**
	 * InternetAddress to Contact
	 */
	public static Contact map(InternetAddress address) {
		if (address.getPersonal() != null)
			if (!address.getPersonal().isEmpty())
				return new Contact(address.getPersonal(), address.getAddress());
		// Else if personal name not specified
		return new Contact(address.getAddress());
	}
	
	/**
	 * Address to Contact
	 */
	public static Contact map(Address address) {
		InternetAddress adr = null;
		try {
			adr = (InternetAddress) address;
		} catch (ClassCastException e) {
			Main.LOGGER.log(Level.SEVERE, "Address provided was not an InternetAddress", e);
			System.exit(1);
		}
		return ContactMapper.map(adr);
	}
	
	/**
	 * List<Address> to List<Contact>
	 */
	public static List<Contact> map(List<Address> addresses) {
		List<Contact> contacts = new ArrayList<Contact>();
		for (Address address : addresses) {
			contacts.add(map(address));
		}
		return contacts;
	}
	
	/**
	 * Address[] to List<Contact>
	 */
	public static List<Contact> map(Address[] addresses) {
		if (addresses == null) {
			addresses = new InternetAddress[] {};
		}
		return ContactMapper.map(Arrays.asList(addresses)); 
	}
	
}
