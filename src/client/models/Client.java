package client.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class Client extends Observable implements Serializable {

	private List<Account> accounts;
	private Account activeAccount;
	private Folder activeFolder;
	private Message activeMessage;
	private static final long serialVersionUID = 7198673490993617265L;

	public Client() {
		accounts = new ArrayList<Account>();
	}

	public void addAccount(Account account) {
		accounts.add(account);
		if(activeAccount == null)
			activeAccount = account;
		setChanged();
		notifyObservers(Client.UpdateType.ACCOUNTS);
	}
	
	public void removeAccount(Account account) {
		accounts.remove(account);
		if(activeAccount == account) {
			if(accounts.size() > 0)
				activeAccount = accounts.get(0);
			else
				activeAccount = null;
		}
		setChanged();
		notifyObservers(Client.UpdateType.ACCOUNTS);
	}
	
	public List<Account> getAccounts() {
		return Collections.unmodifiableList(accounts);
	}
	
	public Account getActiveAccount() {
		return activeAccount;
	}

	public void setActiveAccount(Account activeAccount) {
		this.activeAccount = activeAccount;
		setChanged();
		notifyObservers(Client.UpdateType.ACTIVE_ACCOUNT);
	}

	public Folder getActiveFolder() {
		return activeFolder;
	}

	public void setActiveFolder(Folder activeFolder) {
		this.activeFolder = activeFolder;
		setChanged();
		notifyObservers(Client.UpdateType.ACTIVE_FOLDER);
	}

	public Message getActiveMessage() {
		return activeMessage;
	}

	public void setActiveMessage(Message activeMessage) {
		this.activeMessage = activeMessage;
		setChanged();
		notifyObservers(Client.UpdateType.ACTIVE_MESSAGE);
	}

	public static enum UpdateType {
		ACCOUNTS, ACTIVE_ACCOUNT, ACTIVE_FOLDER, ACTIVE_MESSAGE
	}
	
}
