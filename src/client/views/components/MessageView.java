package client.views.components;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import client.models.Contact;
import client.models.Message;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class MessageView extends VBox {

	private static final Map<String, String> FILE_ICONS = new HashMap<String, String>();
	private static final String DEFAULT_FILE_ICON = "file/file";
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("E dd.MM.yyyy HH:mm");
	
	// Child components
	Label subjectLabel;
	VBox messageContent;
	
	public MessageView(Message message) {
		if (FILE_ICONS.size() == 0) {
			FILE_ICONS.put("application/zip", "file/archive");
			FILE_ICONS.put("application/x-7z-compressed", "file/archive");
			FILE_ICONS.put("application/x-rar-compressed", "file/archive");
			FILE_ICONS.put("application/pdf", "file/pdf");
			FILE_ICONS.put("application/msword", "file/word");
		}
		
		getStyleClass().add("message-view");
		
		
		/*
		 * Subject Label
		 */
		subjectLabel = new Label(message.getSubject());
		
		
		/*
		 * Message Content
		 */
		messageContent = new VBox();
		
		VBox details = new VBox();
		
		Pane senderPane = new ContactPane(message.getSender(), false);
		Label sentDateLabel = new Label(FORMAT.format(message.getDate()));
		// Recipients
		FlowPane recipientPane = new FlowPane();
		ObservableList<Node> c = recipientPane.getChildren();
		c.add(new Label("To:"));
		for (Contact contact : message.getRecipients()) {
			c.add(new ContactPane(contact, false));
		}
		
		details.getChildren().addAll(senderPane, sentDateLabel, recipientPane);
		
		// CC
		if (message.getCcRecipients().size() > 0) {
			FlowPane ccRecipientPane = new FlowPane();
			c = ccRecipientPane.getChildren();
			c.add(new Label("CC:"));
			for (Contact contact : message.getCcRecipients()) {
				c.add(new ContactPane(contact, false));
			}
			details.getChildren().add(ccRecipientPane);
		}
		
		// BCC
		if (message.getBccRecipients().size() > 0) {
			FlowPane bccRecipientPane = new FlowPane();
			c = bccRecipientPane.getChildren();
			c.add(new Label("BCC:"));
			for (Contact contact : message.getBccRecipients()) {
				c.add(new ContactPane(contact, false));
			}
			details.getChildren().add(bccRecipientPane);
		}
		
		WebView content = new WebView();
		content.getEngine().setUserStyleSheetLocation(getClass().getResource("../../assets/styles/webview.css").toString());
		content.getEngine().setJavaScriptEnabled(false);
		if (message.isHTML()) {
			content.getEngine().loadContent(message.getBody());
		} else {
			content.getEngine().loadContent("<div class='plain-text'>" + message.getBody() + "</div>");
		}
		messageContent.getChildren().addAll(details, content);
		
		getChildren().addAll(subjectLabel, messageContent);
	}
	
}
