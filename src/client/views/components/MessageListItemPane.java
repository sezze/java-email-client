package client.views.components;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

import client.Main;
import client.models.Message;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
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
		senderPane.getStyleClass().add("message-sender");

		// Flags
		flagRow = new HBox();
		flagRow.getStyleClass().add("message-flags");
		HBox.setHgrow(flagRow, Priority.ALWAYS);
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
			Icon attachmentIcon = new Icon("clip", 18);
			attachmentRow.getChildren().addAll(attachmentIcon, attachmentLabel);
			lowerRow.getChildren().add(attachmentRow);
		}

		// Date row
		dateRow = new HBox();
		HBox.setHgrow(dateRow, Priority.ALWAYS);
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
	        if(e.getButton().equals(MouseButton.PRIMARY)){
	            if(e.getClickCount() == 2){
	            	// Reply to message
	                try {
						Main.CLIENT.openSendMessageStage(message);
					} catch (IOException e1) {
						Main.LOGGER.log(Level.SEVERE, "Could not open send message dialog", e1);
					}
	            } else {
	            	// Open message
	            	Main.CLIENT.setActiveMessage(message);
	            }
	        }
		});
	}

	private void setFlags() {
		ObservableList<Node> f = flagRow.getChildren();
		f.clear();
		if (message.isAnswered())
			f.add(new Icon("reply", 18));
		if (message.isFlagged())
			f.add(new Icon("flag", 18));
		if (message.isDeleted())
			f.add(new Icon("deleted", 18));
		
		pseudoClassStateChanged(PseudoClass.getPseudoClass("unread"), !message.isSeen());
	}
	
	public void onDestroy() {
		message.removeChangeListener(messageListener);
	}
	
	public Message getMessage() { return message; }

}
