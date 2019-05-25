package client.models;

import java.util.List;

import javax.mail.Folder;

public class Account {
	
	private String password;
	private String smtpAddress;
	private String imapAddress;
	private String address;
	private String name;
	private List<Folder> folders;
	
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	
	public String getSmtpAddress() { return smtpAddress; }
	public void setSmtpAddress(String smtpAddress) { this.smtpAddress = smtpAddress; }
	
	public String getImapAddress() { return imapAddress; }
	public void setImapAddress(String imapAddress) { this.imapAddress = imapAddress; }
	
}
