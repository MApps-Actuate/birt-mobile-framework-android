package com.actuate.birt.wear.exception;

public class BIRTWearException extends Exception {
	//String message = "BIRTWearMD5Exception: MD5 mismatch!";
	private String message = null;
	
	public BIRTWearException() {
		super();
	}
	
	public BIRTWearException(String message) {
		super(message);
		this.message = message;
	}
	
	public BIRTWearException(Throwable cause) {
		super(cause);
	}
	
	@Override
	public String toString() {
		return message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
}
