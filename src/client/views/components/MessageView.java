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
import javafx.scene.layout.Priority;
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
		VBox.setVgrow(this, Priority.ALWAYS);
		
		
		/*
		 * Subject Label
		 */
		subjectLabel = new Label(message.getSubject());
		subjectLabel.getStyleClass().add("message-view-subject");
		
		
		/*
		 * Message Content
		 */
		messageContent = new VBox();
		messageContent.getStyleClass().add("message-view-content");
		VBox.setVgrow(messageContent, Priority.ALWAYS);
		
		VBox details = new VBox();
		details.getStyleClass().add("message-view-details");
		
		Pane senderPane = new ContactPane(message.getSender(), false);
		Label sentDateLabel = new Label(FORMAT.format(message.getDate()));
		
		// Recipients
		FlowPane recipientPane = new FlowPane();
		recipientPane.getStyleClass().add("message-view-recipients");
		ObservableList<Node> c = recipientPane.getChildren();
		c.add(new Label("To:"));
		for (Contact contact : message.getRecipients()) {
			ContactPane cp = new ContactPane(contact, false);
			cp.getStyleClass().add("message-view-recipient");
			c.add(cp);
		}
		
		details.getChildren().addAll(senderPane, sentDateLabel, recipientPane);
		
		// CC
		if (message.getCcRecipients().size() > 0) {
			FlowPane ccRecipientPane = new FlowPane();
			ccRecipientPane.getStyleClass().add("message-view-recipients");
			c = ccRecipientPane.getChildren();
			c.add(new Label("CC:"));
			for (Contact contact : message.getCcRecipients()) {
				ContactPane cp = new ContactPane(contact, false);
				cp.getStyleClass().add("message-view-recipient");
				c.add(cp);
			}
			details.getChildren().add(ccRecipientPane);
		}
		
		// BCC
		if (message.getBccRecipients().size() > 0) {
			FlowPane bccRecipientPane = new FlowPane();
			bccRecipientPane.getStyleClass().add("message-view-recipients");
			c = bccRecipientPane.getChildren();
			c.add(new Label("BCC:"));
			for (Contact contact : message.getBccRecipients()) {
				ContactPane cp = new ContactPane(contact, false);
				cp.getStyleClass().add("message-view-recipient");
				c.add(cp);
			}
			details.getChildren().add(bccRecipientPane);
		}
		
		// Web view
		WebView webView = new WebView();
		webView.getStyleClass().add("message-web-view");
		VBox.setVgrow(webView, Priority.ALWAYS);
		webView.getEngine().setUserStyleSheetLocation(getClass().getResource("../../assets/styles/webview.css").toString());
		webView.getEngine().setJavaScriptEnabled(false);
		if (message.isHTML()) {
			webView.getEngine().loadContent(message.getBody());
		} else {
			webView.getEngine().loadContent("<div class='plain-text'>" + message.getBody() + "</div>");
		}
		
		// Add to children
		messageContent.getChildren().addAll(details, webView);
		getChildren().addAll(subjectLabel, messageContent);
	}
	
}
