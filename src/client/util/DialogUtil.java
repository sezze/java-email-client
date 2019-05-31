package client.util;

import java.util.Optional;

import client.views.components.Icon;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DialogUtil {

	public static boolean confirm(String question) {
		Alert alert = new Alert(AlertType.CONFIRMATION, question, ButtonType.YES, ButtonType.NO);
		alert.setTitle("Java Email Client - " + question);
		alert.setGraphic(new Icon("alert", 48));
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("client/assets/icons/logo.png"));
		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == ButtonType.YES;
	}
	
	public static void showWarning(String message) {
		Alert alert = new Alert(AlertType.INFORMATION, message);
		alert.setTitle("Java Email Client - Warning");
		alert.setGraphic(new Icon("alert", 48));
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("client/assets/icons/logo.png"));
		alert.show();
	}
	
}
