package com.skillstorm;

import com.skillstorm.beans.Job;
import com.skillstorm.services.ListingService;
import com.skillstorm.services.ResumeHandler;

public class JobsApp {

	public static void main(String[] args) {
		
		listingTest();
		//resumeTest();
		
		

	}
	public static void listingTest() {
		ListingService ls = ListingService.getInstance();

		
		ls.browseJobs();
	}

	public static void resumeTest() {
		
		ResumeHandler rh = new ResumeHandler();
		
		rh.resumeOptions("src\\Users\\TestResume.txt");
	}
}
