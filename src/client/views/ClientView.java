package client.views;

import java.io.File;

import client.models.ClientModel;
import client.views.components.ClientMenuBar;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientView {

	public ClientView(ClientModel model, Stage stage) {

		MenuBar menuBar = new ClientMenuBar(stage, model);
		
		VBox layout = new VBox(menuBar);
		
		model.activePageProperty().addListener((o, oldPage, newPage) -> {
			System.out.println("DETECTED");
			layout.getChildren().clear();
			layout.getChildren().add(newPage);
		});
		
		
		// temp show email
//		Properties props = System.getProperties();
//		props.setProperty("mail.store.protocol", "imaps");
//		Session session = Session.getDefaultInstance(props);
//		try {
//			Store store = session.getStore("imaps");
//			store.connect("imap.abo.fi", "user", "password");
//			
//			Folder folder = store.getFolder("INBOX");
//			folder.open(Folder.READ_ONLY);
//			Message message = folder.getMessage(1);
//			
//			MessageItemPane messageItemPane = new MessageItemPane(message);
//			layout.getChildren().add(messageItemPane);
//		} catch (NoSuchProviderException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (MessagingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		
		/*
		 * Scene ---------------------------------------------------
		 */
		
		Scene scene = new Scene(layout, 640, 480);

		// Title
		stage.setTitle("Java Email Client");

		// Icon
		String icon = new File("assets/logo.png").toURI().toString();
		stage.getIcons().add(new Image(icon));

		// Stage configuration
		stage.setScene(scene);
		stage.setMinWidth(320);
		stage.setMinHeight(240);
		stage.show();
	}
	
}
