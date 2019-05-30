package client.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import client.Main;
import client.models.Account;

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
	
	
	private static Object loadObject(String category, String filename) throws ClassNotFoundException, IOException {
		String folder = "cache/" + category;
		return loadObject(new File(folder + "/" + filename));
	}
	
	private static Object loadObject(File file) throws ClassNotFoundException, IOException {
		FileInputStream fileIn = new FileInputStream(file);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		Object o = in.readObject();
		in.close();
		fileIn.close();
		return o;
	}
	
	public static void cacheAccount(Account account) {
		cacheObject(account, "accounts", account.getAddress()+".oos");
	}
	
	public static Account loadAccount(String address) throws ClassNotFoundException, IOException {
		return (Account) loadObject("accounts", address+".oos");
	}

	public static List<Account> loadAllAccounts() {
		File folder = new File("cache/accounts");
		List<Account> accounts = new ArrayList<Account>();
		for (File file : folder.listFiles()) {
			try {
				accounts.add((Account) loadObject(file));
			} catch (ClassNotFoundException | IOException e) {
				Main.LOGGER.log(Level.WARNING, "Failed to load in account " + file.getName(), e);
			}
		}
		return accounts;
	}

}
