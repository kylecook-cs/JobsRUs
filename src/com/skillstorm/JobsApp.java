package com.skillstorm;

import com.skillstorm.beans.Job;
import com.skillstorm.services.ListingService;

public class JobsApp {

	public static void main(String[] args) {
		ListingService ls = new ListingService();

		ls.addListing(new Job("12345", "Software Developer", "Build Maintain Debug Features", "123 Java Ave",
				"San Diego", "CA", 92113, 100_000, "Computer", "google@gmail.com"), true);

		ls.addListing(new Job("56789", "Software Engineer", "Full Stack Engineer", "987 Python Way", "Georgetown", "TX",
				78628, 125_000, "Computer", "contact@dell.com"), true);

		ls.addListing(new Job("12345", "Software Developer", "Build Maintain Debug Features", "123 Java Ave",
				"San Diego", "CA", 92113, 100_000, "Computer", "google@gmail.com"), true);

		ls.addListing(new Job("56789", "Software Engineer", "Full Stack Engineer", "987 Python Way", "Georgetown", "TX",
				78628, 125_000, "Computer", "contact@dell.com"), true);

//		ls.removeListing(new Job("12345", "Software Developer", "Build Maintain Debug Features", "123 Java Ave",
//				"San Diego", "CA", 92113, 100_000, "Computer", "google@gmail.com"));

//		ls.displayListings();

	}

}
