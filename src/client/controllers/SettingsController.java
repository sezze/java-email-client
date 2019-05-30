package client.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class SettingsController implements Initializable {

	@FXML
	private ListView accountsListView;
	
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
	private Slider rateSlider;
	
	@FXML
	private Button createButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void createAccount() {
		
	}
	
	@FXML
	private void configureAccount() {
		
	}
	
	@FXML
	private void removeAccount() {
		
	}
	
	@FXML
	private void clearFields() {
		
	}
	
}
