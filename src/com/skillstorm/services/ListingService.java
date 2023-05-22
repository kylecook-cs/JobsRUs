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
	final private String listingFile; // file path
	private HashMap<String, Job> jobs; // map that holds all the job listings
	private static ListingService instance = null;

	private ListingService() { // constructor that creates map of all current job postings in file
		if (System.getProperty("user.dir").endsWith("bin")) {
            listingFile = "Listings\\Listings.csv";
        } else {
            listingFile = "bin\\Listings\\Listings.csv";
        }
		this.jobs = readListings();
	}

	public static ListingService getInstance() {
		if (instance == null) {
			instance = new ListingService();
		}
		return instance;
	}

	// this method reads in the listings file's contents and maps them to a hashmap
	private HashMap<String, Job> readListings() {
		HashMap<String, Job> jobs = new HashMap<>(); // create new jobs map
		try (BufferedReader br = new BufferedReader(new FileReader(listingFile))) { // read in the file
			String line;
			while ((line = br.readLine()) != null) { // while there is a line in file
				if (!line.isEmpty()) { // checks if line has any values
					String[] jobPosts = line.split("!!!"); // split line into array
					String id = jobPosts[0];
					jobs.put(id, new Job(jobPosts)); // adds job to map with it's id and title being the key
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
	private void updateListings(Job j, boolean flag) {	
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(listingFile, flag))) { // try to open file to write																			// // to
			String listing = j.toString();
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
			updateListings(entry.getValue(), flag); // pass job value to append to file
			flag = true; // changes to append instead of overwriting file
		}
	}

	// this method removes a listing from map and file
	private void removeListing(Job j) {
		if (jobs.containsKey(j.getId())) { // check if job map contains this job
			jobs.remove(j.getId()); // if so remove it
		}
		updateLoop(); // update file
	}

	// this method adds a listing to the map and file
	private void addListing(Job j) {
		if (jobs.isEmpty()) { // checks if job map is empty
			updateListings(j, false); // if so add the job to file
			jobs.put(j.getId(), j); // add job to jobs map
		} else if (jobs.containsKey(j.getId())) { // checks if job key is already in file
			jobs.replace(j.getId(), j); // replaces in case job's properties are updated
			updateLoop();
		} else {
			jobs.put(j.getId(), j); // job was not in job map so add it
			updateListings(j, true);
		}
	}

	// this method allows users to browse the job listings
	public void browseJobs() {
		readListings();
		boolean again = true; // flag for user's loop prompt
		String option = ""; // placeholder for user's response
		System.out.println("\n** Job Browser **");
		while (again) {
			System.out.println("\nBrowse options: " + "\n1. All" + "\n2. Filter" +"\n9. Previous Menu");
			do {
				System.out.print("Please enter choice: ");
				option = in.nextLine();

			} while (!"1".equals(option) && !"2".equals(option) && !"9".equals(option)); // check for user input to match desired answer set
			if (option.equals("1")) {
				displayAllListings(); // display all the available job listings
			} else if (option.equals("2")){
				filterJobs(); // // user chose to search listings with filters
			} else {
				return;
			}
			System.out.print("Would you like to browse again? (y):  ");
			String answer = in.nextLine().toLowerCase().trim(); //
			if (!"y".equals(answer)) { // checks user's prompt for loop
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
				// check if job listings contains filters
				if (j.toString().toLowerCase().contains(f.toLowerCase()) && !filteredJobs.contains(j)) { 
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
		System.out.println("\n** Filters **");
		while (again) {
			System.out.print("Please enter your filter: ");
			filter = in.nextLine();
			filters.add(filter); // add user's filter to list
			System.out.print("\nDo you want to add another filter? (y): ");
			filter = in.nextLine(); // user's answer to loop prompt
			if (!"y".equalsIgnoreCase(filter)) {
				break;
			}
		}
		System.out.println("\n\nYour filters : " + filters);
		displayListing(filter(filters)); // display the listings that coordinate with user's desired filters
	}

	// this method displays all the job listing info in a neat format
	private void displayAllListings() {
		if (!jobs.isEmpty()) {
			for (Map.Entry<String, Job> entry : jobs.entrySet()) { // loop through job map to display a job's properties
				displayListing(entry.getValue());
			}
		}
	}

	// this method displays a job listing info in a neat format
	private void displayListing(Job j) {
		NumberFormat money = NumberFormat.getCurrencyInstance(); // creates currency formatter
		System.out.println("********************");
		System.out.println(String.format("ID: %s \nTitle: %s", j.getId(), j.getTitle()));
		System.out.println("Description: " + j.getDescription());
		System.out.println(
				String.format("Address: %s, %s, %s, %d", j.getStreetAddress(), j.getCity(), j.getState(), j.getZip()));
		System.out.println(String.format("Salary: %s", money.format(j.getSalary())));
		System.out.println("Field: " + j.getField());
		System.out.println("Contact Email: " + j.getContactEmail() + "\n");
	}

	private void displayListing(ArrayList<Job> jobList) {
		for (Job j : jobList) { // loop through list to display each job
			displayListing(j);
		}
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Job> getJobs() {
		return (HashMap<String, Job>) jobs.clone();
	}

}
