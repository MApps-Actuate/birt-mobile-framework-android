Under Construction: birt-mobile-framework-android
=============================

This is the Java-based framework to be used by Android mobile app developers to include BIRT content into their apps.

How to
=============================
BIRTWear  birtWear  = new BIRTWearImpl();

UIOptions uiOptions = new UIOptionsImpl();
		
birtWear.setJSAPILocation("http://localhost:8700/jsapi");
birtWear.setVolume("Default Volume");
birtWear.setUsername("Administrator");
birtWear.setPassword("");
birtWear.setReportName("Report Designs/test.rptdesign");
birtWear.setUIOptions(uiOptions);
birtWear.setIportalLocation("http://localhost:8700/iportal");
birtWear.setCustomParameters("reqOps.setCustomParameters({'__masterpage': 'false'});");
		
String jsapi = birtWear.getReport();
		
// PLACE jsapi IN YOUR WEBVIEW
