package client.views.components;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import javax.mail.MessagingException;

import client.Main;
import client.controllers.ConnectionController;
import client.models.Attachment;
import client.models.Message;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class AttachmentPane extends HBox{

	private VBox detailVBox;
	private Label nameLabel;
	private Label sizeLabel;
	private Icon icon;
	
	public AttachmentPane(Attachment attachment, Message message) {
		getStyleClass().add("attachment");
		detailVBox = new VBox();
		nameLabel = new Label(attachment.getName());
		sizeLabel = new Label((attachment.getSize()/1024) + " kB");
		detailVBox.getChildren().addAll(nameLabel, sizeLabel);
		
		icon = new Icon("file/file", 32);
		
		getChildren().addAll(icon, detailVBox);
		
		setOnMouseClicked(e -> {
			if (e.getButton().equals(MouseButton.PRIMARY)) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Java Mail Client - Save Attachment");
				fileChooser.setInitialFileName(attachment.getName());
				File file = fileChooser.showSaveDialog(Main.getPrimaryStage());
				if (file != null) {
					try {
						ConnectionController.downloadAttachment(file, attachment, message);
					} catch (MessagingException | IOException e1) {
						Main.LOGGER.log(Level.WARNING, "Attachment could not be saved.", e1);
					}
				}
			}
		});
		
	}
	
}
