package com.actuate.developer;

public class Test {
	public static void main(String[] args) {
		BMDK test = new BMDK();
		test.setHost("http://demo.actuate.com");
		test.setUsername("username");
		test.setPassword("password");
		test.setVolume("Default Volume");
		
		String html = test.reportListing();
		
		System.out.println(html);
	}
}
