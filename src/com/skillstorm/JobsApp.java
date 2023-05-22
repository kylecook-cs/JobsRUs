package com.skillstorm;

import com.skillstorm.services.UserService;

public class JobsApp {

	public static void main(String[] args) {
		start();
	}
	
	public static void start() {
		UserService uS = UserService.getInstance();
		
		uS.welcome();
	}
	
	
}
