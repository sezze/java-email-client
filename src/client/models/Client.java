package client.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client implements Serializable {

	// Property names
	public static final String ACCOUNTS = "accounts";
	public static final String ACTIVE_FOLDER = "activeFolder";
	public static final String ACTIVE_ACCOUNT = "activeAccount";
	public static final String ACTIVE_MESSAGE = "activeMessage";

	// Properties
	private List<Account> accounts;
	private Account activeAccount;
	private Folder activeFolder;
	private Message activeMessage;

	// Property listeners
	private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

	// Serialization ID
	private static final long serialVersionUID = 7198673490993617265L;

	public Client() {
		accounts = new ArrayList<Account>();
	}

	/*
	 * Property getters and setters
	 */

	// Accounts
	public void addAccount(Account account) {
		accounts.add(account);
		if (activeAccount == null)
			setActiveAccount(account);
		notifyChangeListeners(ACCOUNTS);
	}

	public void removeAccount(Account account) {
		accounts.remove(account);
		if (activeAccount == account) {
			if (accounts.size() > 0)
				setActiveAccount(accounts.get(0));
			else
				setActiveAccount(null);
		}
		notifyChangeListeners(ACCOUNTS);
	}

	public List<Account> getAccounts() {
		return Collections.unmodifiableList(accounts);
	}

	// Active account
	public Account getActiveAccount() {
		return activeAccount;
	}

	public void setActiveAccount(Account activeAccount) {
		this.activeAccount = activeAccount;
		notifyChangeListeners(ACTIVE_ACCOUNT);
	}

	// Active folder
	public Folder getActiveFolder() {
		return activeFolder;
	}

	public void setActiveFolder(Folder activeFolder) {
		this.activeFolder = activeFolder;
		notifyChangeListeners(ACTIVE_FOLDER);
	}

	// Active message
	public Message getActiveMessage() {
		return activeMessage;
	}

	public void setActiveMessage(Message activeMessage) {
		this.activeMessage = activeMessage;
		notifyChangeListeners(ACTIVE_MESSAGE);
	}

	/*
	 * Property listeners
	 */
	private void notifyChangeListeners(String propertyName) {
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(this, propertyName, null, null));
		}
	}

	public void addChangeListener(PropertyChangeListener listener) {
		listeners.add(listener);
	}

	public void removeChangeListener(PropertyChangeListener listener) {
		listeners.remove(listener);
	}

}
