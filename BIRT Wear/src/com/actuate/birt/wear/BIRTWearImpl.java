package com.actuate.birt.wear;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import com.actuate.birt.wear.exception.BIRTWearException;
import com.actuate.birt.wear.jsapi.JSAPI;
import com.actuate.birt.wear.options.UIOptions;
import com.actuate.birt.wear.options.UIOptionsImpl;

public class BIRTWearImpl implements BIRTWear {
	// BIRT Wear properties
	private String    reportName       = new String();
	private String    username         = new String();
	private String    password         = new String();
	private String    volume           = new String();
	private String    jsapilocation    = new String();
	private String	  iportallocation  = new String();
	private String	  customparameters = new String();
	private UIOptions uiOptions        = new UIOptionsImpl();
	private boolean   interactivity    = true;
	
	// Hash string for security
	private long reportNameChecksum;
	private long usernameChecksum;
	private long passwordChecksum;
	private long jsapiChecksum;
	
	// The holder for our JSAPI
	private String jsapi          = new String();
	
	// BIRTWear Constructor
	public BIRTWearImpl() {
		// Read the JSAPI code
		this.jsapi = new JSAPI().getJSAPI();
		
		// Create the Checksum for our JSAPI
		this.jsapiChecksum = this.createHash(this.jsapi);
	}
	
	// Creates an Checksum hash from a file and returns it
	private long createHash(String contents) {
		Checksum checksum = new CRC32();
		byte bytes[] = contents.getBytes();
		checksum.update(bytes, 0, bytes.length);
		long checksumValue = checksum.getValue();
		return checksumValue;
	}
	
	// Replaces values in the JSAPI String
	private String replaceJSAPIValue(String key, String value, String JSAPI) {
		String newJSAPI = JSAPI;
		
		newJSAPI = newJSAPI.replace(key, value);
		
		return newJSAPI;
	}
	
	// Checks if Checksum's match
	private void checkChecksum(long fromClass, long generated) throws BIRTWearException {
		if(fromClass != generated) {
			throw new BIRTWearException("BIRTWearChecksumException: Checksum's don't match!");
		}
	}
	
	@Override
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	@Override
	public void enableInteractivity(boolean interactivity) {
		this.interactivity = interactivity;
	}

	@Override
	public String getReportName() {		
		return this.reportName;
	}
	
	@Override
	public boolean getInteractivity() {		
		return this.interactivity;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
		
		// Create the Checksum
		this.usernameChecksum = this.createHash(username);
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
		
		// Create the Checksum
		this.passwordChecksum = this.createHash(password);
	}

	@Override
	public String getUsername() {
		try {
			this.checkChecksum(this.usernameChecksum, this.createHash(this.username));
		}catch(BIRTWearException ex){
			ex.printStackTrace();
		}
		
		return this.username;
	}

	@Override
	public boolean getPassword() {
		if(this.password != null) {
			try {
				this.checkChecksum(this.passwordChecksum, this.createHash(this.password));
			}catch(BIRTWearException ex){
				ex.printStackTrace();
			}
			
			return true;
		}
		
		return false;
	}

	@Override
	public String getReport() {
		try {			
			this.checkChecksum(this.jsapiChecksum, this.createHash(this.jsapi));
			
			String jsapi = this.jsapi;
			
			jsapi = this.replaceJSAPIValue("JSAPILOCATION",    this.getJsapiLocation(), 			  jsapi);
			jsapi = this.replaceJSAPIValue("CUSTOMPARAMETERS", this.getCustomParameters(),			  jsapi);
			jsapi = this.replaceJSAPIValue("IPORTALLOCATION",  this.getIportalLocation(), 		  	  jsapi);
			jsapi = this.replaceJSAPIValue("VOLUME",           this.getVolume(),        			  jsapi);
			jsapi = this.replaceJSAPIValue("USERNAME",         this.getUsername(),      			  jsapi);
			jsapi = this.replaceJSAPIValue("PASSWORD",         this.password,           			  jsapi);
			jsapi = this.replaceJSAPIValue("JSAPILOCATION",    this.getJsapiLocation(), 			  jsapi);
			jsapi = this.replaceJSAPIValue("REPORTNAME",       this.getReportName(),    			  jsapi);
			jsapi = this.replaceJSAPIValue("JSAPILOCATION",    this.getJsapiLocation(), 		   	  jsapi);
			jsapi = this.replaceJSAPIValue("SETOPTIONS",       this.getUIOptions().getUIOptions(),    jsapi);
			
			return jsapi;
		}catch(BIRTWearException ex){
			ex.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void setVolume(String volume) {
		this.volume = volume;
	}

	@Override
	public void setJSAPILocation(String jsapiLocation) {
		this.jsapilocation = jsapilocation;
	}

	@Override
	public String getVolume() {
		return this.volume;
	}

	@Override
	public String getJsapiLocation() {
		return this.jsapilocation;
	}

	@Override
	public void setUIOptions(UIOptions uiOptions) {
		this.uiOptions = uiOptions;
	}

	@Override
	public UIOptions getUIOptions() {
		return this.uiOptions;
	}

	@Override
	public void setIportalLocation(String iPortalLocation) {
		this.iportallocation = iPortalLocation;
	}

	@Override
	public String getIportalLocation() {
		return this.iportallocation;
	}

	@Override
	public void setCustomParameters(String customParameters) {
		this.customparameters = customParameters;
	}

	@Override
	public String getCustomParameters() {
		return this.customparameters;
	}
}
