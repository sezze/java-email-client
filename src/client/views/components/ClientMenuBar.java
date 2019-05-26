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
	public ClientMenuBar(Stage stage, Scene scene) {

		/*
		 * File ---------------------------------------------------------------
		 */

		Menu file = new Menu("File");

		// Quit (ALT + F4)
		MenuItem quit = new MenuItem("Quit");
		quit.setAccelerator(new KeyCodeCombination(KeyCode.F4, KeyCombination.ALT_DOWN));
		quit.setOnAction(e -> {
			stage.close();
		});

		file.getItems().addAll(quit);
		
		/*
		 * Debug --------------------------------------------------------------
		 */

		Menu debug = new Menu("Debug");
		
		// Reload stylesheet
		MenuItem reloadStyle = new MenuItem("Reload stylesheet");
		reloadStyle.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
		reloadStyle.setOnAction(e -> {
			scene.getStylesheets().clear();
			scene.getStylesheets().addAll(Main.STYLESHEETS);
		});
		
		// Settings page
		MenuItem settingsPage = new MenuItem("Settings page");
		settingsPage.setOnAction(e -> {
			//model.setActivePage(model.getSettingsPage());
		});
		
		// Main page
		MenuItem mainPage = new MenuItem("Main page");
		mainPage.setOnAction(e -> {
			//model.setActivePage(model.getMainPage());
		});

		debug.getItems().addAll(reloadStyle, settingsPage, mainPage);

		/*
		 * Add menus -----------------------------------------------------------
		 */

		getMenus().addAll(file, debug);

	}
}
