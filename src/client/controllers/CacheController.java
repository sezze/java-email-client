package client.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;

import client.Main;
import client.models.Message;

public class CacheController {

	private static void cacheObject(Object object, String category, String filename) {
		try {
			String folder = "cache/" + category;
			new File(folder).mkdirs();
			FileOutputStream fileOut = new FileOutputStream(folder + "/" + filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(object);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			Main.LOGGER.log(Level.SEVERE, "Failed to cache " + category, e);
		}
	}
	
	public static void cacheMessage(Message message) {
		cacheObject(message, "messages", Long.toString(message.getDate().getTime()));
	}
	
}
