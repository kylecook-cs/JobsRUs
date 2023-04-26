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

	final private String listingFile = "src\\Listings\\Listings.csv"; // file path
	private HashMap<String, Job> jobs; // map that holds all the job listings

	public ListingService() { // constructor that creates map of all current job postings in file
		this.jobs = readListings();
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
		} else {
			jobs.put(j.getJobKey(), j); // job was not in job map so add it
		}
		updateLoop(); // update file
	}

	public ArrayList<Job> filterJobs() {
		ArrayList<Job> filteredJobs = new ArrayList<>();
		ArrayList<String> filters = new ArrayList<>();
		String filter = null;
		boolean again = true;
		Scanner in = new Scanner(System.in);
		System.out.println("\n**** Filters ****");
		while (again) {
			System.out.print("Please enter your filter: ");
			filter = in.nextLine();
			filters.add(filter);
			System.out.print("Do you want to add another filter? (y): ");
			filter = in.nextLine();
			if (!filter.equalsIgnoreCase("y")) {
				again = false;
			}
		}

		System.out.println(filters);
		for (Map.Entry<String, Job> entry : jobs.entrySet()) {
			Job j = entry.getValue();
			for (String f : filters) {
				if (j.toString().contains(f) && !filteredJobs.contains(j)) {
					filteredJobs.add(j);
				}
			}
		}
		System.out.println("\n**** Filtered Jobs ****");
		for (Job j : filteredJobs) {
			displayListing(j);
		}
		in.close();
		return filteredJobs;
	}

	// this method displays all the job listing info in a neat format
	public void displayAllListings() {
		NumberFormat money = NumberFormat.getCurrencyInstance(); // creates currency formatter
		for (Map.Entry<String, Job> entry : jobs.entrySet()) { // loop through job map to display a job's properties
			Job j = entry.getValue();
			System.out.println("\n********************");
			System.out.println(String.format("ID: %s Title: %s", j.getId(), j.getTitle()));
			System.out.println("Description: " + j.getDescription());
			System.out.println(String.format("Address: %s, %s, %s, %d", j.getStreetAddress(), j.getCity(), j.getState(),
					j.getZip()));
			System.out.println(String.format("Salary: %s", money.format(j.getSalary())));
			System.out.println("Field: " + j.getField());
			System.out.println("Contact Email: " + j.getContactEmail() + "\n");
		}
	}

	// this method displays a job listing info in a neat format
	public void displayListing(Job j) {
		NumberFormat money = NumberFormat.getCurrencyInstance(); // creates currency formatter
		System.out.println("********************");
		System.out.println(String.format("ID: %s Title: %s", j.getId(), j.getTitle()));
		System.out.println("Description: " + j.getDescription());
		System.out.println(String.format("Address: %s, %s, %s, %d", j.getStreetAddress(), j.getCity(), j.getState(), j.getZip()));
		System.out.println(String.format("Salary: %s", money.format(j.getSalary())));
		System.out.println("Field: " + j.getField());
		System.out.println("Contact Email: " + j.getContactEmail() + "\n");
	}
}
