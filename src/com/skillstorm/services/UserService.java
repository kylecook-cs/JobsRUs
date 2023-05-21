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

public final class UserService {
	
	private static UserService uService = null;
	
	private static Scanner in = new Scanner(System.in);
	
	private HashMap<String, User> users;
	private User currentUser;
	final private String usersFile = "src\\Users\\Users.csv";
		
	private UserService () {
		users = readUsers();
	}
	
	public void welcome() {
		System.out.printf("**JOBS R US WELCOME PROMPT%n");
		
		int choice = 0;
		do {
			System.out.printf("Do you want to:%n" 
							+ "1. Browse jobs%n" 
							+ "2. Log in%n"
							+ "9. Exit"
							+ "%nEnter choice: ");
			choice = Integer.parseInt(in.nextLine().trim().replaceAll("[\\D]", ""));
		} while (choice != 1 && choice != 2 && choice != 9);

		if (choice == 1) {
			goBrowse();
		} else if (choice == 2) {
			logIn();
		} else if (choice == 9) {
			System.exit(0);
		}
		
		welcome();
	}
	
	private void logIn() {
		System.out.printf("**LOG IN PROMPT%n");

		int choice = 0;
		do {
			System.out.printf("%nDo you want to:" 
							+ "%n1. Log in"
							+ "%n2. Create account"
							+ "%n3. Go back to main menu"
							+ "%nEnter choice: ");
			choice = Integer.parseInt(in.nextLine().trim().replaceAll("[\\D]", ""));
		} while (choice != 1 && choice != 2 && choice != 3);

		if (choice == 1) {
			existingUser();
		} else if (choice == 2){
			newUser();
		}		
	}
	
	private void newUser() {
		System.out.printf("**NEW USER PROMPT%n");

		String email = "";
		String choice1 = "";
		String choice2 = "";

		do {
			System.out.printf("%nEnter email (this will be your username): ");
			email = in.nextLine().trim().toLowerCase().replaceAll("[^0-9_@.a-zA-Z]", "");
			if (email.length() < 5 || !email.contains("@") || !email.contains(".")) {
				System.out.printf("Invalid email. Try again.");
				continue;
			} else if (checkUserExists(email)) {
				System.out.printf(
						"%n**Error**: email is already associated with an existing account.%n" 
					  + "Try again? (Y/N): ");
				choice1 = in.nextLine().trim().replaceAll("[^a-zA-Z]", "");
				if ("y".equalsIgnoreCase(choice1)) {
					continue;
				} else {
					System.out.printf("%nReturning to main menu...%n%n");
					return;
				}
			}
			
			System.out.printf("%nYour username will be: '%s'. Is this corret? (Y/N): ", email);
			choice2 = in.nextLine().trim().replaceAll("[^a-zA-Z]", "");
		} while (!"y".equalsIgnoreCase(choice2));

		createUser(email);
	}
	
	private void createUser(String email) {
		System.out.printf("%nEnter password (cannot contain leading or trailing spaces): ");
		String password = readPassword();
		
		System.out.printf("%nEnter first name: ");
		String firstName = in.nextLine().trim();
		
		System.out.printf("%nEnter last name: ");
		String lastName = in.nextLine().trim();
		
		String name = firstName + " " + lastName;
		
		System.out.printf("%nEnter phone number: ");
		String phone = in.nextLine().trim().replaceAll("[\\D]", "");
		
		String id = String.valueOf(users.size());
		String resume = "";
		
		currentUser = new User(id, password, name, email, phone, resume);
		addUser(currentUser);		
		
		System.out.printf("%nWELCOME %s! What would you like to do now?%n", firstName);
		userOptions();
	}

	private void existingUser() {
		System.out.printf("%n**WELCOME BACK");

		String email = "";
		String password = "";

		System.out.printf("%nEnter your email: ");
		email = in.nextLine().trim().toLowerCase().replaceAll("[^0-9_@.a-zA-Z]", "");

		if (checkUserExists(email)) {
			System.out.printf("%nEnter your password: ");
			password = readPassword();
			
			User user = users.get(email);
			if (checkPassword(user, password)) {
				currentUser = user;
				System.out.printf("%nHello, %s! What would you like to do now?%n", currentUser.getName());
				userOptions();
			} else {
				System.out.printf("%nInvalid password. Please try again.%n%n");
			}
		} else {
			System.out.printf("%nNo account found with this email. Please try again.%n%n");
		}
	}
	
	private boolean checkUserExists(String email) {
		return users.containsKey(email);
	}
	
	private void userOptions() {
		if (currentUser == null) {
			return;
		} else {
			int choice = 0;
			do {
				System.out.printf("%n1. Browse Jobs" + "%n2. Edit profile" + "%n3. Create or edit resume"
						+ "%n4. Change password" + "%n9. Log out and return to main menu" + "%nEnter choice: ");
				choice = Integer.parseInt(in.nextLine().trim().replaceAll("[\\D]", ""));
			} while (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 9);

			ResumeHandler rH = new ResumeHandler();

			if (choice == 1) {
				goBrowse();
			} else if (choice == 2) {
				editProfile();
			} else if (choice == 3) {
//				rH.resumeOptions(currentUser.getName());
			} else if (choice == 4) {
				changePassword();
			} else {
				logOut();
			}
		}
		userOptions();
	}
		
	private void changePassword() {
		String password = "";
		System.out.printf("%nEnter your current password: ");
		password = readPassword();
		
		if (checkPassword(currentUser, password)) {
			System.out.printf("%nEnter your new password (cannot contain leading or trailing spaces): ");
			password = readPassword();
			System.out.printf("%nConfirm new password: ");
			String confirmPassword = readPassword();
			
			while (!password.equals(confirmPassword)) {
				System.out.printf("%nPasswords do not match. Try again.");
				System.out.printf("%nEnter your new password: ");
				password = readPassword();
				System.out.printf("%nConfirm the new password: ");
				confirmPassword = readPassword();
			}
			currentUser.setPassword(password);			
			updateUsersMap(currentUser);
			System.out.printf("%nPassword changed successfully.%n");
		} else {
			System.out.printf("%nInvalid password. Please log back in and try again.%n%n");
			currentUser = null;
		}
	}

	private void editProfile() {					
			System.out.printf("%nEnter your password: ");
			String password = readPassword();
			
			if (!checkPassword(currentUser, password)) {
				System.out.printf("%nInvalid password. Please log back in and try again.%n%n");
				currentUser = null;
				return;
			}			
			
			int choice = 0;
			do {
				System.out.printf("%n1. Update name" 
								+ "%n2. Update email" 
								+ "%n3. Update phone number"
								+ "%n9. Previous menu" 
								+ "%nEnter choice: ");
				choice = Integer.parseInt(in.nextLine().trim().replaceAll("[\\D]", ""));
			} while (choice != 1 && choice != 2 && choice != 3 && choice != 9);
			
			if (choice == 1) {
				updateName();
			} else if (choice == 2) {
				updateEmail();
			} else if (choice == 3) {
				updatePhone();
			} else {
				return;
			}
	}
	
	private void updateName() {
		System.out.printf("%nEnter first name: ");
		String firstName = in.nextLine().trim();
		
		System.out.printf("%nEnter last name: ");
		String lastName = in.nextLine().trim();
		
		String name = firstName + " " + lastName;
		
		currentUser.setName(name);
		updateUsersMap(currentUser);
		System.out.printf("%Name updated successfully, .%n", name);
	}
	
	private void updateEmail() {
		String email = "";
		String choice1 = "";
		String choice2 = "";

		do {
			System.out.printf("%nEnter new email (this will be your new username): ");
			email = in.nextLine().trim().toLowerCase().replaceAll("[^0-9_@.a-zA-Z]", "");
			if (email.length() < 5 || !email.contains("@") || !email.contains(".")) {
				System.out.printf("Invalid email. Try again.");
				continue;
			} else if (checkUserExists(email)) {
				System.out.printf(
						"%n**Error**: email is already associated with an existing account.%n" 
					  + "Try again? (Y/N): ");
				choice1 = in.nextLine().trim().replaceAll("[^a-zA-Z]", "");
				if ("y".equalsIgnoreCase(choice1)) {
					continue;
				} else {
					System.out.printf("%nReturning to previuos menu...%n%n");
					return;
				}
			}
			
			System.out.printf("%nYour new username will be: '%s'. Is this corret? (Y/N): ", email);
			choice2 = in.nextLine().trim().replaceAll("[^a-zA-Z]", "");
		} while (!"y".equalsIgnoreCase(choice2));
		
		currentUser.setEmail(email);
		updateUsersMap(currentUser);
		System.out.printf("%Email updated successfully.%n");
	}

	private void updatePhone() {
		System.out.printf("%nEnter new phone number: ");
		String phone = in.nextLine().trim().replaceAll("[\\D]", "");	
		
		currentUser.setPhoneNumber(phone);
		updateUsersMap(currentUser);
		System.out.printf("%Phone number updated successfully.%n");
	}

	private boolean checkPassword(User user, String password) {
		return user.getPassword().equals(password);
	}

	private void logOut() {
		currentUser = null;
		System.out.printf("Logged out successfully%n");
	}

	private void goBrowse() {
		ListingService lS = ListingService.getInstance();
        lS.browseJobs();
	}
	
	private HashMap<String, User> readUsers() {
		HashMap<String, User> users = new HashMap<>(); // create new users map
		try (BufferedReader br = new BufferedReader(new FileReader(usersFile))) { // read in the file
			String line;
			while ((line = br.readLine()) != null) { // while there is a line in file
				if (!line.isEmpty()) { // checks if line has any values
					String[] userData = line.split("!!!"); // split line into array
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
		private void updateUsers(User u, boolean flag) {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(usersFile, flag))) { // try to open file to write to
				String userData = u.toString();
				bw.append(userData); // append user to file
				bw.newLine();
			} catch (IOException e) {
				System.out.println("Exception caught appending users file");
			}
		}

	// this method used to loop through users map and update the file
	private void updateFileLoop() {
		boolean flag = false; // set to false for first iteration, clears file
		for (Map.Entry<String, User> entry : users.entrySet()) { // loop through values in map
			updateUsers(entry.getValue(), flag); // pass job value to append to file
			flag = true; // changes to append instead of overwriting file
		}
	}

	// this method adds a user to the map and file
	private void addUser(User u) {
		if (users.isEmpty()) { // checks if users map is empty
			updateUsers(u, false); // if so add the user to file
		} else {
			updateUsers(u, true);
		}
		
		users.put(u.getEmail(), u); // add user to users map
	}	
	
	private void updateUsersMap(User u) {
			users.replace(u.getEmail(), u);
			updateFileLoop();
	}
	
	private static String readPassword() {
		if (System.console() != null) {
			return String.valueOf(System.console().readPassword()).trim();
		} else {
			return in.nextLine().trim();
		}
	}
	
	public static UserService getInstance() {
		if (uService == null) {
			uService = new UserService();
		}
		return uService;
	}
}
