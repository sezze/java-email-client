package client.models;

import javax.mail.Folder;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ClientModel {
	
	// (Folder) active folder
	private ObjectProperty<Folder> activeFolder = new SimpleObjectProperty<Folder>();
	public ObjectProperty<Folder> activeFolderProperty() { return activeFolder; }
	public void setActiveFolder(Folder val) { activeFolder.set(val); }
	public Folder getActiveFolder() { return activeFolder.get(); }
	
	// (boolean) is busy
	private BooleanProperty isBusy = new SimpleBooleanProperty();
	public BooleanProperty isBusyProperty() { return isBusy; }
	public void setIsBusy(boolean val) { isBusy.set(val); }
	public boolean getIsBusy() { return isBusy.get(); }
	
}
