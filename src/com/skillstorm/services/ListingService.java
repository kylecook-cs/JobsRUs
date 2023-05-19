package com.skillstorm.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.skillstorm.beans.Job;

// This class is a service class to update the job postings
public class ListingService {

	static Scanner in = new Scanner(System.in); // scanner object
	final private String listingFile = "src\\Listings\\Listings.csv"; // file path
	private HashMap<String, Job> jobs; // map that holds all the job listings
	private static ListingService instance = null;
	static int id = 0;

	private ListingService() { // constructor that creates map of all current job postings in file
		this.jobs = readListings();
	}

	public static ListingService getInstance() {
		if (instance == null) {
			instance = new ListingService();
		}
		return instance;
	}

	// this method reads in the listings file's contents and maps them to a hashmap
	public HashMap<String, Job> readListings() {
		HashMap<String, Job> jobs = new HashMap<>(); // create new jobs map
		try (BufferedReader br = new BufferedReader(new FileReader(listingFile))) { // read in the file
			String line;
			while ((line = br.readLine()) != null) { // while there is a line in file
				if (!line.isEmpty()) { // checks if line has any values
					String[] jobPosts = line.split(","); // split line into array
					String id = jobPosts[0];
					String title = jobPosts[1];
					jobs.put(id + " " + title, new Job(jobPosts)); // adds job to map with it's id and title being the
																	// key
				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return jobs; // returns jobs map
	}

	// this method updates the file with map's values
	public void updateListing(Job j, boolean flag) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(listingFile, flag))) { // try to open file to write
																							// to
			String listing = j.toString().replace("!!!", ","); // get job in desired format to append to file
			bw.append(listing); // append job to file
			bw.newLine();
		} catch (IOException e) {
			System.out.println("Exception caught appending listings file");
		}
	}

	// this method used to loop through job map and update the file
	private void updateLoop() {
		boolean flag = false; // set to false for first iteration, clears file
		for (Map.Entry<String, Job> entry : jobs.entrySet()) { // loop through values in map
			updateListing(entry.getValue(), flag); // pass job value to append to file
			flag = true; // changes to append instead of overwriting file
		}
	}

	// this method removes a listing from map and file
	public void removeListing(Job j) {
		if (jobs.containsKey(j.getJobKey())) { // check if job map contains this job
			jobs.remove(j.getJobKey()); // if so remove it
		}
		updateLoop(); // update file
	}

	// this method adds a listing to the map and file
	public void addListing(Job j) {
		if (jobs.isEmpty()) { // checks if job map is empty
			updateListing(j, false); // if so add the job to file
			jobs.put(j.getJobKey(), j); // add job to jobs map
		} else if (jobs.containsKey(j.getJobKey())) { // checks if job key is already in file
			jobs.replace(j.getJobKey(), j); // replaces in case job's properties are updated
			updateLoop();
		} else {
			jobs.put(j.getJobKey(), j); // job was not in job map so add it
			updateListing(j, true);
		}
	}

	// this method allows users to browse the job listings
	public void browseJobs() {
		boolean again = true; // flag for user's loop prompt
		int option = 0; // placeholder for user's response
		System.out.println("**** Browse Jobs ****");
		while (again) {
			System.out.println("Browse options: " + "\n1: All" + "\n2: Filter");
			do {
				System.out.print("Please enter 1 or 2: ");
				option = in.nextInt();

			} while (option != 1 && option != 2); // check for user input to match desired answer set
			if (option == 1) {
				displayAllListings(); // display all the available job listings
				in.nextLine();
			} else {
				filterJobs(); // // user chose to search listings with filters
			}
			System.out.print("Would you like to browse again? (y):  ");
			String answer = in.nextLine().toLowerCase().trim(); //
			if (!answer.equals("y")) { // checks user's prompt for loop
				break; // user's doesn't want to search any more
			}
		}
	}

	// this method creates list of all the filtered jobs the user's wanted
	private ArrayList<Job> filter(ArrayList<String> filters) {
		ArrayList<Job> filteredJobs = new ArrayList<>();
		for (Map.Entry<String, Job> entry : jobs.entrySet()) { // loop through job listings
			Job j = entry.getValue();
			for (String f : filters) { // loop through filter list
				if (j.toString().toLowerCase().contains(f.toLowerCase()) && !filteredJobs.contains(j)) { // check if job listings contains filters
					filteredJobs.add(j); // if there's a match add to filtered jobs list
				}
			}
		}
		return filteredJobs;
	}

	// this method prompts the user for filters to show relevant listings
	private void filterJobs() {
		ArrayList<String> filters = new ArrayList<>();
		String filter = null; // user prompt
		boolean again = true; // flag for prompt loop
		System.out.println("\n**** Filters ****");
		in.nextLine();
		while (again) {
			System.out.print("Please enter your filter: ");
			filter = in.nextLine();
			filters.add(filter); // add user's filter to list
			System.out.print("Do you want to add another filter? (y): ");
			filter = in.nextLine(); // user's answer to loop prompt
			if (!filter.equalsIgnoreCase("y")) {
				break;
			}
		}
		System.out.println("\nYour filters : " + filters);
		displayListing(filter(filters)); // display the listings that coordinate with user's desired filters
	}

	// this method displays all the job listing info in a neat format
	public void displayAllListings() {
		for (Map.Entry<String, Job> entry : jobs.entrySet()) { // loop through job map to display a job's properties
			displayListing(entry.getValue());
		}
	}

	// this method displays a job listing info in a neat format
	public void displayListing(Job j) {
		NumberFormat money = NumberFormat.getCurrencyInstance(); // creates currency formatter
		System.out.println("********************");
		System.out.println(String.format("ID: %s Title: %s", j.getId(), j.getTitle()));
		System.out.println("Description: " + j.getDescription());
		System.out.println(
				String.format("Address: %s, %s, %s, %d", j.getStreetAddress(), j.getCity(), j.getState(), j.getZip()));
		System.out.println(String.format("Salary: %s", money.format(j.getSalary())));
		System.out.println("Field: " + j.getField());
		System.out.println("Contact Email: " + j.getContactEmail() + "\n");
	}

	public void displayListing(ArrayList<Job> jobList) {
		for (Job j : jobList) { // loop through list to display each job
			displayListing(j);
		}
	}
}
