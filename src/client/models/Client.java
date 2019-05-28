package client.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Client implements Serializable {
	
	public static final String ACCOUNTS = "accounts";
	public static final String ACTIVE_FOLDER = "activeFolder";
	public static final String ACTIVE_ACCOUNT = "activeAccount";
	public static final String ACTIVE_MESSAGE = "activeMessage";

	private List<Account> accounts;
	private Account activeAccount;
	private Folder activeFolder;
	private Message activeMessage;
	
	private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();
	
	private static final long serialVersionUID = 7198673490993617265L;

	public Client() {
		accounts = new ArrayList<Account>();
	}

	public void addAccount(Account account) {
		accounts.add(account);
		if(activeAccount == null)
			setActiveAccount(account);
		notifyChangeListeners(ACCOUNTS);
	}
	
	public void removeAccount(Account account) {
		accounts.remove(account);
		if(activeAccount == account) {
			if(accounts.size() > 0)
				setActiveAccount(accounts.get(0));
			else
				setActiveAccount(null);
		}
		notifyChangeListeners(ACCOUNTS);
	}
	
	public List<Account> getAccounts() {
		return Collections.unmodifiableList(accounts);
	}
	
	public Account getActiveAccount() {
		return activeAccount;
	}

	public void setActiveAccount(Account activeAccount) {
		this.activeAccount = activeAccount;
		notifyChangeListeners(ACTIVE_ACCOUNT);
	}

	public Folder getActiveFolder() {
		return activeFolder;
	}

	public void setActiveFolder(Folder activeFolder) {
		this.activeFolder = activeFolder;
		notifyChangeListeners(ACTIVE_FOLDER);
	}

	public Message getActiveMessage() {
		return activeMessage;
	}

	public void setActiveMessage(Message activeMessage) {
		this.activeMessage = activeMessage;
		notifyChangeListeners(ACTIVE_MESSAGE);
	}
	
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
