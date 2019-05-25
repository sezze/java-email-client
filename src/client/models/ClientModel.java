package client.models;

import javax.mail.Folder;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

public class ClientModel {
	
	/*
	 * Fields
	 */
	
	private ObservableList<Account> accounts = FXCollections.observableArrayList();
	private Pane mainPage;
	private Pane settingsPage;
	
	/*
	 * Properties
	 */
	
	// (Pane) active page
	private ObjectProperty<Pane> activePage = new SimpleObjectProperty<Pane>();
	public ObjectProperty<Pane> activePageProperty() { return activePage; }
	public void setActivePage(Pane val) { activePage.set(val); }
	public Pane getActivePage() { return activePage.get(); }
	
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
	
	/*
	 * Field getters / setters
	 */
	
	public ObservableList<Account> getAccounts() { return accounts; }
	
	public Pane getMainPage() { return mainPage; }
	
	public Pane getSettingsPage() { return settingsPage; }
	
}
