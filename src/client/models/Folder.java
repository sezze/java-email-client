package client.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Folder implements Serializable {

	// Property names
	public static final String NAME = "name";
	public static final String FOLDERS = "folders";
	public static final String MESSAGES = "messages";
	
	// Properties
	private String name;
	private ArrayList<Folder> subFolders;
	private ArrayList<Message> messages;
	private transient boolean isSetup;
	
	// Property listeners
	private transient List<PropertyChangeListener> listeners;
	private transient PropertyChangeListener childListener;

	private static final long serialVersionUID = 8544618504357081546L;
	
	public Folder(String name) {
		this.name = name;
		this.subFolders = new ArrayList<Folder>();
		this.messages = new ArrayList<Message>();
		
	}

	public void checkListeners() {
		if (!isSetup) {
			listeners = new ArrayList<PropertyChangeListener>();
			childListener = e -> {
				notifyChangeListeners(e.getSource(), e.getPropertyName());
			};
			isSetup = true;
		}
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
		checkListeners();
		messages.add(message);
		message.addChangeListener(childListener);
		notifyChangeListeners(this, MESSAGES);
	}
	
	public void addMessages(Collection<Message> messages) {
		checkListeners();
		this.messages.addAll(messages);
		for (Message message : messages) {
			message.addChangeListener(childListener);
		}
		notifyChangeListeners(this, MESSAGES);
	}
	
	public void removeMessage(Message message) {
		checkListeners();
		messages.remove(message);
		message.removeChangeListener(childListener);
		notifyChangeListeners(this, MESSAGES);
	}
	
	public void removeMessages(Collection<Message> messages) {
		checkListeners();
		this.messages.removeAll(messages);
		for (Message message : messages) {
			message.removeChangeListener(childListener);
		}
		notifyChangeListeners(this, MESSAGES);
	}
	
	public List<Message> getMessages() {
		return Collections.unmodifiableList(messages);
	}
	
	// Folders
	public void addFolder(Folder folder) {
		checkListeners();
		subFolders.add(folder);
		folder.addChangeListener(childListener);
		notifyChangeListeners(this, FOLDERS);
	}

	public void addFolders(Collection<Folder> folders) {
		checkListeners();
		folders.addAll(folders);
		for (Folder folder : folders) {
			folder.addChangeListener(childListener);
		}
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
		checkListeners();
		subFolders.remove(folder);
		folder.removeChangeListener(childListener);
		notifyChangeListeners(this, FOLDERS);
	}
	
	/*
	 * Property listeners
	 */
	private void notifyChangeListeners(Folder folder, String propertyName) {
		checkListeners();
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(folder, propertyName, null, null));
		}
	}
	
	private void notifyChangeListeners(Object o, String propertyName) {
		checkListeners();
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(o, propertyName, null, null));
		}
	}
	
	public void addChangeListener(PropertyChangeListener listener) {
		checkListeners();
		listeners.add(listener);
	}
	
	public void removeChangeListener(PropertyChangeListener listener) {
		checkListeners();
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
