package client.util;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

import client.models.Contact;

public class ContactMapper {
	
	public static InternetAddress from(Contact contact) {
		try {
			if(contact.getName() != null)
				return new InternetAddress(contact.getAddress(), contact.getName());
			return new InternetAddress(contact.getAddress());
		} catch (Exception e) {		
			e.printStackTrace();
		}
		return null;
	}
	
	public static Contact from(Address address) {
		InternetAddress adr = (InternetAddress) address;
		if (adr.getPersonal() != null)
			if (!adr.getPersonal().isEmpty())
				return new Contact(adr.getPersonal(), adr.getAddress());
		return new Contact(adr.getAddress());
	}
	
	public static List<InternetAddress> fromMultipleContacts(List<Contact> contacts) {
		List<InternetAddress> addresses = new ArrayList<InternetAddress>();
		for (Contact contact : contacts) {
			addresses.add(from(contact));
		}
		return addresses;
	}
	
	public static List<Contact> fromMultipleAddresses(List<Address> addresses) {
		List<Contact> contacts = new ArrayList<Contact>();
		for (Address address : addresses) {
			contacts.add(from(address));
		}
		return contacts;
	}
	
}
