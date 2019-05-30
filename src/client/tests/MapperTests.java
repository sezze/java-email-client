package client.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;

import client.mappers.MessageMapper;
import client.models.Contact;
import client.models.Message;

class MapperTests {

	@Test
	void contactMapperTest() throws MessagingException, IOException {
		List<Contact> recipients = new ArrayList<Contact>();
		recipients.add(new Contact("another@example.com"));
		
		Message message = new Message.Builder()
				.sender(new Contact("user@example.com"))
				.addRecipients(recipients)
				.subject("A test message")
				.body("This is just a test", false)
				.build();
		
		javax.mail.Message javaMessage = MessageMapper.map(message);
		
		MessageMapper.map(javaMessage);
		
	}

}
