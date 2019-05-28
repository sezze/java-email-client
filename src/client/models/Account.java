package client.models;

import java.util.Observable;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class Account extends Observable {

	private String displayName;
	private String address;
	private String username;
	private String password;
	private String smtpAddress;
	private String imapAddress;

	private Folder rootFolder;

	private Session session;
	private Store store;

	public Account(String displayName, String address, String username, String password, String smtpAddress,
			String imapAddress) {
		this.displayName = displayName;
		this.address = address;
		this.username = username;
		this.password = password;
		this.smtpAddress = smtpAddress;
		this.imapAddress = imapAddress;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
		setChanged();
		notifyObservers();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		setChanged();
		notifyObservers();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		setChanged();
		notifyObservers();
	}

	public String getSmtpAddress() {
		return smtpAddress;
	}

	public void setSmtpAddress(String smtpAddress) {
		this.smtpAddress = smtpAddress;
		setChanged();
		notifyObservers();
	}

	public String getImapAddress() {
		return imapAddress;
	}

	public void setImapAddress(String imapAddress) {
		this.imapAddress = imapAddress;
		setChanged();
		notifyObservers();
	}

	public Folder getRootFolder() {
		return rootFolder;
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
		try {
			this.store = session.getStore("imaps");
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
	}

	public Store getStore() {
		return store;
	}

	@Override
	public String toString() {
		return address;
	}

}
