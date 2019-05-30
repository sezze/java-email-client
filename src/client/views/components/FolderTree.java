package client.views.components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import client.models.Folder;
import javafx.application.Platform;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FolderTree extends TreeView<Folder> {
	
	Folder folder;
	PropertyChangeListener folderListener;
	
	public FolderTree(Folder folder) {
		this();
		setFolder(folder);
	}
	
	public FolderTree() {
		// Scale to parent
		maxWidth(USE_PREF_SIZE);
		VBox.setVgrow(this, Priority.ALWAYS);
		
		// Listen for changes in folder
		folderListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// Only update if structure or name change (Not new messages, not visible in tree)
				if (evt.getPropertyName() == Folder.FOLDERS || evt.getPropertyName() == Folder.NAME) {
					Platform.runLater(() -> updateTree());
				}
			}
		};
	}

	private void updateTree() {
		TreeItem<Folder> rootItem = new TreeItem<Folder>(folder);
		setShowRoot(false);
		updateTree(rootItem, folder);
		setRoot(rootItem);
	}
	
	private void updateTree(TreeItem<Folder> parent, Folder parentFolder) {
		parent.setExpanded(true);
		// Recursivly go through child folders and add to tree 
		for (Folder folder : parentFolder.getFolders()) {
			TreeItem<Folder> item = new TreeItem<Folder>(folder);
			parent.getChildren().add(item);
			updateTree(item, folder);
		}
	}
	
	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		if (this.folder != null) {
			// If currently listening to a previous folder
			folder.removeChangeListener(folderListener);
		}
		
		// Set folder and update tree
		this.folder = folder;
		updateTree();
		
		// Listen for folder changes
		folder.addChangeListener(folderListener);
	}
	
}
