package client.mappers;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.MessagingException;

import client.Main;
import client.models.Folder;
import client.models.Message;

public class FolderMapper {

	/**
	 * Java Mail Folder -> Client Folder
	 */
	public static Folder map(javax.mail.Folder folder) throws MessagingException, IOException {
		
		// Check if folder is closed
		if (!folder.isOpen()) {
			folder.open(javax.mail.Folder.READ_ONLY);
		}
		
		Folder fldr = new Folder(folder.getName());
		
		int messageCount = folder.getMessageCount();
		int loadMessageCount = Math.min(messageCount, Main.MAX_MESSAGE_COUNT);
		for (int i = 1; i <= loadMessageCount; i++) {
			javax.mail.Message message = folder.getMessage(messageCount-i);
			fldr.addMessage(MessageMapper.map(message));
		}

		for (javax.mail.Folder subfolder : folder.list()) {
			fldr.addFolder(map(subfolder));
		}
		
		folder.close();
		return fldr;
	}
	
	public static void mapToExisting(javax.mail.Folder folder, Folder currentFolder, boolean isRoot) throws MessagingException, IOException {
		
		// Check if folder is closed
		if (!folder.isOpen() && !isRoot) {
			folder.open(javax.mail.Folder.READ_ONLY);
		}
		
		Set<String> currentFolderNames = new HashSet<String>();
		
		for (Folder subfolder : currentFolder.getFolders()) {
			currentFolderNames.add(subfolder.getName());
		}
		
		for (javax.mail.Folder subfolder : folder.list()) {
			if (currentFolderNames.contains(subfolder.getName())) {
				currentFolderNames.remove(subfolder.getName());
				try {
					mapToExisting(subfolder, currentFolder.getFolderFromName(subfolder.getName()), false);
				} catch (IllegalArgumentException e) {
					Main.LOGGER.log(Level.WARNING, "Missmatching folders", e);
				}
			} else {
				currentFolder.addFolder(map(subfolder));
			}
		}
		
		for (String folderName : currentFolderNames) {
			try {
				currentFolder.removeFolder(currentFolder.getFolderFromName(folderName));
			} catch (IllegalArgumentException e) {
				Main.LOGGER.log(Level.WARNING, "Missmatching folders", e);
			}
		}
		
		if (!isRoot) {
			int clientMessageCount = currentFolder.getMessages().size();
			Set<Long> currentMessageTimes = new HashSet<Long>(clientMessageCount);
			Map<Long, Message> messageMap = new HashMap<Long, Message>(clientMessageCount);
			
			for (Message message : currentFolder.getMessages()) {
				currentMessageTimes.add(message.getDate().getTime());
				messageMap.put(message.getDate().getTime(), message);
			}
			long messageDate;
			Message m;
			Flags f;
			int messageCount = folder.getMessageCount();
			int loadMessageCount = Math.min(messageCount, Main.MAX_MESSAGE_COUNT);
			for (int i = 0; i < loadMessageCount; i++) {
				javax.mail.Message message = folder.getMessage(messageCount-i);
				messageDate = message.getSentDate().getTime();
				if (currentMessageTimes.contains(messageDate)) {
					//Update flags
					f = message.getFlags();
					m = messageMap.get(messageDate);
					m.setAnswered(f.contains(Flag.ANSWERED));
					m.setDeleted(f.contains(Flag.DELETED));
					m.setDraft(f.contains(Flag.DRAFT));
					m.setFlagged(f.contains(Flag.FLAGGED));
					m.setSeen(f.contains(Flag.SEEN));
					messageMap.remove(messageDate);
				} else {
					currentFolder.addMessage(MessageMapper.map(message));
				}	
			}
			
			for (Message message : messageMap.values()) {
				currentFolder.removeMessage(message);
			}
		}
		
		
		
		if (!isRoot) folder.close();
	}
	
}
