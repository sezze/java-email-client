package client.views.components;


import client.models.Message;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MessageListItemPane extends VBox {
	
	private Message message;
	
	private Pane senderPane;
	private Label subjectLabel;
	private Label attachmentLabel;
	private Label dateLabel;
	
	private HBox lowerRow;
	private HBox attachmentRow;
	private HBox dateRow;

	public MessageListItemPane(Message message) {
		this.message = message;
		
		getStyleClass().add("message-list-item");
		
		senderPane = new ContactPane(message.getSender());
		subjectLabel = new Label(message.getSubject());
		
		// Attachment row
		attachmentLabel = new Label(Integer.toString(message.getAttachements().size()));
		ImageView attachmentIcon = new ImageView("client/assets/images/clip.png");
		attachmentIcon.setFitWidth(18);
		attachmentIcon.setFitHeight(18);
		attachmentRow = new HBox();
		attachmentRow.getChildren().addAll(attachmentIcon, attachmentLabel);
		
		// Date row
		dateLabel = new Label(message.getDate().toString());
		dateRow = new HBox();
		dateRow.getChildren().addAll(dateLabel);
		
		// Lower row
		lowerRow = new HBox();
		lowerRow.getChildren().addAll(attachmentRow, dateRow);
		attachmentRow.setAlignment(Pos.CENTER_LEFT);
		dateRow.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(dateRow, Priority.ALWAYS);
		
		getChildren().addAll(senderPane, subjectLabel, lowerRow);
	}

	public Message getMessage() { return message; }
	
}
