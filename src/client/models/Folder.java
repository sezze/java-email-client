package client.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

public class Folder extends Observable {

	private String name;
	private List<Folder> subFolders;
	private List<Message> messages;

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
		setChanged();
		notifyObservers();
	}
	
	public void addMessages(Collection<Message> messages) {
		this.messages.addAll(messages);
		setChanged();
		notifyObservers();
	}
	
	public List<Message> getMessages() {
		return Collections.unmodifiableList(messages);
	}

	public void addFolder(Folder folder) {
		subFolders.add(folder);
		setChanged();
		notifyObservers();
	}

	public void addFolders(Collection<Folder> folders) {
		folders.addAll(folders);
		setChanged();
		notifyObservers();
	}

	public List<Folder> getFolders() {
		return Collections.unmodifiableList(subFolders);
	}
	
	@Override
	public String toString() {
		return name;
	}

}
