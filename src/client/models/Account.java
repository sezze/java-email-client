package client.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Account implements Serializable {

	// Property names
	public static final String DETAILS = "details";
	public static final String SYNC_RATE = "syncRate";
	
	// Properties
	private String displayName;
	private String address;
	private String username;
	private String password;
	private String smtpAddress;
	private String imapAddress;
	private Folder rootFolder;
	private int syncRate;
	private transient boolean isSetup;
	
	// Property listeners
	private transient List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

	private static final long serialVersionUID = 8009287544356881799L;
	
	public Account(String displayName, String address, String username, String password, String smtpAddress,
			String imapAddress, int syncRate) {
		// Set property values
		this.displayName = displayName;
		this.address = address;
		this.username = username;
		this.password = password;
		this.smtpAddress = smtpAddress;
		this.imapAddress = imapAddress;
		this.syncRate = syncRate;
		rootFolder = new Folder("root "+displayName);
	}
	
	public void checkListeners() {
		if (!isSetup) {
			listeners = new ArrayList<PropertyChangeListener>();
			isSetup = true;
		}
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
		notifyChangeListeners(DETAILS);
	}
	
	// Address
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	// Username
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		notifyChangeListeners(DETAILS);
	}
	
	// Password
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		notifyChangeListeners(DETAILS);
	}

	// SMTP Address
	public String getSmtpAddress() {
		return smtpAddress;
	}

	public void setSmtpAddress(String smtpAddress) {
		this.smtpAddress = smtpAddress;
		notifyChangeListeners(DETAILS);
	}

	// IMAP Address
	public String getImapAddress() {
		return imapAddress;
	}

	public void setImapAddress(String imapAddress) {
		this.imapAddress = imapAddress;
		notifyChangeListeners(DETAILS);
	}

	// Root folder
	public Folder getRootFolder() {
		return rootFolder;
	}
	
	// Sync rate
	public int getSyncRate() {
		return syncRate;
	}

	public void setSyncRate(int syncRate) {
		this.syncRate = syncRate;
		notifyChangeListeners(SYNC_RATE);
	}
	
	
	/*
	 * Property listener
	 */
	private void notifyChangeListeners(String propertyName) {
		notifyChangeListeners(propertyName, null, null);
	}
	
	private void notifyChangeListeners(String propertyName, Object oldValue, Object newValue) {
		checkListeners();
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(this, propertyName, oldValue, newValue));
		}
	}
	
	public void addChangeListener(PropertyChangeListener listener) {
		checkListeners();
		listeners.add(listener);
	}
	
	public void removeChangeListener(PropertyChangeListener listener) {
		checkListeners();
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
