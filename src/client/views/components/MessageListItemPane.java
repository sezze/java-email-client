package client.views.components;

import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;

import client.Main;
import client.models.Message;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MessageListItemPane extends VBox {

	private Message message;

	// Child components
	private HBox upperRow;
	private Pane senderPane;
	private HBox flagRow;
	private Label subjectLabel;
	private HBox lowerRow;
	private HBox attachmentRow;
	private Label attachmentLabel;
	private HBox dateRow;
	private Label dateLabel;
	
	private PropertyChangeListener messageListener;

	public static final SimpleDateFormat FORMAT = new SimpleDateFormat("HH:mm dd.MM.yyyy");

	public MessageListItemPane(Message message) {
		this.message = message;
		messageListener = e -> {
			// Set flags
			Platform.runLater(() -> setFlags());
		};
		
		// Style class
		getStyleClass().add("message-list-item");

		
		/*
		 * Upper row
		 */
		upperRow = new HBox();
		upperRow.getStyleClass().add("message-upper-row");

		// Sender pane
		senderPane = new ContactPane(message.getSender(), true);

		// Flags
		flagRow = new HBox();
		flagRow.getStyleClass().add("message-flags");
		setFlags();

		upperRow.getChildren().addAll(senderPane, flagRow);

		
		/*
		 * Subject label
		 */
		subjectLabel = new Label(message.getSubject());
		subjectLabel.getStyleClass().add("message-subject");

		
		/*
		 * Lower row
		 */
		lowerRow = new HBox();
		lowerRow.getStyleClass().add("message-lower-row");

		// Attachment row

		if (message.getAttachments().size() > 0) {
			attachmentRow = new HBox();
			attachmentRow.getStyleClass().add("message-attachment-row");
			attachmentLabel = new Label(Integer.toString(message.getAttachments().size()));
			Icon attachmentIcon = new Icon("clip", 24);
			attachmentRow.getChildren().addAll(attachmentIcon, attachmentLabel);
			lowerRow.getChildren().add(attachmentRow);
		}

		// Date row
		dateRow = new HBox();
		dateRow.getStyleClass().add("message-date-row");
		dateLabel = new Label(FORMAT.format(message.getDate()));
		dateRow.getChildren().addAll(dateLabel);

		lowerRow.getChildren().add(dateRow);

		getChildren().addAll(upperRow, subjectLabel, lowerRow);

		
		/*
		 * Event listener
		 */
		message.addChangeListener(messageListener);
		
		setOnMouseClicked(e -> {
			Main.CLIENT.setActiveMessage(message);
		});
	}

	private void setFlags() {
		ObservableList<Node> f = flagRow.getChildren();
		f.clear();
		if (message.isAnswered())
			f.add(new Icon("reply", 24));
		if (message.isFlagged())
			f.add(new Icon("flag", 24));
		if (message.isDeleted())
			f.add(new Icon("deleted", 24));
		
		pseudoClassStateChanged(PseudoClass.getPseudoClass("unread"), !message.isSeen());
	}
	
	public void onDestroy() {
		message.removeChangeListener(messageListener);
	}
	
	public Message getMessage() { return message; }

}
