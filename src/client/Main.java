package client;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import client.models.Client;
import client.views.components.ClientMenuBar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	public static final Client CLIENT = new Client();
	public static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static final String[] STYLESHEETS = { "client/assets/styles/theme.css", "client/assets/styles/client.css" };

	public static void main(String[] args) {

		/*
		 * Logger setup
		 */

		// Configuration
		LogManager.getLogManager().reset();
		LOGGER.setLevel(Level.ALL); // Process all log entries

		ConsoleHandler ch = new ConsoleHandler();
		ch.setLevel(Level.ALL); // Show all log entries in console
		LOGGER.addHandler(ch);

		// File logger
		try {
			FileHandler fh = new FileHandler("client.log");
			fh.setLevel(Level.ALL); // Show fine or worse log entries in file
			fh.setFormatter(new SimpleFormatter());
			LOGGER.addHandler(fh);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Failed to create file handler for logger.", e);
		}

		
		/*
		 * Launch application
		 */
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {

		/*
		 * Scene setup
		 */

		// Root node
		VBox root = new VBox();
		root.setFillWidth(true);

		// Scene
		Scene scene = new Scene(root, 1024, 640);
		scene.getStylesheets().addAll(STYLESHEETS);

		// Menu bar
		MenuBar menuBar = new ClientMenuBar(primaryStage, scene);

		// Load page
		VBox page = null;
		try {
			page = FXMLLoader.load(getClass().getResource("views/Client.fxml"));
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Error initializing Client.fxml", e);
			System.exit(1);
		}
		VBox.setVgrow(page, Priority.ALWAYS);

		// Add children to root
		root.getChildren().addAll(menuBar, page);

		
		/*
		 * Stage setup
		 */
		primaryStage.setTitle("Java Email Client");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		primaryStage.setOnHiding(e -> {
			e.consume();
			// TODO Finish syncing and caching!
			Main.LOGGER.log(Level.WARNING, "The program does not yet check if syncing or caching is finished when the program exits!");
			System.exit(0);
		});
		
	}

}
