package client.handlers;

import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import client.models.Account;

public class ConnectionHandler {
	
	public static Session Connect(Account account) throws AuthenticationFailedException {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props);
		Store store;
		
		try {
			store = session.getStore("imaps");
			store.connect(account.getImapAddress(), account.getUsername(), account.getPassword());
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return session;
	}
	
}
