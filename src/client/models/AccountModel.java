package client.models;

import javax.mail.Folder;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AccountModel {
	
	/*
	 * Fields
	 */
	
	private String password;
	private String smtpAddress;
	private String imapAddress;
	
	/*
	 * Properties
	 */
	
	// (String) address
	private StringProperty address = new SimpleStringProperty();
	public StringProperty addressProperty() { return address; }
	public void setAddress(String val) { address.set(val); }
	public String getAddress() { return address.get(); }
	
	// (String) name
	private StringProperty name = new SimpleStringProperty();
	public StringProperty nameProperty() { return name; }
	public void setName(String val) { name.set(val); }
	public String getName() { return name.get(); }
	
	// (Folder) inbox folder
	private ObjectProperty<Folder> inboxFolder = new SimpleObjectProperty<Folder>();
	public ObjectProperty<Folder> inboxFolderProperty() { return inboxFolder; }
	public void setInboxFolder(Folder val) { inboxFolder.set(val); }
	public Folder getInboxFolder() { return inboxFolder.get(); }
	
	/*
	 * Field getters / setters
	 */
	
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	
	public String getSmtpAddress() { return smtpAddress; }
	public void setSmtpAddress(String smtpAddress) { this.smtpAddress = smtpAddress; }
	
	public String getImapAddress() { return imapAddress; }
	public void setImapAddress(String imapAddress) { this.imapAddress = imapAddress; }
	
}
