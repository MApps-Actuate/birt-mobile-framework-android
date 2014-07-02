package com.actuate.developer;

import com.actuate.developer.BMDK.Output;

public class Test {
	public static void main(String[] args) {
		BMDK test = new BMDK();
		test.setHost("http://demo.actuate.com");
		test.setUsername("username");
		test.setPassword("password");
		test.setVolume("Default Volume");
		
		String html = test.exportReport("/Home/devSite/test.rptdesign", Output.WebViewer);
		System.out.println(html);
	}
}
