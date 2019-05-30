package client.controllers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

import client.Main;
import client.models.Account;
import client.models.Client;
import client.models.Folder;
import client.models.Message;
import client.views.components.FolderTree;
import javafx.application.Platform;
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
	
	PropertyChangeListener activeAccountListener;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		activeAccountListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName() == Account.ROOT_FOLDER) {
					Platform.runLater(() -> folderTree.setFolder(Main.CLIENT.getActiveAccount().getRootFolder()));
				}
			}
		};
		
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
			case Client.ACTIVE_ACCOUNT:
				System.out.println("FIRED");
				if (e.getOldValue() != null) {
					((Account) e.getOldValue()).removeChangeListener(activeAccountListener);					
				}
				Main.CLIENT.getActiveAccount().addChangeListener(activeAccountListener);
				Platform.runLater(() -> folderTree.setFolder(Main.CLIENT.getActiveAccount().getRootFolder()));
				break;
			}
		});

		// Folder tree selection listener
		folderTree.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends TreeItem<Folder>> o, TreeItem<Folder> oldValue,
						TreeItem<Folder> newValue) -> {
							// Set active folder to selection
							Main.CLIENT.setActiveFolder(newValue != null ? newValue.getValue() : null);
							System.out.println("-----  " + Main.CLIENT.getActiveFolder() + "  -----");
							if (Main.CLIENT.getActiveFolder() != null) {
								for (Message message : Main.CLIENT.getActiveFolder().getMessages()) {
									System.out.println(message.getSubject());
								}
							}
						});
		
		Account testAccount = new Account("Test User", "test@nrng.gq", "test@nrng.gq", "TestPassword", "smtp.yandex.com", "imap.yandex.com", 60000);
		
		Main.CLIENT.addAccount(testAccount);
		
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
