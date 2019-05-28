package client.views.components;

import java.util.Observable;
import java.util.Observer;

import client.models.Folder;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FolderTree extends TreeView<Folder> implements Observer {
	
	Folder folder;
	
	public FolderTree(Folder folder) {
		this();
		setFolder(folder);
	}
	
	public FolderTree() {
		maxWidth(USE_PREF_SIZE);
		VBox.setVgrow(this, Priority.ALWAYS);
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
			folder.deleteObserver(this);
		}
		this.folder = folder;
		updateTree();
		folder.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {
				
	}
	
}
