package client.models;

import javax.mail.Session;
import javax.mail.Store;

public class Account {
	
	private String name;
	private String username;
	private String password;
	private String smtpAddress;
	private String imapAddress;
	
	private Folder[] folders;
	
	private Session session;
	private Store store;
	
	public Account(String name, String username, String password, String smtpAddress, String imapAddress) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.smtpAddress = smtpAddress;
		this.imapAddress = imapAddress;
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public String getSmtpAddress() { return smtpAddress; }
	public void setSmtpAddress(String smtpAddress) { this.smtpAddress = smtpAddress; }

	public String getImapAddress() { return imapAddress; }
	public void setImapAddress(String imapAddress) { this.imapAddress = imapAddress; }

	public Folder[] getFolders() { return folders; }
	public void setFolders(Folder[] folders) { this.folders = folders; }

	public Session getSession() { return session; }
	public void setSession(Session session) { this.session = session; }

	public Store getStore() { return store; }
	
}
