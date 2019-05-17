package client.views;

import java.io.File;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

import client.models.ClientModel;
import client.views.components.MessageItemPane;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientView {

	public ClientView(ClientModel model, Stage stage) {

		MenuBar menuBar = new MenuBar();
		menuBar.setStyle("-fx-background-color: #f8f8f8;");
		VBox layout = new VBox(menuBar);
		Menu file = new Menu("File");
		// Quit
		MenuItem quit = new MenuItem("Quit");
		quit.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));
		file.getItems().addAll(quit);
		menuBar.getMenus().add(file);
		quit.setOnAction(e -> {
			stage.close();
		});
		
		StringProperty prop = new SimpleStringProperty("Si");
		TextField field1 = new TextField("First value 1");
		prop.bind(field1.textProperty());
		prop.addListener((a,b,c) -> {
			System.out.println(c);
		});
		Label label1 = new Label("Label 1 value");
		label1.textProperty().bind(prop);
		TextField field2 = new TextField("First value 2");
		field2.textProperty().bind(prop);
		Label label2 = new Label("Label 2 value");
		layout.getChildren().addAll(field1, label1, field2, label2);
		
		// temp show email
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		Session session = Session.getDefaultInstance(props);
		try {
			Store store = session.getStore("imaps");
			store.connect("imap.abo.fi", "user", "password");
			
			Folder folder = store.getFolder("INBOX");
			folder.open(Folder.READ_ONLY);
			Message message = folder.getMessage(1);
			
			MessageItemPane messageItemPane = new MessageItemPane(message);
			layout.getChildren().add(messageItemPane);
		} catch (NoSuchProviderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
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
