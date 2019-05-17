package client.views.components;

import java.io.UnsupportedEncodingException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MessageItemPane extends StackPane {
	public MessageItemPane(Message message) {
		super();
		VBox vBox = new VBox();
		this.getChildren().add(vBox);
		Label authorLabel = new Label("Error");
		Label subjectLabel = new Label("Failed to get message information");
		Label dateLabel = new Label("--:--");
		try {

			authorLabel.setText(MimeUtility.decodeText(message.getFrom()[0].toString()));
			subjectLabel.setText(message.getSubject());
			dateLabel.setText(message.getSentDate().toString());
		} catch (MessagingException e) {
			System.out.println("Failed to get email message information");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		vBox.getChildren().addAll(authorLabel, subjectLabel, dateLabel);
	}
}
