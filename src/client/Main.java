package client;

import client.views.components.ClientMenuBar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static final String[] STYLESHEETS = {
			"client/assets/styles/theme.css",
			"client/assets/styles/client.css"
	};

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox root = new VBox();
		root.setFillWidth(true);
		Scene scene = new Scene(root, 1024, 640);
		scene.getStylesheets().addAll(STYLESHEETS);
		
		MenuBar menuBar = new ClientMenuBar(primaryStage, scene);
		Parent page = FXMLLoader.load(getClass().getResource("views/Client.fxml"));
		VBox.setVgrow(page, Priority.ALWAYS);
		root.getChildren().addAll(menuBar, page);
		
		primaryStage.setTitle("Java Email Client");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
