package client.controllers;

import java.util.Properties;
import java.util.logging.Level;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

import com.sun.mail.util.MailConnectException;

import client.Main;
import client.mappers.MessageMapper;
import client.models.Account;
import client.models.Message;
import client.util.DialogUtil;
import javafx.application.Platform;

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
		try {
			store.connect(account.getImapAddress(), account.getUsername(), account.getPassword());
		} catch (AuthenticationFailedException e) {
			Platform.runLater(() -> DialogUtil.showWarning(account.getAddress() + " failed to authenticate, check your details."));
			Main.LOGGER.log(Level.INFO, "Failed to authenticate to the server.", e);
		} catch (MailConnectException e) {
			Main.LOGGER.log(Level.INFO, "Failed to connect to " + account.getImapAddress(), e);
		}
	}
	
	public void stop() {
		try {
			store.close();
		} catch (MessagingException e) {
			Main.LOGGER.log(Level.WARNING, "Failed to close the store.", e);
			store = null;
		}
	}
	
	public void sendMessage(Message message) throws MessagingException {
		
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtps.host", account.getSmtpAddress());
		properties.setProperty("mail.smtps.port", "465");
		properties.setProperty("mail.smtps.auth", "true");
		properties.setProperty("mail.user", account.getUsername());
		properties.setProperty("mail.password", account.getPassword());
		properties.setProperty("mail.debug", "true");
		Session session = Session.getDefaultInstance(properties);
		Transport transport = session.getTransport("smtps");
		transport.connect(null, properties.getProperty("mail.password"));
		javax.mail.Message msg = MessageMapper.map(message, session);
		msg.saveChanges();
		transport.sendMessage(msg, msg.getAllRecipients());
		transport.close();
		
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
