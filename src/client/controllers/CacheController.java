package client.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void cacheMessage(Message message) {
		cacheObject(message, "messages", Long.toString(message.getDate().getTime()));
	}
	
}
