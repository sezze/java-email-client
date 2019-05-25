package client.tests;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class MessageTests {

	public static Store store;
	public static Session session;
	
	@BeforeAll
	public static void connect() throws MessagingException, FileNotFoundException, IOException {
		Properties userProps = new Properties();
		userProps.load(new FileInputStream("tests.properties"));
		
		System.out.println("Connecting to IMAP");
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		session = Session.getDefaultInstance(props);
		store = session.getStore("imaps");
		System.out.println(userProps.getProperty("server") + userProps.getProperty("user") + userProps.getProperty("password"));
		store.connect(userProps.getProperty("server"), userProps.getProperty("user"), userProps.getProperty("password"));
	}

	@Test
	public void fetchFolders() throws MessagingException {
		System.out.println("\n\nTesting folder fetching");
		Folder[] folders = store.getDefaultFolder().list();
		System.out.println(Arrays.toString(folders));
		assertTrue(folders.length > 0);
	}

	@Test
	public void fetchMessages() throws MessagingException, UnsupportedEncodingException {
		System.out.println("\n\nTesting message fetching");
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_ONLY);
		Message[] messages = folder.getMessages();
		System.out.println(MimeUtility.decodeText(messages[2].getFrom()[0].toString()));
	}
	
	@Test
	public void readMessage() throws MessagingException, IOException {
		System.out.println("\n\nReading message content");
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		Message message = folder.getMessages()[2];
		Multipart multipart = (Multipart) message.getContent();
		for (int i = 0; i < multipart.getCount(); i++) {
			Part part = multipart.getBodyPart(i);
			if (part.isMimeType("text/html")) {
				System.out.println(part.getContent().toString());
				//System.out.println(MimeUtility.decodeText(part.getContent().toString()));
			}
		}	
	}
	
	@Test
	public void getPDF() throws MessagingException, IOException {
		System.out.println("\n\nReading message content");
		Folder folder = store.getFolder("INBOX");
		folder.open(Folder.READ_WRITE);
		Message message = folder.getMessages()[2];
		Multipart multipart = (Multipart) message.getContent();
		for (int i = 0; i < multipart.getCount(); i++) {
			MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(i);
			if (part.isMimeType("application/pdf")) {
				part.saveFile(part.getFileName());
			}
		}	
	}
	
}
