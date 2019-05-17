package client;

import client.controllers.ClientController;
import client.models.ClientModel;
import client.views.ClientView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Client extends Application {

	// Models
	private ClientModel model;

	// Views
	private ClientView view;

	// Controllers
	private ClientController controller;

	/**
	 * Main method, launches the application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Models
		model = new ClientModel();

		// Views
		view = new ClientView(model, primaryStage);

		// Controllers
		controller = new ClientController();
	}

}
