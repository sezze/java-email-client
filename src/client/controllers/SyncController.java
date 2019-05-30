package client.controllers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import javax.mail.MessagingException;

import client.Main;
import client.mappers.FolderMapper;
import client.models.Account;
import client.models.Folder;

public class SyncController{
	
	/* 
	 * TASKS:
	 * Download new messages✔️ ,download folder and message changes✔ ,message flags ✔️ ,(local folder changes), (send messages)
	 */
	
	// Properties
	private Account account;
	private ConnectionController con;
	private boolean isSyncing;
	private boolean stopping;
	
	// Sync listeners
	private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();
	
	Thread syncTimerThread;
	PropertyChangeListener folderListener;

	public SyncController(Account account, ConnectionController con) {		
		this.account = account;
		this.con = con;
		
//		folderListener = new PropertyChangeListener() {
//			@Override
//			public void propertyChange(PropertyChangeEvent evt) {
//				Main.LOGGER.log(Level.WARNING, "Syncing not implemented!");
//			}
//		};
		
//		account.getRootFolder().addChangeListener(folderListener);
		
		syncTimerThread = new Thread() {
			public void run() {
				while (!interrupted() && !stopping) {
					sync(account.getRootFolder());
					
					try {
						sleep(account.getSyncRate());
					} catch (InterruptedException e) {
						Main.LOGGER.log(Level.FINE, "The thread was interrupted.", e);
					}
				}
			}
		};
		syncTimerThread.start();
	}
	
	private void sync(Folder currentFolder) {
		if (stopping) {
			return;
		}
		setSyncing(true);
		// Check if connected
		if (!con.isConnected()) {
			try {
				con.connect();
			} catch (MessagingException e) {
				Main.LOGGER.log(Level.INFO, "Failed to connect with IMAP server.", e);
				setSyncing(false);
				return;
			}
			if (!con.isConnected()) {
				Main.LOGGER.log(Level.INFO, "Connection with IMAP server could not be established.");
				setSyncing(false);
				return;
			}
		}
		
		try {
			FolderMapper.mapToExisting(con.getStore().getDefaultFolder(), currentFolder, true);
		} catch (MessagingException | IOException e) {
			Main.LOGGER.log(Level.WARNING, "Couldn't get server messages", e);
		}
		
		setSyncing(false);
		
	}
	
	// Stop thread and listeners
	public void stop() {
		stopping = true;
		syncTimerThread.interrupt();
		account.getRootFolder().removeChangeListener(folderListener);
	}
	
	public void stopAndJoin() {
		stop();
		try {
			syncTimerThread.join();
		} catch (InterruptedException e) {
			Main.LOGGER.log(Level.FINE, "Sync thread interrupted.", e);
		}
	}
	
	
	/*
	 * Property getters and setters
	 */
	
	// Account
	public Account getAccount() {
		return account;
	}

	// Syncing
	public boolean isSyncing() {
		return isSyncing;
	}

	private void setSyncing(boolean isSyncing) {
		this.isSyncing = isSyncing;
		notifyChangeListeners(isSyncing);
	}
	
	// Connection
	public ConnectionController getConnectionController() {
		return con;
	}


	/*
	 * Property listeners
	 */
	private void notifyChangeListeners(boolean value) {
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(this, null, !value, value));
		}
	}

	public void addChangeListener(PropertyChangeListener listener) {
		listeners.add(listener);
	}

	public void removeChangeListener(PropertyChangeListener listener) {
		listeners.remove(listener);
	}
	
}
