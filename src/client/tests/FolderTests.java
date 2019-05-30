package client.tests;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import client.models.Folder;

class FolderTests {

	@Test
	void createFolder() {
		Folder folder = new Folder("Test folder");
		assertTrue(folder.getName() == "Test folder");
		assertTrue(folder.getFolders().size() == 0);
		assertTrue(folder.getMessages().size() == 0);
	}

	@Test
	void nestedFolders() {
		Folder folder = new Folder("Test folder");
		folder.addFolder(new Folder("Subfolder"));
		assertTrue(folder.getFolders().size() == 1);
		assertTrue(folder.getFolders().get(0).getName() == "Subfolder");
	}

}
