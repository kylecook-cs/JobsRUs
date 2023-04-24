package com.skillstorm;

public class Jobs {
	
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
	
	public Jobs(String id, String title, String description, String streetAddress, String city, String state, int zip,
			double salary, String field, String contactEmail) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.salary = salary;
		this.field = field;
		this.contactEmail = contactEmail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	@Override
	public String toString() {
		return "Jobs [id=" + id + ", title=" + title + ", description=" + description + ", streetAddress="
				+ streetAddress + ", city=" + city + ", state=" + state + ", zip=" + zip + ", salary=" + salary
				+ ", field=" + field + ", contactEmail=" + contactEmail + "]";
	}
	
	
	
	
	

}
