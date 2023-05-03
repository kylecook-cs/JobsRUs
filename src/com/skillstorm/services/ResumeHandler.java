package com.skillstorm.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ResumeHandler {

	public void readResume(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.isEmpty()) {
					System.out.println(line);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("That file is not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void writeResume(String filePath) {
		boolean flag = true;

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			Scanner in = new Scanner(System.in);
			System.out.println("Welcome to the Resume Builder");
			System.out.print("Please enter the name you would like to appear on the resume: ");
			bw.write("                              " + in.nextLine());
			bw.newLine();
			System.out.print("\nPlease enter the address you would like to appear on the resume: ");
			bw.write("                    " + in.nextLine());
			bw.newLine();
			System.out.print("\nPlease enter the email you would like to appear on the resume: ");
			bw.write("                           " + in.nextLine());
			bw.newLine();
			System.out.print("\nPlease enter the phone number you would like to appear on the resume: ");
			bw.write("                             " + in.nextLine());
			bw.newLine();
			bw.newLine();
			bw.write("EDUCATION");
			bw.newLine();
			do {
				System.out.println("\nEDUCATION");
				System.out.print("\nPlease enter your major or certification title: ");
				bw.write("  " + in.nextLine());
				System.out.print("\nPlease enter the name of your school or organzation: ");
				bw.write(" - " + in.nextLine());
				bw.newLine();
				System.out.print("\nWould you like to add any accolades? (Y/N): ");
				if (in.nextLine().equalsIgnoreCase("y")) {
					do {
						System.out.print("\nEnter accolade: ");
						bw.write("   \u2022\t" + in.nextLine());
						bw.newLine();
						System.out.print("\nWould you like to add another? (Y/N): ");
						if (!(in.nextLine().equalsIgnoreCase("y"))) {
							flag = false;
						}
					} while (flag);
					flag = true;
				}
				System.out.print("\nWould you like to add another degree or certification? (Y/N): ");
				if (!in.nextLine().equalsIgnoreCase("y")) {
					flag = false;
				}
			} while (flag);
			flag = true;
			bw.newLine();
			bw.write("EXPERIENCE");
			bw.newLine();
			do {
				System.out.println("\nEXPERIENCE");
				System.out.print("\nPlease enter the posititon title: ");
				bw.write("  " + in.nextLine());
				System.out.print("\nPlease enter the company name: ");
				bw.write(" - " + in.nextLine());
				System.out.print("\nPlease enter the time frame (ex - October 2007 -  May 2014: ");
				bw.write("                    " + in.nextLine());
				bw.newLine();
				System.out.print("\nWould you like to add a description bullet? (Y/N): ");
				if (in.nextLine().equalsIgnoreCase("y")) {
					do {
						System.out.print("\nEnter description: ");
						bw.write("   \u2022\t" + in.nextLine());
						bw.newLine();
						System.out.print("\nWould you like to add another? (Y/N): ");
						if (!in.nextLine().equalsIgnoreCase("y")) {
							flag = false;
						}
					} while (flag);
					flag = true;
				}
				System.out.print("\nWould you like to add more job experience? (Y/N): ");
				if (!in.nextLine().equalsIgnoreCase("y")) {
					flag = false;
				}
			} while (flag);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
