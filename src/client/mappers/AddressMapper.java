package client.mappers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import client.Main;
import client.models.Contact;

public class AddressMapper {
	
	/**
	 * Contact to InternetAddress
	 */
	public static InternetAddress map(Contact contact) {
		InternetAddress adr = null;
		try {
			if(contact.getName() != null)
				adr = new InternetAddress(contact.getAddress(), contact.getName());
			else
				adr = new InternetAddress(contact.getAddress());
		} catch (UnsupportedEncodingException e) {
			Main.LOGGER.log(Level.WARNING, "Unsupported personal name encoding", e);
		} catch (AddressException e) {
			Main.LOGGER.log(Level.WARNING, "Wrongly formatted address provided", e);
		}
		return adr;
	}
	
	/**
	 * List<Contact> to List<InternetAddress>
	 */
	public static List<InternetAddress> map(List<Contact> contacts) {
		List<InternetAddress> addresses = new ArrayList<InternetAddress>();
		for (Contact contact : contacts) {
			addresses.add(map(contact));
		}
		return addresses;
	}
	
	/**
	 * Contact[] to List<InternetAddress>
	 */
	public static List<InternetAddress> map(Contact[] contacts) {
		return AddressMapper.map(Arrays.asList(contacts));
	}
	
	/**
	 * List<Contact> to InternetAddress[]
	 */
	public static InternetAddress[] mapAsArray(List<Contact> contacts) {
		return AddressMapper.map(contacts).toArray(new InternetAddress[0]);
	}
	
	/**
	 * Contact[] to InternetAddress[]
	 */
	public static InternetAddress[] mapAsArray(Contact[] contacts) {
		return AddressMapper.mapAsArray(Arrays.asList(contacts));
	}
}
