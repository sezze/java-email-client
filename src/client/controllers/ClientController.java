package client.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javax.mail.internet.MimeMessage;

import client.models.Contact;
import client.models.Message;
import client.views.components.MessageListItemPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class ClientController implements Initializable {
	
	@FXML
	private VBox messageList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Contact sender = new Contact("Bill Gates", "bill@microsoft.com");
		Contact[] recievers = {new Contact("Sebastian", "sebbe.aarnio@hotmail.com")};
		Message message = new Message(sender, recievers, LocalDateTime.now(), "Message subject", false, "This is the message body.");
		messageList.getChildren().add(new MessageListItemPane(message));
	}
	
}
