package com.actuate.developer;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

public class BMDK {
	// Public variables
	public enum Output {WebViewer, HTML, PDF, XLS, XLSX};  // TODO: Add more output types
	
	// Private variables
	private String	  username;
	private String	  password;
	private String	  host;
	private String	  volume;
	private SecretKey key;
	private String    reportListing = "<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\" /><title>Report Explorer Page</title></head><body onload=\"init( )\"><div id=\"explorerpane\"><script type=\"text/javascript\" language=\"JavaScript\" src=\"host/iportal/jsapi\"></script><script type=\"text/javascript\" language=\"JavaScript\">function init( ) {  actuate.load(\"reportexplorer\");  requestOpts = new actuate.RequestOptions( );  requestOpts.setVolumeProfile(\"volume\");  actuate.initialize( \"host/iportal/\", requestOpts, \"username\", \"password\", runReportExplorer);}function runReportExplorer( ) {  var explorer = new actuate.ReportExplorer(\"explorerpane\");  explorer.setFolderName( \"/Home/devSite\" );  var resultDef = \"Name|FileType|Version|VersionName|Description\";  explorer.setResultDef( resultDef.split(\"|\") );  explorer.submit( );}</script></div></body></html>";
	private Cipher    ecipher;
	private Cipher    dcipher;
		
	// BMDK constructor.  This will create encryption/decryption
	// keys that persist durring the life of this Class
	// through the live of the Class.
	public BMDK() {
		try {
			// Create DES key
			this.key = KeyGenerator.getInstance("DES").generateKey();
			
			// Create the ciphers
			ecipher = Cipher.getInstance("DES");
			dcipher = Cipher.getInstance("DES");
			
			// Init the ciphers with our new key
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher.init(Cipher.DECRYPT_MODE, key);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void exportReport(String reportName, Output outputType) {
		if(outputType.equals(Output.WebViewer)) {
			// TODO: Regex out placeholders from viewReport
			// and replace with reportName, outputType, username,
			// password, host, volume and return the HTML as
			// a byte array.
		}else if(outputType.equals(Output.HTML)) {
			// TODO: Regex out placeholders from viewReport
			// and replace with reportName, outputType, username,
			// password, host, volume and return the HTML as
			// a byte array.
		}else if(outputType.equals(Output.PDF)) {
			// TODO: Regex out placeholders from viewReport
			// and replace with reportName, outputType, username,
			// password, host, volume and return the PDF as
			// a byte array.
		}else if(outputType.equals(Output.XLS)) {
			// TODO: Regex out placeholders from viewReport
			// and replace with reportName, outputType, username,
			// password, host, volume and return the XLS as
			// a byte array.
		}else if(outputType.equals(Output.XLSX)) {
			// TODO: Regex out placeholders from viewReport
			// and replace with reportName, outputType, username,
			// password, host, volume and return the XLSX as
			// a byte array.
		}
		
		// TODO: Add more output types
	}
	
	public String reportListing() {
		String temp = this.reportListing.replaceAll("username", this.decrypt(this.username));
		temp		= temp.replaceAll("password", this.decrypt(this.password));
		temp		= temp.replaceAll("host", this.decrypt(this.host));
		temp		= temp.replaceAll("volume", this.decrypt(this.volume));
		
		return temp;
	}
	
	// Takes a string, encrpyts it, then returns the encrypted
	// version.
 	private String encrypt(String password) {
		String encPass = new String();
		
		try {
			byte[] utf8 = password.getBytes("UTF8");
			byte[] enc  = ecipher.doFinal(utf8);
			
			// Encode to base64
			enc = BASE64EncoderStream.encode(enc);
			
			encPass = new String(enc);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return encPass;
	}
	
	// Take an encrypted string and returns it decrypted
	private String decrypt(String password) {
		String decrypted = new String();
		
		try {
			// Decode with base64
			byte[] dec  = BASE64DecoderStream.decode(password.getBytes());
			byte[] utf8 = dcipher.doFinal(dec);
			
			// Create new string
			decrypted = new String(utf8, "UTF8");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return decrypted;
	}
	
	// Setter for the username
	// Set's the username
	public void setUsername(String username) {
		String temp = this.encrypt(username);
		this.username = temp;
	}
	
	// Setter for the password
	// Set's the password
	public void setPassword(String password) {		
		String temp = this.encrypt(password);
		this.password = temp;
	}
	
	// Setter for the host
	// Set's the host for the iHub
	public void setHost(String host) {
		String temp = this.encrypt(host);
		this.host = temp;
	}
	
	// Setter or the volume
	// Set's the volume name
	public void setVolume(String volume) {
		String temp = this.encrypt(volume);
		this.volume = temp;
	}
	
	// Getter for the username
	// Get's the set username
	public String getUsername() {
		return this.decrypt(this.username);
	}
	
	// Checks if the password is set and returns
	// true if yes, false if no.
	// Checks if the password has been set
	public boolean getPassword() {
		if(this.password == null) {
			return false;
		}
		
		return true;
	}
	
	// Getter for the host
	// Get's the host name that has been set
	public String getHost() {
		return this.decrypt(this.host);
	}
	
	// Getter for the volume
	// Get's the volume that has been set
	public String getVolume() {
		return this.decrypt(this.volume);
	}
}
