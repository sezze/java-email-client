package client.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import client.Main;
import client.controllers.ConnectionController;
import client.controllers.SendMessageController;
import client.controllers.SyncController;
import client.util.DialogUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Client implements Serializable {

	// Property names
	public static final String ACCOUNTS = "accounts";
	public static final String ACTIVE_FOLDER = "activeFolder";
	public static final String ACTIVE_ACCOUNT = "activeAccount";
	public static final String ACTIVE_MESSAGE = "activeMessage";

	// Properties
	private List<Account> accounts = new ArrayList<Account>();
	private List<ConnectionController> connectionControllers = new ArrayList<ConnectionController>();
	private List<SyncController> syncControllers = new ArrayList<SyncController>();
	private Account activeAccount;
	private Folder activeFolder;
	private Message activeMessage;
	private transient Stage settingsStage;

	// Property listeners
	private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();
	
	// Serialization ID
	private static final long serialVersionUID = 7198673490993617265L;

	public Client() {}
	
	public SendMessageController openSendMessageStage() throws IOException {
		if (getActiveAccount() == null) { DialogUtil.showWarning("You have no configured accounts, go to settings"); return null; }
		
		
		/*
		 * Scene setup 
		 */
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("../views/SendMessage.fxml").openStream());
		SendMessageController sendMessageController = (SendMessageController) loader.getController();
		
		// Scene
		Scene scene = new Scene(root, 460, 560);
		scene.getStylesheets().addAll(Main.STYLESHEETS);
		
		/*
		 * Stage setup
		 */
		Stage stage = new Stage();
		stage.setTitle("Java Email Client - New Message");
		stage.setScene(scene);
		stage.getIcons().add(new Image("client/assets/icons/logo.png"));
		stage.show();
		
		// Set connection controller
		for (ConnectionController ctrl : connectionControllers) {
			if (ctrl.getAccount() == getActiveAccount()) {
				sendMessageController.setConnectionController(ctrl);
			}
		}
		
		return sendMessageController;
	}

	public void openSendMessageStage(Message message) throws IOException {
		SendMessageController ctrl = openSendMessageStage();
		if (ctrl != null) {
			ctrl.loadMessage(message);
		}
	}
	
	public void openSettings() {
		if (settingsStage != null)
			if (settingsStage.isShowing()) {
				settingsStage.toFront();
				return;
			}
		
		
		/*
		 * Scene setup
		 */

		// Root node
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../views/Settings.fxml"));
		} catch (IOException e) {
			Main.LOGGER.log(Level.SEVERE, "Error initializing Settings.fxml", e);
			return;
		}

		// Scene
		Scene scene = new Scene(root, 460, 560);
		scene.getStylesheets().addAll(Main.STYLESHEETS);
		
		/*
		 * Stage setup
		 */
		settingsStage = new Stage();
		settingsStage.setTitle("Java Email Client - Settings");
		settingsStage.setScene(scene);
		settingsStage.getIcons().add(new Image("client/assets/icons/logo.png"));
		settingsStage.show();
	}
	
	/*
	 * Property getters and setters
	 */

	// Accounts
	public void addAccount(Account account) {
		accounts.add(account);
		if (activeAccount == null)
			setActiveAccount(account);
		
		// Add account's connection and sync controllers
		addSyncController(account, addConnectionController(account));
		notifyChangeListeners(ACCOUNTS);
	}

	public void removeAccount(Account account) {
		accounts.remove(account);
		if (activeAccount == account) {
			if (accounts.size() > 0)
				setActiveAccount(accounts.get(0));
			else {
				setActiveAccount(null);
			}
				
		}
		
		for (SyncController ctrl : syncControllers) {
			if (ctrl.getAccount() == account) {
				ctrl.stop();
				syncControllers.remove(ctrl);
				ctrl.getConnectionController().stop();
				connectionControllers.remove(ctrl.getConnectionController());
				break;
			}
		}
				
		notifyChangeListeners(ACCOUNTS);
	}

	public List<Account> getAccounts() {
		return Collections.unmodifiableList(accounts);
	}

	// Connection Controller
	public ConnectionController addConnectionController(Account account) {
		ConnectionController con = new ConnectionController(account);
		connectionControllers.add(con);
		return con;
	}
	
	public List<ConnectionController> getConnectionControllers() {
		return Collections.unmodifiableList(connectionControllers);
	}
	
	public void removeConnectionController(Account account) {
		for (ConnectionController connectionController : connectionControllers) {
			if (connectionController.getAccount() == account) {
				connectionController.stop();
				connectionControllers.remove(connectionController);
			}
		}
	}
	
	// Sync Controller
	public void addSyncController(Account account, ConnectionController con) {
		syncControllers.add(new SyncController(account, con));
	}
	
	public List<SyncController> getSyncControllers() {
		return Collections.unmodifiableList(syncControllers);
	}

	public void removeSyncController(Account account) {
		for (SyncController syncController : syncControllers) {
			if (syncController.getAccount() == account) {
				syncController.stop();
				syncControllers.remove(syncController);
			}
		}
	}
	
	// Active account
	public Account getActiveAccount() {
		return activeAccount;
	}

	public void setActiveAccount(Account activeAccount) {
		Account oldActiveAccount = getActiveAccount();
		this.activeAccount = activeAccount;
		notifyChangeListeners(ACTIVE_ACCOUNT, oldActiveAccount, getActiveAccount());
	}

	// Active folder
	public Folder getActiveFolder() {
		return activeFolder;
	}

	public void setActiveFolder(Folder activeFolder) {
		Folder oldActiveFolder = getActiveFolder();
		this.activeFolder = activeFolder;
		notifyChangeListeners(ACTIVE_FOLDER, oldActiveFolder, getActiveFolder());
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
		notifyChangeListeners(propertyName, null, null);
	}
	
	private void notifyChangeListeners(String propertyName, Object oldValue, Object newValue) {
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(this, propertyName, oldValue, newValue));
		}
	}
	
	public void addChangeListener(PropertyChangeListener listener) {
		listeners.add(listener);
	}

	public void removeChangeListener(PropertyChangeListener listener) {
		listeners.remove(listener);
	}

}
