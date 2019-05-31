package client.views.components;

import client.Main;
import client.controllers.SyncController;
import client.models.Client;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;

public class SyncingNotifier extends HBox{
	
	// Child components
	ProgressBar syncingProgress;
	Label statusLabel;
	
	public SyncingNotifier() {
		
		setSpacing(8);
		setPadding(new Insets(2));
		setAlignment(Pos.CENTER_RIGHT);
		
		syncingProgress = new ProgressBar();
		syncingProgress.setProgress(-1);
		statusLabel = new Label();
		
		getChildren().addAll(statusLabel, syncingProgress);
		
		update();
		
		Main.CLIENT.addChangeListener(e -> {
			if (e.getPropertyName() == Client.SYNC) {
				Platform.runLater(() -> update());
			}
		});
		
	}

	private void update() {
		if (SyncController.getTotalConcurrentSyncing() > 0) {
			statusLabel.setText("Syncing " + SyncController.getTotalConcurrentSyncing() + " accounts.");
			syncingProgress.setVisible(true);
			syncingProgress.setManaged(true);
		} else {
			statusLabel.setText("Finished syncing.");
			syncingProgress.setVisible(false);
			syncingProgress.setManaged(false);
		}
		
	}
	
}
