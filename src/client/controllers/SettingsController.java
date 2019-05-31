package client.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import client.Main;
import client.models.Account;
import client.models.Client;
import client.util.DialogUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SettingsController implements Initializable {

	static final int[] SYNC_RATES = new int[] {10000,30000,60000,600000,1800000,3600000};
	static final String[] SYNC_RATE_LABELS = new String[] {"10 Seconds","30 Seconds", "1 Minute", "10 Minutes", "30 Minutes", "1 Hour"};
	
	@FXML
	private ListView<Account> accountsListView;
	
	@FXML
	private Label createAccountLabel;
	
	@FXML
	private TextField displayNameField;
	
	@FXML
	private TextField emailAddressField;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private TextField imapAddressField;
	
	@FXML
	private TextField smtpAddressField;
	
	@FXML
	private ComboBox<String> rateComboBox;
	
	@FXML
	private Button createButton;
	
	private boolean _isEditingAccount;
	private Account editingAccount;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		rateComboBox.getItems().addAll(SYNC_RATE_LABELS);
		rateComboBox.setValue(SYNC_RATE_LABELS[2]);
		
		updateAccountsList();
		setEditingAccount(false);
		
		/*
		 * Event listeners
		 */
		Main.CLIENT.addChangeListener(e -> {
			if (e.getPropertyName() == Client.ACCOUNTS) {
				updateAccountsList();
			}
		});
	}
	
	private void updateAccountsList() {
		accountsListView.getItems().clear();
		for (Account account : Main.CLIENT.getAccounts()) {
			accountsListView.getItems().add(account);
		}
	}

	@FXML
	private void createAccount() {
		String displayName = displayNameField.getText().trim();
		String emailAddress = emailAddressField.getText().trim();
		String username = usernameField.getText().trim();
		String password = passwordField.getText();
		String imapAddress = imapAddressField.getText().trim();
		String smtpAddress = smtpAddressField.getText().trim();
		int syncRateIndex = rateComboBox.getSelectionModel().getSelectedIndex();
		if (displayName.isEmpty()) { DialogUtil.showWarning("Display name missing!"); return; };
		if (emailAddress.isEmpty()) { DialogUtil.showWarning("Email address missing!"); return; };
		if (username.isEmpty()) { DialogUtil.showWarning("Username missing!"); return; };
		if (password.isEmpty()) { DialogUtil.showWarning("Password missing!"); return; };
		if (imapAddress.isEmpty()) { DialogUtil.showWarning("IMAP Address missing!"); return; };
		if (smtpAddress.isEmpty()) { DialogUtil.showWarning("SMTP Address missing!"); return; };
		if (syncRateIndex == -1) { DialogUtil.showWarning("Sync rate missing!"); return; };
		
		int syncRate = SYNC_RATES[syncRateIndex];
		if (isEditingAccount()) {
			Main.CLIENT.removeAccount(editingAccount);
			editingAccount = null;
			setEditingAccount(false);
		}
		Account account = new Account(displayName, emailAddress, username, password, smtpAddress, imapAddress, syncRate);
		Main.CLIENT.addAccount(account);		
		CacheController.cacheAccount(account);
		forceClearFields();
	}
	
	@FXML
	private void configureAccount() {
		Account account = accountsListView.getSelectionModel().getSelectedItem();
		if (account == null) {
			DialogUtil.showWarning("No account selected.");
			return;
		}
		displayNameField.setText(account.getDisplayName());
		emailAddressField.setText(account.getAddress());
		usernameField.setText(account.getUsername());
		passwordField.setText(account.getPassword());
		imapAddressField.setText(account.getImapAddress());
		smtpAddressField.setText(account.getSmtpAddress());
		for (int i = 0; i < SYNC_RATES.length; i++) {
			if (SYNC_RATES[i] == account.getSyncRate()) {
				rateComboBox.setValue(SYNC_RATE_LABELS[i]);
			}
		}
		setEditingAccount(true);
		editingAccount = account;
		
		displayNameField.requestFocus();
	}
	
	@FXML
	private void removeAccount() {
		Account account = accountsListView.getSelectionModel().getSelectedItem();
		if (account == null) {
			DialogUtil.showWarning("No account selected.");
			return;
		}
		Main.CLIENT.removeAccount(account);
	}
	
	@FXML
	private void clearFields() {
		if (DialogUtil.confirm("Are you sure you want to clear all fields?")) {
			forceClearFields();
		}
	}
	
	private void forceClearFields() {
		displayNameField.clear();
		emailAddressField.clear();
		usernameField.clear();
		passwordField.clear();
		imapAddressField.clear();
		smtpAddressField.clear();
		rateComboBox.setValue(SYNC_RATE_LABELS[2]);
		displayNameField.requestFocus();
	}

	
	/*
	 * Getters and setters
	 */
	public boolean isEditingAccount() {
		return _isEditingAccount;
	}

	public void setEditingAccount(boolean isEditingAccount) {
		if (isEditingAccount) {
			createAccountLabel.setText("Edit account");
		} else {
			createAccountLabel.setText("Create account");
		}
		this._isEditingAccount = isEditingAccount;
	}
	
}
