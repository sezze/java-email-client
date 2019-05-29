package client.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import client.mappers.MessageMapper;
import client.models.Contact;

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

//	@Test
//	public void fetchFolders() throws MessagingException {
//		System.out.println("\n\nTesting folder fetching");
//		Folder[] folders = store.getDefaultFolder().list();
//		System.out.println(Arrays.toString(folders));
//		assertTrue(folders.length > 0);
//	}
//
//	@Test
//	public void fetchMessages() throws MessagingException, UnsupportedEncodingException {
//		System.out.println("\n\nTesting message fetching");
//		Folder folder = store.getFolder("INBOX");
//		folder.open(Folder.READ_ONLY);
//		Message[] messages = folder.getMessages();
//		System.out.println(MimeUtility.decodeText(messages[2].getFrom()[0].toString()));
//	}
//	
//	@Test
//	public void readMessage() throws MessagingException, IOException {
//		System.out.println("\n\nReading message content");
//		Folder folder = store.getFolder("INBOX");
//		folder.open(Folder.READ_WRITE);
//		Message message = folder.getMessages()[2];
//		Multipart multipart = (Multipart) message.getContent();
//		for (int i = 0; i < multipart.getCount(); i++) {
//			Part part = multipart.getBodyPart(i);
//			if (part.isMimeType("text/html")) {
//				System.out.println(part.getContent().toString());
//				//System.out.println(MimeUtility.decodeText(part.getContent().toString()));
//			}
//		}	
//	}
//	
//	@Test
//	public void getPDF() throws MessagingException, IOException {
//		System.out.println("\n\nReading message content");
//		Folder folder = store.getFolder("INBOX");
//		folder.open(Folder.READ_WRITE);
//		Message message = folder.getMessages()[2];
//		Multipart multipart = (Multipart) message.getContent();
//		for (int i = 0; i < multipart.getCount(); i++) {
//			MimeBodyPart part = (MimeBodyPart) multipart.getBodyPart(i);
//			if (part.isMimeType("application/pdf")) {
//				part.saveFile(part.getFileName());
//			}
//		}	
//	}
	
	@Test
	public void convertMessages() throws MessagingException, IOException {
		System.out.println("\n\nTesting out conversion");
		ArrayList<Contact> recipients = new ArrayList<Contact>();
		recipients.add(new Contact("Sebastian", "sebastian@nanyabiz.nes"));
		client.models.Message testMessage = new client.models.Message.Builder()
				.sender(new Contact("Bill Gates", "bill@microsoft.com"))
				.addRecipients(recipients)
				.subject("Dear Sebastian, I have now sent you the million dollars that I've promised you!")
				.body("WOWA, <b><i>this</i> is the body of the email!!</b>", true)
				.isFlagged(true).build();
		System.out.println(testMessage.getRecipients());
		Message javaMail = MessageMapper.map(testMessage);
		client.models.Message converted = MessageMapper.map(javaMail);
	}
	
//	@Test
//	public void convertMessagesFromServer() throws MessagingException {
//		System.out.println("\n\nTesting out conversion from server");
//		Folder folder = store.getFolder("INBOX");
//		folder.open(Folder.READ_WRITE);
//		Message message = folder.getMessages()[2];
//		client.models.Message converted = MessageMapper.from(message);
//	}
	
}
