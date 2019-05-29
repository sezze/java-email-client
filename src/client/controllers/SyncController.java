package client.controllers;

import client.models.Account;
import client.models.Folder;

public class SyncController {
	
	/* 
	 * TASKS:
	 * Download new messages, download folder and message changes, message flags, (local folder changes), (send messages)
	 */
	
	private Account account;

	public SyncController(Account account) {
		this.account = account;
		
		account.getRootFolder().addChangeListener(e -> {
			Folder folder = (Folder) e.getSource();
			switch (e.getPropertyName()) {
			case Folder.FOLDERS:
				break;
			case Folder.MESSAGES:
				
				break;
			case Folder.NAME:
				break;			
			}
		});
	}
	
	public void syncFromServer() {
		//Folder rootFolder = account.getStore().getDefaultFolder();
	}
	
	
	
}
