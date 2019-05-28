package client.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Folder {

	private String name;
	private List<Folder> subFolders;
	private List<Message> messages;
	
	private List<PropertyChangeListener> listeners = new ArrayList<PropertyChangeListener>();

	public Folder(String name) {
		this.name = name;
		this.subFolders = new ArrayList<Folder>();
		this.messages = new ArrayList<Message>();
	}

	public String getName() {
		return name;
	}

	public void addMessage(Message message) {
		messages.add(message);
		notifyChangeListeners();
	}
	
	public void addMessages(Collection<Message> messages) {
		this.messages.addAll(messages);
		notifyChangeListeners();
	}
	
	public List<Message> getMessages() {
		return Collections.unmodifiableList(messages);
	}

	public void addFolder(Folder folder) {
		subFolders.add(folder);
		notifyChangeListeners();
	}

	public void addFolders(Collection<Folder> folders) {
		folders.addAll(folders);
		notifyChangeListeners();
	}

	public List<Folder> getFolders() {
		return Collections.unmodifiableList(subFolders);
	}
	
	private void notifyChangeListeners() {
		for (PropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(this, null, null, null));
		}
	}
	
	public void addChangeListener(PropertyChangeListener listener) {
		listeners.add(listener);
	}
	
	public void removeChangeListener(PropertyChangeListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public String toString() {
		return name;
	}

}
