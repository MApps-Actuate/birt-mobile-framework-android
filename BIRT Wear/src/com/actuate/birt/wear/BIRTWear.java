package com.actuate.birt.wear;

import com.actuate.birt.wear.options.UIOptions;

public interface BIRTWear {	
	// Setters
	public void setReportName(String reportName);
	public void setUsername(String username);
	public void setPassword(String password);
	public void enableInteractivity(boolean interactivity);
	public void setVolume(String volume);
	public void setJSAPILocation(String jsapiLocation);
	public void setUIOptions(UIOptions uiOptions);
	public void setIportalLocation(String iPortalLocation);
	public void setCustomParameters(String customParameters);
	
	// Getters
	public String    getReportName();
	public String    getUsername();
	public String    getVolume();
	public String    getJsapiLocation();
	public String    getIportalLocation();
	public String	 getCustomParameters();
	public UIOptions getUIOptions();
	public boolean   getPassword();
	public boolean   getInteractivity();
	
	// Other methods
	public String  getReport();
}
