package com.skillstorm.beans;

public final class User {
	
	private String id;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String resume;
	
	public User(String[] data) {
		id = data[0];
		password = data[1];
		firstName = data[2];
		lastName = data[3];
		email = data[4];
		phoneNumber = data[5];
		resume = data[6];
	}
	
	public User(String id, String password, String firstName, String lastName, String email, String phoneNumber) {
		this.id = id;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		resume = id + "-" + lastName + firstName;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getResume() {
		return resume;
	}	
	
	@Override
	public String toString() {
		return String.format("%s!!!%s!!!%s!!!%s!!!%s!!!%s!!!%s", id, password, firstName, lastName, email, phoneNumber, resume);
	}
}
