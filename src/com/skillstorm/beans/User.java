package com.skillstorm.beans;

public class User {
	
	private String id;
	private String password;
	private String name;
	private String email;
	private String phoneNumber;
	private String resume;

	
	public User(String[] data) {
		id = data[0];
		password = data[1];
		name = data[2];
		email = data[3];
		phoneNumber = data[4];
		resume = data[5];
	}
	
	public User(String id, String password, String name, String email, String phoneNumber, String resume) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.resume = resume;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void setResume(String resume) {
		this.resume = resume;
	}	
	
	@Override
	public String toString() {
		return String.format("%s!!!%s!!!%s!!!%s!!!%s!!!%s", id, password, name, email, phoneNumber, resume);
	}	
}
