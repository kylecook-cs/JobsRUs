package com.skillstorm.services;

import java.io.File;
import java.util.Scanner;

import com.skillstorm.beans.User;

public class UserService {
	
	private File users;	
	
	public User welcome(Scanner in) {
		System.out.println("Welcome to the HomeBuyer App!!!\n");
		
		boolean cont = false; // just a flag
		int choice; // need this scoped outside of my while loop so I can use it later
		do {
			System.out.println("Are you: ");
			System.out.println("1. A new user?");
			System.out.println("2. A returning user?");
			choice = Integer.parseInt(in.nextLine().trim());
			
			// make sure my continue flag is properly set
			if (choice < 1 || choice > 2) {
				System.out.println("Please enter a 1 or a 2");
				cont = true;
			} else {
				cont = false;
			}
		} while(cont);
		
		// select the new user or existing user based on their choice
		if (choice == 1) {
			// new user
			return newUser(in); // objects can be null
		} else {
			// returning user			
			return existingUser(in);
		}				
	}
	
	public User newUser(Scanner in) {
		System.out.println("I have just a few questions to ask you");
		System.out.print("What is your name?: ");
		String name = in.nextLine().trim();

		System.out.print("What is your email?: ");
		String email = in.nextLine().trim();
		
		System.out.print("What is your phone number?: ");
		String phoneNumber = in.nextLine().trim();
		System.out.println("Processing....");
		
		// I need to hand a buyer a name, email, and phoneNumber
		// so I need to ask my user for those things
		User user = null;
		System.out.println("Welcome to our service, " + user.getName() + "!");
		
		return user;
	}
	
	public User existingUser(Scanner in) {
		System.out.println("ok... ");
		
		boolean cont = false;
		User user = null;
		do {
			System.out.println("Who are you?");
			for (int i = 0; i < numUsers; i++) {
				System.out.println(i + ". " + users[i]);
			}
			int choice = Integer.parseInt(in.nextLine().trim());
			
			if (choice < 0 || choice > numUsers - 1) {
				System.out.println("Please select a user");
				cont = true;
			} else {
				user = users[choice];
				System.out.println("Welcome back " + user.getName());
				cont = false;
			}			
		} while(cont);
				
		return user;
	}

	public File getUsers() {
		return users;
	}
}
