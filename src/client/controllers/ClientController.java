package client.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;

import client.Main;
import client.models.Account;
import client.models.Client;
import client.models.Contact;
import client.models.Folder;
import client.models.Message;
import client.views.components.FolderTree;
import client.views.components.MessageListItemPane;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ClientController implements Initializable {

	@FXML
	private Pane messagePane;

	@FXML
	private ComboBox<Account> accountsComboBox;

	@FXML
	private VBox folderTreeContainer;

	private FolderTree folderTree;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		/*
		 * Child components
		 */
		
		// Create child components
		folderTree = new FolderTree();
		folderTreeContainer.getChildren().add(folderTree);

		// Initialize child components
		updateAccountsComboBox();

		/*
		 * Event listeners
		 */
		
		// Client listener
		Main.CLIENT.addChangeListener(e -> {
			switch (e.getPropertyName()) {
			case Client.ACCOUNTS:
				updateAccountsComboBox();
				break;
			}
		});

		// Folder tree selection listener
		folderTree.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends TreeItem<Folder>> o, TreeItem<Folder> oldValue,
						TreeItem<Folder> newValue) -> {
							// Set active folder to selection
							Main.CLIENT.setActiveFolder(newValue.getValue());
						});
	}

	@FXML
	private void onAccountsComboBoxChange() {
		// Set active account to account combo box value
		Main.CLIENT.setActiveAccount(accountsComboBox.getValue());
	}

	private void updateAccountsComboBox() {
		// Set items to available accounts
		accountsComboBox.setItems(FXCollections.observableArrayList(Main.CLIENT.getAccounts()));
		// Set selection to active account
		accountsComboBox.setValue(Main.CLIENT.getActiveAccount());
	}

}
