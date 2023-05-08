package com.skillstorm.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.skillstorm.beans.User;

public class UserService {
	
	private static UserService uService = null;
	
	static Scanner in = new Scanner(System.in);
	
	static String regularExpression = "^[a-zA-Z]([_]|[a-zA-Z0-9]){3,40}";

	private HashMap<String, User> users;
	private User currentUser;
	final private String usersFile = "src\\Users\\Users.csv";
	
	public UserService () {
		users = readUsers();
	}
	
	public void welcome() {
		System.out.printf("**JOBS R US WELCOME PROMPT%n");
		
		int choice = 0;
		do {
			System.out.printf("Do you want to:%n" 
							+ "1. Browse jobs%n" 
							+ "2. Log in%n");
			choice = Integer.parseInt(in.nextLine().trim().replaceAll("[\\D]", "")); // removes all non-digit characters
		} while (choice != 1 && choice != 2);

		if (choice == 1) {
			goBrowse();
		} else {
			logIn();
		}
	}
	
	public void logIn() {
		System.out.printf("**LOG IN PROMPT%n");

		int choice = 0;
		do {
			System.out.printf("Do you want to:%n" 
							+ "1. Log in%n"
							+ "2. Create account%n");
			choice = Integer.parseInt(in.nextLine().trim().replaceAll("[\\D]", "")); // removes all non-digit characters
		} while (choice != 1 && choice != 2);

		if (choice == 1) {
			existingUser();
		} else {
			createUser();
		}
	}
	
	public void createUser() {		
		System.out.printf("**CREATE USER PROMPT%n");		
		
		String email = "";
		String choice = "";
		
		do {
			System.out.printf("%nEnter email (this will be your username): ");
			email = in.nextLine().trim().toLowerCase().replaceAll("[^0-9_@.a-zA-Z]", "");
			System.out.printf("%nYou entered '%s'. Is this corret? (Y/N): ");
			choice = in.nextLine().trim().replaceAll("[^a-zA-Z]", "");
		} while (!"y".equalsIgnoreCase(choice));
		
//		boolean flag = checkUserExists(email);
		while (checkUserExists(email)) {
			System.out.printf("%n**Error**: email is already associated with an existing account.%n" 
							+ "Try again? (Y/N): ");
			choice = in.nextLine().trim().replaceAll("[^a-zA-Z]", "");
			if ("n".equalsIgnoreCase(choice)) {
				break;
			}
			email = in.nextLine().trim().toLowerCase();
//			System.out.printf();
		}
		if ("n".equalsIgnoreCase(choice)) {
			System.out.printf("%nReturning to start menu...%n%n");
			welcome();
		}
		
		System.out.printf("%nEnter password: ");
		String password = in.nextLine().trim();
		
		System.out.printf("%nEnter first name: ");
		String firstName = in.nextLine().trim();
		
		System.out.printf("%nEnter last name: ");
		String lastName = in.nextLine().trim();
		
		String name = firstName + " " + lastName;
		
		System.out.printf("%nEnter phone number: ");
		String phone = in.nextLine().trim().replaceAll("[\\D]", "");
		
		String id = String.valueOf(users.size());
		String resume = "";
		
		User user =  new User(id, password, name, email, phone, resume);
		addUser(user);
		
		currentUser = user;
		
		System.out.printf("%nWELCOME %s! What would you like to do now?%n", firstName);
		userOptions();
	}

	public void existingUser() {
		
	}
	
	public boolean checkUserExists(String email) {
		return users.containsKey(email);
	}
	
	public void userOptions() {
		System.out.printf("1. Browse Jobs%"
						+ "2. Edit profile%n"
						+ "3. Change password%n"
						+ "4. Log out%n");
		System.out.printf("**METHOD NOT FULLY IMPLEMENTED");
		int choice = 0;
		do {
			System.out.printf("1. Browse Jobs%"
							+ "2. Edit profile%n"
							+ "3. Change password%n"
							+ "4. Log out%n");
			choice = Integer.parseInt(in.nextLine().trim().replaceAll("[\\D]", "")); // removes all non-digit characters
		} while (choice != 1 && choice != 2 && choice != 3 && choice != 4);

		if (choice == 1) {
			goBrowse();
		} else if (choice == 2){
//			editProfile()?
		} else if (choice == 3){
//			changePassword()?
		} else {
//			logOut()?
		}
	}
		
	public void goBrowse() {
//		ListingService lS = ListingService.getInstance();
//        lS.browseJobs();
	}
	
	public HashMap<String, User> readUsers() {
		HashMap<String, User> users = new HashMap<>(); // create new users map
		try (BufferedReader br = new BufferedReader(new FileReader(usersFile))) { // read in the file
			String line;
			while ((line = br.readLine()) != null) { // while there is a line in file
				if (!line.isEmpty()) { // checks if line has any values
					String[] userData = line.split(","); // split line into array
					String email = userData[3];
					users.put(email, new User(userData)); // adds user to map with it's id and title being the key
				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return users; // returns users map
	}

	// this method updates the file with map's values
	public void updateUsersList(User u, boolean flag) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(usersFile, flag))) { // try to open file to write to
			String userData = u.toString().replace("!!!", ","); // get user data in desired format to append to file
			bw.append(userData); // append user to file
			bw.newLine();
		} catch (IOException e) {
			System.out.println("Exception caught appending users file");
		}
	}

	// this method used to loop through job map and update the file
	private void updateLoop() {
		boolean flag = false; // set to false for first iteration, clears file
		for (Map.Entry<String, User> entry : users.entrySet()) { // loop through values in map
			updateUsersList(entry.getValue(), flag); // pass job value to append to file
			flag = true; // changes to append instead of overwriting file
		}
	}

	// this method removes a listing from map and file
//	public void removeUser(User u) {
//		if (users.containsKey(u.getEmail())) { // check if job map contains this job
//			users.remove(u.getEmail()); // if so remove it
//		}
//		updateLoop(); // update file
//	}

	// this method adds a user to the map and file
	public void addUser(User u) {
		if (users.isEmpty()) { // checks if users map is empty
			updateUsersList(u, false); // if so add the user to file
			users.put(u.getEmail(), u); // add user to users map
		} else {
			users.put(u.getEmail(), u); // job was not in users map so add it
			updateUsersList(u, true);
		}
	}
	
	public void updateUser(User u) {
			users.replace(u.getEmail(), u);
			updateLoop();
	}
	
	public static UserService getInstance() {
		if (uService == null) {
			uService = new UserService();
		}
		return uService;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, User> getUsers() {
		return (HashMap<String, User>) users.clone();
	}

	public User getCurrentUser() {
		return currentUser;
	}
}
