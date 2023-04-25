package com.skillstorm.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.skillstorm.beans.Job;

public class ListingService {

	final private String listingFile = "src\\Listings\\Listings.csv";
	private HashMap<String, Job> jobs;
	

	public ListingService() {
		this.jobs = readListings();
	}

	public HashMap<String, Job> readListings() {
		HashMap<String, Job> jobs = new HashMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(listingFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty()) {
					String[] jobPosts = line.split(",");
					String id = jobPosts[0];
					String title = jobPosts[1];
					jobs.put(id + " " + title, new Job(jobPosts));
				}
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return jobs;
	}

	public void addListing(Job j, boolean flag) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(listingFile, flag))) {
			String listing = j.toString().replace("!!!", ",");
			bw.append(listing);
			bw.newLine();
			jobs.put(j.getId() + " " + j.getTitle(), j);
		} catch (IOException e) {
			System.out.println("Exception caught appending listings file");
		}
	}

	public void removeListing(Job j) {
		boolean flag = false;
		for (Map.Entry<String, Job> entry : jobs.entrySet()) {
			if (entry.getKey().equals(j.getId() + " " + j.getTitle())) {
				jobs.remove(entry.getKey(), j);
				continue;
			}
			addListing(entry.getValue(), flag);
			flag = true;
		}
	}

	public void displayListings() {
		for (Map.Entry<String, Job> entry : jobs.entrySet()) {
			Job j = entry.getValue();
			System.out.println("********************");
			System.out.println(String.format("ID: %s Title: %s", j.getId(), j.getTitle()));
			System.out.println("Description: " + j.getDescription());
			System.out.println(String.format("Address: %s, %s, %s, %d", j.getStreetAddress(), j.getCity(), j.getState(),
					j.getZip()));
			System.out.println(String.format("Salary: $%.2f", j.getSalary()));
			System.out.println("Field: " + j.getField());
			System.out.println("Contact Email: " + j.getContactEmail() + "\n");
		}
	}
	
	public boolean checkDuplicate(Job j) {
		return false;
	}
	
}
