package com.skillstorm;

import com.skillstorm.beans.Job;
import com.skillstorm.services.ListingService;
import com.skillstorm.services.ResumeHandler;
import com.skillstorm.services.UserService;

public class JobsApp {

	public static void main(String[] args) {
//		ListingService ls = ListingService.getInstance();
//		ResumeHandler rh = new ResumeHandler();
//
//
//		ls.addListing(new Job("Software Developer", "Build Maintain Debug Features", "123 Java Ave",
//				"San Diego", "CA", 92113, 100_000, "Computer", "google@gmail.com"));
//
//		ls.addListing(new Job("56789", "Software Engineer", "Full Stack Engineer", "987 Python Way", "Georgetown", "TX",
//				78628, 125_000, "Computer", "contact@dell.com"));
//		
//		ls.addListing(new Job("4567", "DevOps", "Testing Apps", "456 C Sharp Circle", "San Jose", "CA",
//				95112, 140_000, "Computer", "contact@dyahoo.com"));
//
//		ls.addListing(new Job("12345", "Software Developer", "Build Maintain Debug Features", "123 Java Ave",
//				"San Diego", "CA", 92113, 100_000, "Computer", "google@gmail.com"));
//
//		ls.addListing(new Job("56789", "Software Engineer", "Full Stack Engineer", "987 Python Way", "Georgetown", "TX",
//				78628, 125_000, "Computer", "contact@dell.com"));
//
//		ls.removeListing(new Job("12345", "Software Developer", "Build Maintain Debug Features", "123 Java Ave",
//				"San Diego", "CA", 92113, 100_000, "Computer", "google@gmail.com"));
//
//		ls.displayAllListings();
//		
//		ls.browseJobs();
		
		
//		rh.readResume("src\\Users\\Resume.txt");
//		rh.displayResume();
//		rh.readResume("src\\Users\\TestResume.txt");
//		rh.displayResume();
//		rh.editResume();
//		rh.displayResume();
//		rh.writeResume("src\\Users\\TestResume.txt");
//		
		userTest();
	}
	
	public static void userTest() {
		UserService uS = UserService.getInstance();
		
		uS.welcome();
	}

	
}
