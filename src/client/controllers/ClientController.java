package client.controllers;

import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.ResourceBundle;

import client.Main;
import client.models.Account;
import client.models.Client;
import client.models.Folder;
import client.models.Message;
import client.views.components.FolderTree;
import client.views.components.MessageListItemPane;
import client.views.components.MessageView;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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

	@FXML
	private ScrollPane messageViewContainer;
	
	@FXML
	private TextField searchField;
	
	private FolderTree folderTree;

	PropertyChangeListener activeFolderListener;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
		activeFolderListener = e -> {
			if (e.getPropertyName() == Folder.MESSAGES) {
				Platform.runLater(() -> updateMessageList());
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
				if (Main.CLIENT.getActiveAccount() != null) {
					Platform.runLater(() -> folderTree.setFolder(Main.CLIENT.getActiveAccount().getRootFolder()));
				} else {
					Platform.runLater(() -> folderTree.setFolder(null));
				}
				break;
			case Client.ACTIVE_FOLDER:
				searchField.setText("");
				updateMessageList();
				if (e.getOldValue() != null) {
					((Folder) e.getOldValue()).removeChangeListener(activeFolderListener);					
				}
				if (e.getNewValue() != null) {
					Main.CLIENT.getActiveFolder().addChangeListener(activeFolderListener);
				}
				break;
			case Client.ACTIVE_MESSAGE:
				updateMessageView();
				break;
			}
		});
		
		// Folder tree selection listener
		folderTree.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends TreeItem<Folder>> o, TreeItem<Folder> oldValue,
						TreeItem<Folder> newValue) -> {
							// Set active folder to selection
							Main.CLIENT.setActiveFolder(newValue != null ? newValue.getValue() : null);
						});
		
		// Search listener
		searchField.textProperty().addListener((o, oldValue, newValue) -> search(newValue));
		
		//Account testAccount = new Account();
		//Main.CLIENT.addAccount(testAccount);
		
		for (Account account : CacheController.loadAllAccounts()) {
			Main.CLIENT.addAccount(account);
		}
		
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
	
	private void search(String query) {
		query = query.toLowerCase();
		for (Node node : messagePane.getChildren()) {
			MessageListItemPane pane = (MessageListItemPane) node;
			Message msg = pane.getMessage();
			String str = msg.getSubject();
			str += msg.getSender().getName() + msg.getSender().getAddress();
			str += MessageListItemPane.FORMAT.format(msg.getDate());
			str = str.toLowerCase();
			boolean visible = str.indexOf(query) != -1;
			pane.setVisible(visible);
			pane.setManaged(visible);
		}
	}
	
	private void updateMessageList() {
		for (Node node : messagePane.getChildren()) {
			((MessageListItemPane) node).onDestroy();
		}
		messagePane.getChildren().clear();
		if (Main.CLIENT.getActiveFolder() != null) {
			for (Message message : Main.CLIENT.getActiveFolder().getMessages()) {
				messagePane.getChildren().add(new MessageListItemPane(message));
			}
		}
	}
	
	private void updateMessageView() {
		messageViewContainer.setContent(new MessageView(Main.CLIENT.getActiveMessage()));
	}

}
