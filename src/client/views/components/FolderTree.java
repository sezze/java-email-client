package client.views.components;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import client.models.Folder;
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
		maxWidth(USE_PREF_SIZE);
		VBox.setVgrow(this, Priority.ALWAYS);
		
		folderListener = new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				updateTree();
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
			folder.removeChangeListener(folderListener);
		}
		this.folder = folder;
		updateTree();
		folder.addChangeListener(folderListener);
	}
	
}
