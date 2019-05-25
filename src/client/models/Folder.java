package client.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Folder {

	private String name;
	private ObservableList<Message> messages;
	
	public Folder(String name) {
		this.name = name;
		this.messages = FXCollections.observableArrayList();
	}

	public String getName() { return name; }
	public ObservableList<Message> getMessages() { return messages; }
	
}
