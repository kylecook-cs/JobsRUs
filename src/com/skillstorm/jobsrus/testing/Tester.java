package com.skillstorm.jobsrus.testing;

import java.util.ArrayList;
import java.util.Scanner;

import com.skillstorm.beans.User;
import com.skillstorm.services.UserService;

public class Tester {
	
	static Scanner in = new Scanner(System.in);
	static UserService uServ = new UserService();
	
	public static void main(String[] args) {
		
//		
//		String firstName = "19824u512985j125ikn124(*&^*(^(!*&@^$*!&@$+___kj23o5 5(*#&%$(U@H!@?><@$!{".trim().replaceAll("[^a-zA-Z]", "");
		String firstName = "ndsangui_n-etti2125125@g   mail.com".trim().replaceAll("[^0-9_@.a-zA-Z]", "");
		System.out.println(firstName);
//		
		
//		ArrayList<String> jobsApplied = new ArrayList<>();
//		
//		jobsApplied.add("aJob'sToString!!!SomeOtherValue!!!Other!!!Another");
//		jobsApplied.add("aJob'sToString!!!SomeOtherValue!!!Other!!!Another");
//		
//		
//		
//		System.out.println(jobsApplied);
//		// remove any non-digit
//		String x = "kj12h4kj12h5837512nklfxf*(&%^*&^@$!@2.,mm.13124,1mhxnjklojnjbu sar qwr1on24 oj/*/6sdg5s+--()_-124-124af-asf";
////		
//		String xNumbers = x.trim().replaceAll("[\\D]", "");
////		
//		System.out.println("       x = " + x);
//		System.out.println("xNumbers = " + xNumbers);
//		// end
	}
	
	public static void createUser() {		
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
			choice = "";
			System.out.printf(
					"%n**Error**: email is already associated with an existing account.%n" + "Try again? (Y/N): ");
			choice = in.nextLine().trim().replaceAll("[^a-zA-Z]", "");
			if ("n".equalsIgnoreCase(choice)) {
				break;
			}
			System.out.printf("%nEnter email (this will be your username): ");
			email = in.nextLine().trim().toLowerCase().replaceAll("[^0-9_@.a-zA-Z]", "");
			System.out.printf("%nYou entered '%s'. Is this corret? (Y/N): ");
			choice = in.nextLine().trim().replaceAll("[^a-zA-Z]", "");

			;
			email = in.nextLine().trim().toLowerCase();
//			System.out.printf();
		}
		
		if ("n".equalsIgnoreCase(choice)) {
			System.out.printf("%nReturning to start menu...%n%n");
//			welcome();
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
	
	public boolean checkUserExists(String email) {
		return false;
	}
}
