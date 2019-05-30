package client.views.components;

import client.models.Contact;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ContactPane extends HBox {
	
	private Contact contact;
	
	// Child components
	private Label nameLabel;
	private Label addressLabel;
	
	public ContactPane(Contact contact, boolean nameOnly) {
		getStyleClass().add("contact-pane");
		
		this.contact = contact;
		
		setSpacing(5); // Space between name and address
		
		if (contact.getName() == null) {
			// If only address, no display name
			addressLabel = new Label(contact.getAddress());
			getChildren().add(addressLabel);
		} else if (nameOnly) {
			// If only name is to be shown
			nameLabel = new Label(contact.getName());
			getChildren().add(nameLabel);
		} else {
			// If address and display name
			nameLabel = new Label(contact.getName());
			addressLabel = new Label("<" + contact.getAddress() + ">");
			getChildren().addAll(nameLabel, addressLabel);
		}
		
	}
	
	public Contact getContact() { return contact; }
	
}
