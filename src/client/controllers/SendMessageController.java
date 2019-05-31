package client.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import client.Main;
import client.mappers.ContactMapper;
import client.models.Contact;
import client.models.Message;
import client.util.DialogUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SendMessageController implements Initializable{

	@FXML
	private TextField toField;
	
	@FXML
	private TextField ccField;
	
	@FXML
	private TextField bccField;
	
	@FXML
	private TextField subjectField;
	
	@FXML
	private TextArea bodyTextArea;

	private ConnectionController con;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	private void send() {
		String recipientsStr = toField.getText().trim();
		String ccRecipientsStr = ccField.getText().trim();
		String bccRecipientsStr = bccField.getText().trim();
		String subject = subjectField.getText().trim();
		String body = bodyTextArea.getText().trim();
		
		if (recipientsStr.isEmpty()) { DialogUtil.showWarning("Recipients are missing!"); return; };
		
		List<Contact> recipients = new ArrayList<Contact>();
		for (String recipient : recipientsStr.split(";|,")) {
			try {
				recipients.add(ContactMapper.map(recipient));
			} catch (AddressException e) {
				Main.LOGGER.log(Level.FINE, "Improper address formatting", e);
				DialogUtil.showWarning("Recipient " + recipient + " is improperly formatted!");
				return;
			}
		}
		
		List<Contact> ccRecipients = new ArrayList<Contact>();
		if (!ccRecipientsStr.isEmpty())
			for (String recipient : ccRecipientsStr.split(";|,")) {
				try {
					ccRecipients.add(ContactMapper.map(recipient));
				} catch (AddressException e) {
					Main.LOGGER.log(Level.FINE, "Improper address formatting", e);
					DialogUtil.showWarning("CC Recipient " + recipient + " is improperly formatted!");
					return;
				}
			}
		
		List<Contact> bccRecipients = new ArrayList<Contact>();
		if (!bccRecipientsStr.isEmpty())
			for (String recipient : bccRecipientsStr.split(";|,")) {
				try {
					bccRecipients.add(ContactMapper.map(recipient));
				} catch (AddressException e) {
					Main.LOGGER.log(Level.FINE, "Improper address formatting", e);
					DialogUtil.showWarning("BCC Recipient " + recipient + " is improperly formatted!");
					return;
				}
			}
		
		Message message = new Message.Builder()
				.sender(new Contact(con.getAccount()))
				.addRecipients(recipients)
				.addCcRecievers(ccRecipients)
				.addBccRecievers(bccRecipients)
				.subject(subject)
				.body(body, false)
				.build();
		
		try {
			con.sendMessage(message);
		} catch (MessagingException e) {
			Main.LOGGER.log(Level.WARNING, "Failed to send", e);
			DialogUtil.showWarning("Failed to send message.");
			return;
		}
		
	}
	
	@FXML
	private void discard() {
		if (DialogUtil.confirm("Are you sure you want to clear?")) {
			toField.clear();
			ccField.clear();
			bccField.clear();
			subjectField.clear();
			bodyTextArea.clear();
			toField.requestFocus();
		}
	}
	
	public void loadMessage(Message message) {
		toField.setText(message.getSender().toString());
		subjectField.setText("Re: " + message.getSubject());
		bodyTextArea.requestFocus();
	}

	public void setConnectionController(ConnectionController con) {
		this.con = con;
	}
	
}
