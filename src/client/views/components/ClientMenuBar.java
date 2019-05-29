package client.views.components;


import client.Main;
import client.models.Account;
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
		 * File menu
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
		
		// Add account
		MenuItem addAccount = new MenuItem("Add account");
		addAccount.setOnAction(e -> {
			Main.CLIENT.addAccount(new Account("Herbert Love", "herbert@yahoo.com", "herbert@yahoo.com", "herbertthesherbert59", "smtp.yahoo.com", "imap.yahoo.com"));
		});
		
		// Settings page
		MenuItem settingsPage = new MenuItem("Settings page");
		settingsPage.setOnAction(e -> {
			// model.setActivePage(model.getSettingsPage());
		});
		
		// Main page
		MenuItem mainPage = new MenuItem("Main page");
		mainPage.setOnAction(e -> {
			// model.setActivePage(model.getMainPage());
		});

		debug.getItems().addAll(reloadStyle, addAccount, settingsPage, mainPage);

		/*
		 * Add menus
		 */
		getMenus().addAll(file, debug);

	}
}
