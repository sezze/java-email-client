package client.controllers;

import java.util.Properties;
import java.util.logging.Level;

import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import client.Main;
import client.models.Account;

public class ConnectionController {

	// Properties
	private Account account;
	private Session session;
	private Store store;
	
	public ConnectionController(Account account) {
		this.account = account;
	}
	
	public void connect() throws MessagingException {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		setSession(Session.getDefaultInstance(props));
		store.connect(account.getImapAddress(), account.getUsername(), account.getPassword());
	}
	
	public void stop() {
		try {
			store.close();
		} catch (MessagingException e) {
			Main.LOGGER.log(Level.WARNING, "Failed to close the store.", e);
			store = null;
		}
	}
	
	/*
	 * Property getters and setters
	 */
	
	// Account
	public Account getAccount() {
		return account;
	}
	
	// Session
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
	
	// Is connected
	public boolean isConnected() {
		return store != null ? store.isConnected() : false;
	}
	
	// Store
	public Store getStore() {
		return store;
	}
	
}
