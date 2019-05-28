package client.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import client.Main;
import client.models.Account;
import client.models.Client;
import client.models.Contact;
import client.models.Folder;
import client.models.Message;
import client.views.components.FolderTree;
import client.views.components.MessageListItemPane;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

public class ClientController implements Initializable, Observer {
	
	@FXML
	private Pane messagePane;
	
	@FXML
	private ComboBox<Account> accountsComboBox;
	
	@FXML
	private Pane folderPane;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Account account = new Account("Test User", "test@nrng.gq", "test@nrng.gq", "TestPassword", "smtp.yandex.com", "imap.yandex.com");
		Main.client.addAccount(account);		
		
		Message message = new Message.Builder()
				.sender(new Contact("Bill Gates", "bill@microsoft.com"))
				.addRecipients(new ArrayList<Contact>() {{new Contact("Sebastian", "sebbe.aarnio@hotmail.com");}})
				.subject("Dear Sebastian, I have now sent you the million dollars that I've promised you!")
				.body("WOWA, <b><i>this</i> is the body</b>", true)
				.build();
		messagePane.getChildren().add(new MessageListItemPane(message));
		CacheController.cacheMessage(message);
		
		Folder testFolder = new Folder("Rooty");
		Folder inboxy = new Folder("INBOXY");
		testFolder.addFolder(inboxy);
		Folder testy = new Folder("TESTY");
		inboxy.addFolder(testy);
		folderPane.getChildren().add(new FolderTree(testFolder));
		
		updateAccountsComboBox();
		Main.client.addObserver(this);
		Main.client.getActiveAccount().addObserver(this);
	}

	@FXML
	private void onAccountsComboBoxChange() {
		Main.client.setActiveAccount(accountsComboBox.getValue());
	}
	
	private void updateAccountsComboBox() {
		accountsComboBox.setItems(FXCollections.observableArrayList(Main.client.getAccounts()));
		accountsComboBox.setValue(Main.client.getActiveAccount());
	}
	
	
	
	@Override
	public void update(Observable o, Object arg) {
		Client client = (Client) o;
		Client.UpdateType type = (Client.UpdateType) arg;
		if (type == Client.UpdateType.ACCOUNTS)
			updateAccountsComboBox();
		
	}
	
}
