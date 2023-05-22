package com.skillstorm.services;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// This class is a handler for a Resume file 
public class ResumeHandler {
	
	private ArrayList<String> resume = new ArrayList<>();
	private boolean flag = true;
	private static Scanner in = new Scanner(System.in);
	
	// This method is to read the resume file and display it
	private void readResume(String filePath) {
		resume.clear();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) { // read in the resume file
			String line;
			while ((line = br.readLine()) != null) { // while there is a line in the resume file
				if (!line.isEmpty()) { // checks if the line is empty
					resume.add(line); // add the line to resume ArrayList
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("\nYour Resume file was not found.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// This method will take in an ArrayList that contains all the lines
	// of a Resume file and displays it
	private void displayResume() {
		for (String line : resume) { // iterates lines in ArrayList holding Resume file lines
			System.out.println(line); // displays line
		}
	}
	
	// This method is to prompt user for header section data of Resume file
	private void header() {
		try {
			resume.removeAll(resume.subList(0, 4)); // removes header section data from ArrayList
		} catch (IndexOutOfBoundsException e) { // catches if nothing exists
		} // prompt user for data for header section
		System.out.print("\nPlease enter the name you would like to have appear on the resume: ");
		resume.add(0, "                              " + in.nextLine());
		System.out.print("\nPlease enter the address you would like to have appear on the resume: ");
		resume.add(1, "                    " + in.nextLine());
		System.out.print("\nPlease enter the email you would like to have appear on the resume: ");
		resume.add(2, "                           " + in.nextLine());
		System.out.print("\nPlease enter the phone number you would like to have appear on the resume: ");
		resume.add(3, "                             " + in.nextLine());
		if (!resume.contains("*EDUCATION*")) { // checks if Education section is made if not it creates one
			resume.add(4, "*EDUCATION*");
		}
	}
	
	// This method is to prompt user for education section data of Resume file
	private void education() {
		flag = true;
		System.out.println("\nEDUCATION");
		try {
			for (int i = resume.indexOf("*EDUCATION*") + 1; !resume.get(i).equals("*EXPERIENCE*");) {
				resume.remove(resume.indexOf("*EDUCATION*") + 1); // removes education section data from ArrayList
			}
		} catch (IndexOutOfBoundsException e) { // catches if nothing exists
		} // prompt user for data for education section
		for (int i = resume.indexOf("*EDUCATION*") + 1;; i++) {
			System.out.print("\nPlease enter your major or certification title: ");
			String major = in.nextLine();
			System.out.print("\nPlease enter the name of your school or organization: ");
			String school = in.nextLine();
			resume.add(i, "  " + major + " - " + school);
			System.out.print("\nWould you like to add any accolades? (Y/N): ");
			if ("y".equalsIgnoreCase(in.nextLine())) {
				do {
					System.out.print("\nEnter accolade: ");
					resume.add(++i, "   \u2022\t" + in.nextLine());
					System.out.print("\nWould you like to add another? (Y/N): ");
					if (!"y".equalsIgnoreCase(in.nextLine())) {
						flag = false;
					}
				} while (flag);
				flag = true;
			}
			System.out.print("\nWould you like to add another degree or certification? (Y/N): ");
			if (!"y".equalsIgnoreCase(in.nextLine())) {
				if (!resume.contains("*EXPERIENCE*")) {
					resume.add("*EXPERIENCE*");
				}
				break;
			}
		}
	}
	
	// This method is to prompt user for experience section data of Resume file
	private void experience() {
		flag = true;
		System.out.println("\nEXPERIENCE");
		try { // removes experience section data from ArrayList
			resume.removeAll(resume.subList(resume.indexOf("*EXPERIENCE*") + 1, resume.size()));
		} catch (IndexOutOfBoundsException e) { // catches if nothing exists
		} // prompt user for data for experience section
		for (int i = resume.indexOf("*EXPERIENCE*") + 1;; i++) {
			System.out.print("\nPlease enter the position title: ");
			String position = in.nextLine();
			System.out.print("\nPlease enter the company name: ");
			String company = in.nextLine();
			System.out.print("\nPlease enter the time frame (ex - October 2007 -  May 2014): ");
			String time = in.nextLine();
			resume.add(i, "  " + position + " - " + company + "                    " + time);
			System.out.print("\nWould you like to add a description bullet? (Y/N): ");
			if ("y".equalsIgnoreCase(in.nextLine())) {
				do {
					System.out.print("\nEnter description: ");
					resume.add(++i, "   \u2022\t" + in.nextLine());
					System.out.print("\nWould you like to add another? (Y/N): ");
					if (!"y".equalsIgnoreCase(in.nextLine())) {
						flag = false;
					}
				} while (flag);
				flag = true;
			}
			System.out.print("\nWould you like to add more job experience? (Y/N): ");
			if (!"y".equalsIgnoreCase(in.nextLine())) {
				break;
			}
		}
	}
	
	// This method is called to create the ArrayList that will hold lines of the
	// Resume file
	private void createResume() {
		System.out.println("\nWelcome to JOBS R US's Resume Builder\n");
		header();
		education();
		experience();
	}
	
	// This method is used to edit sections of a Resume file
	private void editResume() {
		String choice = "";
		flag = true;
		System.out.println("\nWelcome to JOBS R US's Resume Editor");
		do {
			do {
				System.out.println("\n1. Header Section");
				System.out.println("2. Education Section");
				System.out.println("3. Experience Section");
				System.out.println("9. Previous Menu");
				System.out.print("Which section of your resume would you like to edit?: ");
				choice = in.nextLine();
			} while (!"1".equals(choice) && !"2".equals(choice) && !"3".equals(choice) && !"9".equals(choice));
			if (choice.equals("1")) {
				header();
			} else if (choice.equals("2")) {
				education();
			} else if (choice.equals("3")) {
				experience();
			} else {
				return;
			}
			System.out.print("\nWould you like to edit another section? (Y/N): ");
			if (!"y".equalsIgnoreCase(in.nextLine())) {
				flag = false;
			}
		} while (flag);
	}
	
	public void resumeOptions(String resume) {
		String choice = "";
		String resumePath = "";
		if (System.getProperty("user.dir").endsWith("bin")) {
			File resumeDir = new File("Users\\" + resume);
			resumeDir.mkdir();
			resumePath = "Users\\" + resume + "\\resume.txt";
        } else {
        	File resumeDir = new File("bin\\Users\\" + resume);
			resumeDir.mkdir();
			resumePath = "bin\\Users\\" + resume + "\\resume.txt";
        }
		if (checkFile(resumePath)) {
			do {
				System.out.printf("%n1. Display Resume" + "%n2. Edit Resume" + "%n9. Previous Menu" + "%nEnter choice: ");
				choice = in.nextLine();
			} while (!"1".equals(choice) && !"2".equals(choice) && !"9".equals(choice));
			if (choice.equals("1")) {
				readResume(resumePath);
				displayResume();
			} else if (choice.equals("2")) {
				readResume(resumePath);
				editResume();
				writeResume(resumePath);
			}  else if (choice.equals("9")) {
				return;
			}
		} else {
			System.out.println("\nYou don't have a resume on file.");
			System.out.print("Would you like to create one? (Y/N) : ");
			if ("y".equalsIgnoreCase(in.nextLine())) {
				createResume();
				writeResume(resumePath);
			} else {
				return;
			}
		}
		resumeOptions(resume);
	}
	
	private boolean checkFile(String fileName) {
		return new File(fileName).exists();
	}
	// This method will create a Resume file based on user prompt stored in
	// ArrayList
	private void writeResume(String filePath) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			for (String line : resume) {
				bw.write(line);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}