package client.tests;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import client.controllers.CacheController;
import client.models.Account;
import client.models.Contact;
import client.models.Folder;
import client.models.Message;

class CacheTests {

	@Test
	void test() throws ClassNotFoundException, IOException {
		Account a = new Account("Test Account", "test@example.com", "testuser", "password", "smtp.example.com", "imap.example.com", 50000);
		Folder f = new Folder("INBOX");
		Folder g = new Folder("Trash");
		a.getRootFolder().addFolder(f);
		a.getRootFolder().addFolder(g);
		Message m = new Message.Builder()
				.subject("Test Message")
				.sender(new Contact(a))
				.addRecipients(Arrays.asList(new Contact(a)))
				.body("Test message", false)
				.build();
		f.addMessage(m);
		CacheController.cacheAccount(a);
		Account b = CacheController.loadAccount(a.getAddress());
		assertTrue(a.getDisplayName().equals(b.getDisplayName()));
		assertTrue(a.getPassword().equals(b.getPassword()));
		assertTrue(b.getRootFolder().getFolderFromName("INBOX").getMessages().size() == 1);
		assertTrue(b.getRootFolder().getFolders().size() == 2);
	}

}
