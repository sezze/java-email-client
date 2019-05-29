package client.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import client.Main;

public class Account {
	
	// Properties
	private String displayName;
	private String address;
	private String username;
	private String password;
	private String smtpAddress;
	private String imapAddress;
	private Folder rootFolder;
	
	// Java Mail properties
	private Session session;
	private Store store;
	
	// Property listeners
	private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

	public Account(String displayName, String address, String username, String password, String smtpAddress,
			String imapAddress) {
		// Set property values
		this.displayName = displayName;
		this.address = address;
		this.username = username;
		this.password = password;
		this.smtpAddress = smtpAddress;
		this.imapAddress = imapAddress;
	}
	
	/*
	 * Property getters and setters
	 */

	// Display name
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
		notifyChangeListeners();
	}
	
	// Username
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		notifyChangeListeners();
	}
	
	// Password
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		notifyChangeListeners();
	}

	// SMTP Address
	public String getSmtpAddress() {
		return smtpAddress;
	}

	public void setSmtpAddress(String smtpAddress) {
		this.smtpAddress = smtpAddress;
		notifyChangeListeners();
	}

	// IMAP Address
	public String getImapAddress() {
		return imapAddress;
	}

	public void setImapAddress(String imapAddress) {
		this.imapAddress = imapAddress;
		notifyChangeListeners();
	}

	// Root folder
	public Folder getRootFolder() {
		return rootFolder;
	}
	
	// Java Mail Session
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
		try {
			// Try to set store from session
			this.store = session.getStore("imaps");
		} catch (NoSuchProviderException e) {
			Main.LOGGER.log(Level.SEVERE, "Failed to get store from session", e);
			System.exit(1);
		}
	}
	
	// Java Mail Store
	public Store getStore() {
		return store;
	}
	
	/*
	 * Property listener
	 */
	private void notifyChangeListeners() {
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(this, null, null, null));
		}
	}
	
	public void addChangeListener(PropertyChangeListener listener) {
		listeners.add(listener);
	}
	
	public void removeChangeListener(PropertyChangeListener listener) {
		listeners.remove(listener);
	}
	
	/*
	 * To string
	 */
	@Override
	public String toString() {
		return address;
	}

}
