package com.skillstorm.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;

public final class SecGenerator {
	
private static SecGenerator gen = null;
		
	private HashMap<String, String> salts;
	private final String saltsFile;
		
	private SecGenerator() {
		if (System.getProperty("user.dir").endsWith("bin")) {
			saltsFile = "com\\skillstorm\\dat";
		} else {
			saltsFile = "bin\\com\\skillstorm\\dat";
		}
		salts = readSalts();
	}
	
	public boolean validatePW(String id, String hash, String password) {
		if (containsId(id)) {
			String salt = salts.get(id);
			
			return hash.equals(hashPassword(password, salt));
		} else {
			return false;	
		}
	}
	
	private boolean containsId(String id) {
		return salts.containsKey(id);
	}
	
	private String hashPassword(String pw, String salt) {
		String hashed = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(salt.getBytes(StandardCharsets.UTF_8));
			byte[] bytes = md.digest(pw.getBytes(StandardCharsets.UTF_8));
			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
			}
            hashed = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("MD Exception");
		}
		
		return hashed;
	}
	
	private String makeSalt(String id) {
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[16];
		random.nextBytes(bytes);
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		}
		String salt = sb.toString(); 
		addSalt(id, salt);
		return salt;
	}
	
	public String makePassword(String id, String pw) {
		return hashPassword(pw, makeSalt(id));
	}
	
	private HashMap<String, String> readSalts() {
		HashMap<String, String> salts = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(saltsFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty()) {
					String[] data = line.split("l"); 
					salts.put(data[1], data[0]);
				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return salts;
	}
	
	private void updateFile(String id, String s, boolean flag) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(saltsFile, flag))) {
			bw.append(s + "l" + id);
			bw.newLine();
		} catch (IOException e) {
			System.out.println("Exception caught appending dat file");
		}
	}
		
	private void addSalt(String id, String salt) {
		if (salts.isEmpty()) {
			updateFile(id, salt, false);
		} else {
			updateFile(id, salt, true);
		}
		
		salts.put(id, salt);
	}
	
	public static SecGenerator getInstance() {
		if (gen == null) {
			gen = new SecGenerator();
		}
		return gen;
	}
}
