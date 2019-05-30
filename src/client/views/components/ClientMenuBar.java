package client.views.components;


import client.Main;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class ClientMenuBar extends MenuBar {
	public ClientMenuBar(Stage stage, Scene scene, Main main) {

		/*
		 * File menu
		 */
		Menu file = new Menu("File");
		
		// Open settings
		MenuItem settings = new MenuItem("Settings");
		settings.setOnAction(e -> main.openSettings());

		// Quit (ALT + F4)
		MenuItem quit = new MenuItem("Quit");
		quit.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));
		quit.setOnAction(e -> stage.close());

		file.getItems().addAll(settings, quit);
		
		
		/*
		 * Debug menu
		 */
		Menu debug = new Menu("Debug");
		
		// Reload stylesheet
		MenuItem reloadStyle = new MenuItem("Reload stylesheet");
		reloadStyle.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
		reloadStyle.setOnAction(e -> {
			scene.getStylesheets().clear();
			scene.getStylesheets().addAll(Main.STYLESHEETS);
		});

		debug.getItems().addAll(reloadStyle);

		
		/*
		 * Add menus
		 */
		getMenus().addAll(file, debug);

	}
}
