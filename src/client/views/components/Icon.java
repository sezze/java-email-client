package client.views.components;

import javafx.scene.image.ImageView;

public class Icon extends ImageView {

	public Icon(String icon, int size) {
		
		super("client/assets/icons/"+icon+".png");
		setFitHeight(size);
		setFitWidth(size);
		
	}

}
