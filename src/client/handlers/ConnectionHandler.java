package client.handlers;

import java.io.FileInputStream;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Session;
import javax.mail.Store;

public class ConnectionHandler {
	
	public static void Connect() throws AuthenticationFailedException {
		Properties userProps = new Properties();
		userProps.load(new FileInputStream("tests.properties"));
		System.out.println("Connecting to IMAP");
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props);
		Store store = session.getStore("imaps");
		System.out.println(userProps.getProperty("server") + userProps.getProperty("user") + userProps.getProperty("password"));
		store.connect(userProps.getProperty("server"), userProps.getProperty("user"), userProps.getProperty("password"));
	}
	
}
