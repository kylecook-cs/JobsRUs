package com.skillstorm.beans;

// this class creates a Job object
public class Job {

	private String id;
	private String title;
	private String description;
	private String streetAddress;
	private String city;
	private String state;
	private int zip;
	private double salary;
	private String field;
	private String contactEmail;
	private String jobKey;

	// Constructors
	public Job() {
	}

	public Job(String[] jobs) {
		this.id = jobs[0];
		this.title = jobs[1];
		this.jobKey = id + " " + title;
		this.description = jobs[2];
		this.streetAddress = jobs[3];
		this.city = jobs[4];
		this.state = jobs[5];
		this.zip = Integer.parseInt(jobs[6]);
		this.salary = Double.parseDouble(jobs[7]);
		this.field = jobs[8];
		this.contactEmail = jobs[9];
	}

	public Job(String id, String title, String description, String streetAddress, String city, String state, int zip,
			double salary, String field, String contactEmail) {
		this.id = id;
		this.title = title;
		this.jobKey = id + " " + title;
		this.description = description;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.salary = salary;
		this.field = field;
		this.contactEmail = contactEmail;
	}

	// Getter & Setter methods
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		this.jobKey = id + " " + this.title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		this.jobKey = this.id + " " + title;
	}

	public String getJobKey() {
		return jobKey;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	// Override methods
	@Override
	public String toString() {
		return String.format("%s!!!%s!!!%s!!!%s!!!%s!!!%s!!!%d!!!%.2f!!!%s!!!%s", id, title, description, streetAddress,
				city, state, zip, salary, field, contactEmail);
	}

	@Override
	public boolean equals(Object other) {
		return this.toString().equals(other.toString());
	}
}
