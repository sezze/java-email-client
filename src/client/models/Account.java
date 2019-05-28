package client.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class Account {

	private String displayName;
	private String address;
	private String username;
	private String password;
	private String smtpAddress;
	private String imapAddress;

	private Folder rootFolder;

	private Session session;
	private Store store;
	
	private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

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
		notifyChangeListeners();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		notifyChangeListeners();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		notifyChangeListeners();
	}

	public String getSmtpAddress() {
		return smtpAddress;
	}

	public void setSmtpAddress(String smtpAddress) {
		this.smtpAddress = smtpAddress;
		notifyChangeListeners();
	}

	public String getImapAddress() {
		return imapAddress;
	}

	public void setImapAddress(String imapAddress) {
		this.imapAddress = imapAddress;
		notifyChangeListeners();
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

	@Override
	public String toString() {
		return address;
	}

}
