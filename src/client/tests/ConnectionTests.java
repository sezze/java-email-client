package client.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;

import client.controllers.ConnectionController;
import client.mappers.FolderMapper;
import client.models.Account;
import client.models.Folder;

class ConnectionTests {

	@Test
	void testConnection() throws FileNotFoundException, IOException, MessagingException {
		Properties p = new Properties();
		p.load(new FileInputStream("tests.properties"));
		Account account = new Account(p.getProperty("displayName"), p.getProperty("address"), p.getProperty("username"),
				p.getProperty("password"), p.getProperty("smtpAddress"), p.getProperty("imapAddress"), 10000);
		ConnectionController con = new ConnectionController(account);
		con.connect();
		assertTrue(con.isConnected());
		Folder rootFolder = new Folder("ROOT");
		for (javax.mail.Folder folder : con.getStore().getDefaultFolder().list()) {
			rootFolder.addFolder(FolderMapper.map(folder));
		}
		assertTrue(account.getRootFolder().getFolders().size() > 0);
	}

}
