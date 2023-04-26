package com.skillstorm;

import com.skillstorm.beans.Job;
import com.skillstorm.services.ListingService;

public class JobsApp {

	public static void main(String[] args) {
		ListingService ls = new ListingService();


		ls.addListing(new Job("12345", "Software Developer", "Build Maintain Debug Features", "123 Java Ave",
				"San Diego", "CA", 92113, 100_000, "Computer", "google@gmail.com"));

		ls.addListing(new Job("56789", "Software Engineer", "Full Stack Engineer", "987 Python Way", "Georgetown", "TX",
				78628, 125_000, "Computer", "contact@dell.com"));
		
		ls.addListing(new Job("4567", "DevOps", "Testing Apps", "456 C Sharp Circle", "San Jose", "CA",
				95112, 140_000, "Computer", "contact@dyahoo.com"));

		ls.addListing(new Job("12345", "Software Developer", "Build Maintain Debug Features", "123 Java Ave",
				"San Diego", "CA", 92113, 100_000, "Computer", "goooogle@gmail.com"));

		ls.addListing(new Job("56789", "Software Engineer", "Full Stack Engineer", "987 Python Way", "Georgetown", "TX",
				78628, 125_000, "Computer", "contact@dell.com"));

//		ls.removeListing(new Job("12345", "Software Developer", "Build Maintain Debug Features", "123 Java Ave",
//				"San Diego", "CA", 92113, 100_000, "Computer", "google@gmail.com"));

		ls.displayAllListings();
		
		ls.filterJobs();

	}

}
