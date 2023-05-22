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
	final private String usersFile;
	
	private UserService() {
		if (System.getProperty("user.dir").endsWith("bin")) {
			usersFile = "Users\\Users.csv";
		} else {
			usersFile = "bin\\Users\\Users.csv";
		}
		users = readUsers();
	}
	
	public void welcome() {
		System.out.printf("%n%n** Welcome to JOBS-R-US **%n");
		
		String choice = "";
		do {
			System.out.printf("%nWould you like to?%n" 
							+ "1. Browse jobs%n" 
							+ "2. Log in%n"
							+ "9. Exit"
							+ "%nEnter choice: ");
			choice = in.nextLine().trim().replaceAll("[\\D]", "");
		} while (!"1".equals(choice) && !"2".equals(choice) && !"9".equals(choice));

		if ("1".equals(choice)) {
			goBrowse();
		} else if ("2".equals(choice)) {
			logIn();
		} else if ("9".equals(choice)) {
			System.exit(0);
		}
		
		welcome();
	}
	
	private void logIn() {
		System.out.printf("%n** User Login **");

		String choice = "";
		do {
			System.out.printf("%n1. Log in"
							+ "%n2. Create account"
							+ "%n9. Go back to start menu"
							+ "%nEnter choice: ");
			choice = in.nextLine().trim().replaceAll("[\\D]", "");
		} while (!"1".equals(choice) && !"2".equals(choice) && !"9".equals(choice));

		if ("1".equals(choice)) {
			existingUser();
		} else if ("2".equals(choice)){
			newUser();
		} else {
			return;
		}
	}
	
	private void newUser() {
		System.out.printf("%n** New user account **%n");

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
					System.out.printf("%nReturning to start menu...%n%n");
					return;
				}
			}
			
			System.out.printf("%nYour username will be: '%s'. Is this corret? (Y/N): ", email);
			choice2 = in.nextLine().trim().replaceAll("[^a-zA-Z]", "");
		} while (!"y".equalsIgnoreCase(choice2));

		createUser(email);
	}
	
	private void createUser(String email) {
		String id = String.valueOf(users.size());
		System.out.printf("%nPassword must be a minimum of 4 characters (leading or trailing white space is removed).%n"
						+ "Enter password: ");
		String password = createPassword(id);
		
		System.out.printf("%nEnter first name: ");
		String firstName = in.nextLine().trim();
		
		System.out.printf("%nEnter last name: ");
		String lastName = in.nextLine().trim();
		
		System.out.printf("%nEnter phone number: ");
		String phone = in.nextLine().trim().replaceAll("[\\D]", "");		
		
		currentUser = new User(id, password, firstName, lastName, email, phone);
		
		addUser(currentUser);
		
		System.out.printf("%nThank you for choosing JOBS-R-Us, %s!%n"
						+ "What would you like to do now?%n", firstName);
		userOptions();
	}

	private void existingUser() {
		System.out.printf("%n** Welcome back! **%n");

		String email = "";
		String password = "";

		System.out.printf("%nEmail: ");
		email = in.nextLine().trim().toLowerCase().replaceAll("[^0-9_@.a-zA-Z]", "");

		if (checkUserExists(email)) {
			System.out.printf("%nPassword: ");
			password = readPassword();
			
			User user = users.get(email);
			if (checkPassword(user, password)) {
				currentUser = user;
				System.out.printf("%nHello, %s! Good to see you again!%n", currentUser.getFirstName());
				userOptions();
			} else {
				System.out.printf("%nInvalid password. Please try again.%n%n");
			}
		} else {
			System.out.printf("%nAccount not found. Please try again.%n%n");
		}
	}
	
	private boolean checkUserExists(String email) {
		return users.containsKey(email);
	}
	
	private void userOptions() {
		if (currentUser == null) {
			return;
		} else {
			System.out.printf("%n%s's User Panel: %n", currentUser.getFirstName());
			String choice = "";
			do {
				System.out.printf("%n1. Browse job listings" + "%n2. Edit my profile" + "%n3. View or edit my resume"
						+ "%n4. Change password" + "%n9. Log out and return to start menu" + "%nEnter choice: ");
				choice = in.nextLine().trim().replaceAll("[\\D]", "");
			} while (!"1".equals(choice) && !"2".equals(choice) && !"3".equals(choice) && !"4".equals(choice) && !"9".equals(choice));

			ResumeHandler rH = new ResumeHandler();

			if ("1".equals(choice)) {
				goBrowse();
			} else if ("2".equals(choice)) {
				editProfile();
			} else if ("3".equals(choice)) {
				rH.resumeOptions(currentUser.getResume());
			} else if ("4".equals(choice)) {
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
			System.out.printf("%nPassword must be a minimum of 4 characters, leading or trailing white space is removed.%n"
					+ "Enter password: ");
			password = createPassword(currentUser.getId());
			
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
			
			System.out.printf("%n** %s's Profile **", currentUser.getFirstName());			
			displayUserInfo(currentUser);
			System.out.printf("** **%n");
			
			String choice = "";
			do {
				System.out.printf("%n1. Update name" 
								+ "%n2. Update email" 
								+ "%n3. Update phone number"
								+ "%n9. Previous menu" 
								+ "%nEnter choice: ");
				choice = in.nextLine().trim().replaceAll("[\\D]", "");
			} while (!"1".equals(choice) && !"2".equals(choice) && !"3".equals(choice) && !"9".equals(choice));
			
			if ("1".equals(choice)) {
				updateName();
			} else if ("2".equals(choice)) {
				updateEmail();
			} else if ("3".equals(choice)) {
				updatePhone();
			} else {
				return;
			}
	}
	
	private void displayUserInfo(User u) {
		System.out.printf("%nName: %s%nEmail: %s%nPhone number: %s%n", (u.getFirstName() + " " + u.getLastName()),
				u.getEmail(), u.getPhoneNumber());
	}

	private void updateName() {
		System.out.printf("%nEnter first name: ");
		String firstName = in.nextLine().trim();

		System.out.printf("%nEnter last name: ");
		String lastName = in.nextLine().trim();

		currentUser.setFirstName(firstName);
		currentUser.setLastName(lastName);
		updateUsersMap(currentUser);
		System.out.printf("%nName updated successfully, %s.%n", (firstName + " " + lastName));
	}

	private void updateEmail() {
		String newEmail = "";
		String choice1 = "";
		String choice2 = "";

		do {
			System.out.printf("%nEnter new email (this will be your new username): ");
			newEmail = in.nextLine().trim().toLowerCase().replaceAll("[^0-9_@.a-zA-Z]", "");
			if (newEmail.length() < 5 || !newEmail.contains("@") || !newEmail.contains(".")) {
				System.out.printf("Invalid email. Try again.");
				continue;
			} else if (checkUserExists(newEmail)) {
				System.out.printf(
						"%n**Error**: email is already associated with an existing account.%n" + "Try again? (Y/N): ");
				choice1 = in.nextLine().trim().replaceAll("[^a-zA-Z]", "");
				if ("y".equalsIgnoreCase(choice1)) {
					continue;
				} else {
					System.out.printf("%nReturning to previuos menu...%n%n");
					return;
				}
			}

			System.out.printf("%nYour new username will be: '%s'. Is this corret? (Y/N): ", newEmail);
			choice2 = in.nextLine().trim().replaceAll("[^a-zA-Z]", "");
		} while (!"y".equalsIgnoreCase(choice2));

		updateMapKey(currentUser, newEmail);
		System.out.printf("%nEmail updated successfully: %s.%n", newEmail);
	}

	private void updateMapKey(User u, String newEmail) {
		users.remove(currentUser.getEmail());
		currentUser.setEmail(newEmail);
		users.put(newEmail, currentUser);
	}

	private void updatePhone() {
		System.out.printf("%nEnter new phone number: ");
		String phone = in.nextLine().trim().replaceAll("[\\D]", "");

		currentUser.setPhoneNumber(phone);
		updateUsersMap(currentUser);
		System.out.printf("%nPhone number updated successfully: %s.%n", phone);
	}

	private boolean checkPassword(User user, String password) {
		
		return SecGenerator.getInstance().validatePW(user.getId(), user.getPassword(), password);
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
		HashMap<String, User> users = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(usersFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty()) {
					String[] userData = line.split("!!!"); // split line into array
					String email = userData[4];
					users.put(email, new User(userData)); // adds user to map
				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return users;
	}

	// this method updates the file with map's values
	private void updateUsers(User u, boolean flag) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(usersFile, flag))) {
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
			updateUsers(entry.getValue(), flag); // pass user info to append to file
			flag = true; // changes to append instead of overwriting file
		}
	}

	// this method adds a user to the map and file
	private void addUser(User u) {
		if (users.isEmpty()) {
			updateUsers(u, false);
		} else {
			updateUsers(u, true);
		}
		
		users.put(u.getEmail(), u);
	}

	private void updateUsersMap(User u) {
		users.replace(u.getEmail(), u);
		updateFileLoop();
	}

	private String createPassword(String id) {
		String password = readPassword();
		
		while (password.length() < 4) {
			System.out.printf("%nPassword must be at least 4 characters (leading or trailing white space is removed).%n"
							+ "Try again. Enter password: ");
			password = readPassword();
		}

		System.out.printf("%nConfirm password: ");

		String confirmPassword = readPassword();

		while (!password.equals(confirmPassword)) {
			System.out.printf("%nPasswords do not match. Try again.");
			System.out.printf("%nEnter password: ");
			password = readPassword();
			System.out.printf("%nConfirm password: ");
			confirmPassword = readPassword();
		}
		
		return SecGenerator.getInstance().makePassword(id, password);
	}

	private String readPassword() {
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
