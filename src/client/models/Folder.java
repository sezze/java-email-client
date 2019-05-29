package client.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Folder {

	// Property names
	public static final String NAME = "name";
	public static final String FOLDERS = "folders";
	public static final String MESSAGES = "messages";
	
	// Properties
	private String name;
	private List<Folder> subFolders;
	private List<Message> messages;
	
	// Property listeners
	private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

	public Folder(String name) {
		this.name = name;
		this.subFolders = new ArrayList<Folder>();
		this.messages = new ArrayList<Message>();
	}

	/*
	 * Property getters and setters
	 */
	
	// Name
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
		notifyChangeListeners(this, NAME);
	}

	// Messages
	public void addMessage(Message message) {
		messages.add(message);
		notifyChangeListeners(this, MESSAGES);
	}
	
	public void addMessages(Collection<Message> messages) {
		this.messages.addAll(messages);
		notifyChangeListeners(this, MESSAGES);
	}
	
	public void removeMessage(Message message) {
		messages.remove(message);
		notifyChangeListeners(this, MESSAGES);
	}
	
	public void removeMessages(Collection<Message> messages) {
		this.messages.removeAll(messages);
		notifyChangeListeners(this, MESSAGES);
	}
	
	public List<Message> getMessages() {
		return Collections.unmodifiableList(messages);
	}
	
	// Folders
	public void addFolder(Folder folder) {
		subFolders.add(folder);
		notifyChangeListeners(this, FOLDERS);
	}

	public void addFolders(Collection<Folder> folders) {
		folders.addAll(folders);
		notifyChangeListeners(this, FOLDERS);
	}

	public List<Folder> getFolders() {
		return Collections.unmodifiableList(subFolders);
	}
	
	public Folder getFolderFromName(String name) {
		for (Folder subFolder : subFolders) {
			if (subFolder.getName().equals(name)) {
				return subFolder;
			}
		}
		throw new IllegalArgumentException("No subfolder with name "+name); 
	}
	
	public void removeFolder(Folder folder) {
		subFolders.remove(folder);
		notifyChangeListeners(this, FOLDERS);
	}
	
	/*
	 * Property listeners
	 */
	private void notifyChangeListeners(Folder folder, String propertyName) {
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(folder, propertyName, null, null));
		}
	}
	
	public void addChangeListener(PropertyChangeListener listener) {
		listeners.add(listener);
	}
	
	public void removeChangeListener(PropertyChangeListener listener) {
		listeners.remove(listener);
	}
	
	/*
	 * To string
	 */
	@Override
	public String toString() {
		return name;
	}

}
