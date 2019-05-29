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
	 * Download new messages, download folder and message changes, message flags, (local folder changes), (send messages)
	 */
	
	// Properties
	private Account account;
	private ConnectionController con;
	private boolean isSyncing;
	
	// Sync listeners
	private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();
	
	Thread syncTimerThread;
	PropertyChangeListener folderListener;

	public SyncController(Account account, ConnectionController con) {
		System.out.println("A Sync Controller has been created.");
		System.out.println(con);
		
		this.account = account;
		this.con = con;
		
		folderListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				Main.LOGGER.log(Level.WARNING, "Syncing not implemented!");
			}
		};
		
		account.getRootFolder().addChangeListener(folderListener);
				
		syncTimerThread = new Thread() {
			public void run() {
				while (!interrupted()) {
					
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
		setSyncing(true);
		
		System.out.println(currentFolder.getFolders().size());
		
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
		
		System.out.println("Finished syncing");
		
		System.out.println(currentFolder.getFolders().size());
		
		setSyncing(false);
	}
	
	// Stop thread and listeners
	public void stop() {
		syncTimerThread.interrupt();
		account.getRootFolder().removeChangeListener(folderListener);
	}
	
	
	/*
	 * Property getters and setters
	 */
	
	// Account
	public Account getAccount() {
		return account;
	}

	public boolean isSyncing() {
		return isSyncing;
	}

	private void setSyncing(boolean isSyncing) {
		this.isSyncing = isSyncing;
		notifyChangeListeners(isSyncing);
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
