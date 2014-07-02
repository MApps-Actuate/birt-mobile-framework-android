package com.actuate.developer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

public class BMDK {
	// Public variables
	public enum OutputType {WebViewer, HTML, PDF, XLS, XLSX,
							ODP, ODS, ODT, PS, PPT, PPTX, DOC, DOCX}; // TODO: Add spudsoft?
	
	// Private variables
	private String	       username;
	private String	       password;
	private String	       host;
	private String	       volume;
	private SecretKey      key;
	private Cipher         ecipher;
	private Cipher         dcipher;
	private MessageDigest  MD5;
	private final String   reportListing        = "<html><head><meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\" /><title>Report Explorer Page</title></head><body onload=\"init( )\"><div id=\"explorerpane\"><script type=\"text/javascript\" language=\"JavaScript\" src=\"host/iportal/jsapi\"></script><script type=\"text/javascript\" language=\"JavaScript\">function init( ) {  actuate.load(\"reportexplorer\");  requestOpts = new actuate.RequestOptions( );  requestOpts.setVolumeProfile(\"volume\");  actuate.initialize( \"host/iportal/\", requestOpts, \"username\", \"password\", runReportExplorer);}function runReportExplorer( ) {  var explorer = new actuate.ReportExplorer(\"explorerpane\");  explorer.setFolderName( \"/Home/devSite\" );  var resultDef = \"Name|FileType|Version|VersionName|Description\";  explorer.setResultDef( resultDef.split(\"|\") );  explorer.submit( );}</script></div></body></html>";
	private final String   reportListingMD5     = createHash(reportListing);
	private final String   viewReport           = "<script type='text/javascript' language='JavaScript' src='host/iportal/jsapi'></script><script type='text/javascript'>actuate.load('viewer');var reqOps = new actuate.RequestOptions();reqOps.setVolume('volume');reqOps.setCustomParameters({});actuate.initialize('http://demo.actuate.com/iportal/', reqOps == undefined ? null : reqOps, 'username', 'password', myInit);function myInit() {viewer1 = new actuate.Viewer('container1');viewer1.setReportDesign('report');var options = new actuate.viewer.UIOptions();viewer1.setUIOptions(options);viewer1.submit();}</script><div id='container1' style='border-width: 0px; border-style: solid;'></div>";
	private final String   viewReportMD5        = createHash(viewReport);
	private final String   viewReportType       = "host/iportal/executereport.do?__locale=en_US&__vp=volumename&volume=volumename&closex=true&__executableName=reportname&__requesttype=immediate&__format=formattype";
	private final String   viewReportTypeMD5    = createHash(viewReportType);
		
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
	
	private String createHash(String toHash) {
		try {
			// Create the MessageDigest
			MD5 = MessageDigest.getInstance("MD5");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		// This will hold the final hash
		String hash = new String();
		
		// Update MD5 with the string we're going to hash
		MD5.update(toHash.getBytes());
		
		byte byteData[] = MD5.digest();
		
		StringBuffer sb = new StringBuffer();
		
        for (int i = 0; i < byteData.length; i++) {
        	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        
        // Assign the hash
        hash = sb.toString();
		
		return hash;
	}
	
	public String exportReport(String reportName, OutputType outputType) {
		String temp   = new String();
		
		if(outputType.equals(OutputType.WebViewer)) {
			if(viewReportMD5.equals(createHash(viewReport))) {
				System.out.println("Matches!");
				temp = viewReport;
				temp = temp.replaceAll("host", getHost());
				temp = temp.replaceAll("volume", getVolume());
				temp = temp.replaceAll("username", getUsername());
				temp = temp.replaceAll("password", decrypt(password));
				temp = temp.replaceAll("report", reportName);
				
				System.out.println(temp);
			}else{
				temp = "MD5 Error!";
				System.out.println("MD5 Error!");
			}
		}else if(outputType.equals(OutputType.HTML)) {
			if(viewReportTypeMD5.equals(createHash(viewReportType))) {
				String newURL = new String();

				newURL = viewReportType;
				newURL = newURL.replaceAll("host", getHost());
				newURL = newURL.replaceAll("volumename", getVolume());
				newURL = newURL.replaceAll("reportname", reportName);
				newURL = newURL.replaceAll("formattype", "html");
				newURL = newURL.replaceAll(" ", "%20");
				temp   = getContent(newURL);
			}else{
				temp = "MD5 Error!";
				System.out.println("MD5 Error!");
			}
		}else if(outputType.equals(OutputType.PDF)) {
			if(viewReportTypeMD5.equals(createHash(viewReportType))) {
				String newURL = new String();

				newURL = viewReportType;
				newURL = newURL.replaceAll("host", getHost());
				newURL = newURL.replaceAll("volumename", getVolume());
				newURL = newURL.replaceAll("reportname", reportName);
				newURL = newURL.replaceAll("formattype", "pdf");
				newURL = newURL.replaceAll(" ", "%20");
				temp   = getContent(newURL);
			}else{
				temp = "MD5 Error!";
				System.out.println("MD5 Error!");
			}
		}else if(outputType.equals(OutputType.XLS)) {
			String newURL = new String();

			newURL = viewReportType;
			newURL = newURL.replaceAll("host", getHost());
			newURL = newURL.replaceAll("volumename", getVolume());
			newURL = newURL.replaceAll("reportname", reportName);
			newURL = newURL.replaceAll("formattype", "xls");
			newURL = newURL.replaceAll(" ", "%20");
			temp   = getContent(newURL);
		}else if(outputType.equals(OutputType.XLSX)) {
			String newURL = new String();

			newURL = viewReportType;
			newURL = newURL.replaceAll("host", getHost());
			newURL = newURL.replaceAll("volumename", getVolume());
			newURL = newURL.replaceAll("reportname", reportName);
			newURL = newURL.replaceAll("formattype", "xlsx");
			newURL = newURL.replaceAll(" ", "%20");
			temp   = getContent(newURL);
		}else if(outputType.equals(OutputType.ODP)) {
			String newURL = new String();

			newURL = viewReportType;
			newURL = newURL.replaceAll("host", getHost());
			newURL = newURL.replaceAll("volumename", getVolume());
			newURL = newURL.replaceAll("reportname", reportName);
			newURL = newURL.replaceAll("formattype", "odp");
			newURL = newURL.replaceAll(" ", "%20");
			temp   = getContent(newURL);
		}else if(outputType.equals(OutputType.ODS)) {
			String newURL = new String();

			newURL = viewReportType;
			newURL = newURL.replaceAll("host", getHost());
			newURL = newURL.replaceAll("volumename", getVolume());
			newURL = newURL.replaceAll("reportname", reportName);
			newURL = newURL.replaceAll("formattype", "ods");
			newURL = newURL.replaceAll(" ", "%20");
			temp   = getContent(newURL);
		}else if(outputType.equals(OutputType.ODT)) {
			String newURL = new String();

			newURL = viewReportType;
			newURL = newURL.replaceAll("host", getHost());
			newURL = newURL.replaceAll("volumename", getVolume());
			newURL = newURL.replaceAll("reportname", reportName);
			newURL = newURL.replaceAll("formattype", "odt");
			newURL = newURL.replaceAll(" ", "%20");
			temp   = getContent(newURL);
		}else if(outputType.equals(OutputType.PS)) {
			String newURL = new String();

			newURL = viewReportType;
			newURL = newURL.replaceAll("host", getHost());
			newURL = newURL.replaceAll("volumename", getVolume());
			newURL = newURL.replaceAll("reportname", reportName);
			newURL = newURL.replaceAll("formattype", "ps");
			newURL = newURL.replaceAll(" ", "%20");
			temp   = getContent(newURL);
		}else if(outputType.equals(OutputType.PPT)) {
			String newURL = new String();

			newURL = viewReportType;
			newURL = newURL.replaceAll("host", getHost());
			newURL = newURL.replaceAll("volumename", getVolume());
			newURL = newURL.replaceAll("reportname", reportName);
			newURL = newURL.replaceAll("formattype", "ppt");
			newURL = newURL.replaceAll(" ", "%20");
			temp   = getContent(newURL);
		}else if(outputType.equals(OutputType.PPTX)) {
			String newURL = new String();

			newURL = viewReportType;
			newURL = newURL.replaceAll("host", getHost());
			newURL = newURL.replaceAll("volumename", getVolume());
			newURL = newURL.replaceAll("reportname", reportName);
			newURL = newURL.replaceAll("formattype", "pptx");
			newURL = newURL.replaceAll(" ", "%20");
			temp   = getContent(newURL);
		}else if(outputType.equals(OutputType.DOC)) {
			String newURL = new String();

			newURL = viewReportType;
			newURL = newURL.replaceAll("host", getHost());
			newURL = newURL.replaceAll("volumename", getVolume());
			newURL = newURL.replaceAll("reportname", reportName);
			newURL = newURL.replaceAll("formattype", "doc");
			newURL = newURL.replaceAll(" ", "%20");
			temp   = getContent(newURL);
		}else if(outputType.equals(OutputType.DOCX)) {
			String newURL = new String();

			newURL = viewReportType;
			newURL = newURL.replaceAll("host", getHost());
			newURL = newURL.replaceAll("volumename", getVolume());
			newURL = newURL.replaceAll("reportname", reportName);
			newURL = newURL.replaceAll("formattype", "docx");
			newURL = newURL.replaceAll(" ", "%20");
			temp   = getContent(newURL);
		}
		
		// TODO: Add more output types
		
		return temp;
	}
	
	private String getContent(String url) {
		String content = new String();
		
		try {
			URL obj = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setReadTimeout(60000);
			conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
			conn.addRequestProperty("User-Agent", "Mozilla");
			conn.addRequestProperty("Referer", "BMDK");
			
			System.out.println("Request URL..." + url);
			
			boolean redirect = false;
			
			// normally, 3xx is redirect
			int status = conn.getResponseCode();
			if (status != HttpURLConnection.HTTP_OK) {
				if (status == HttpURLConnection.HTTP_MOVED_TEMP
					|| status == HttpURLConnection.HTTP_MOVED_PERM
						|| status == HttpURLConnection.HTTP_SEE_OTHER)
				redirect = true;
			}
			
			System.out.println("Response Code ... " + status);
			
			if (redirect) {
				 
				// get redirect url from "location" header field
				String newUrl = conn.getHeaderField("Location");
				
				// get the cookie if need, for login
				String cookies = conn.getHeaderField("Set-Cookie");
				
				// open the new connnection again
				System.out.println(conn.getHeaderField(newUrl));
				conn = (HttpURLConnection) new URL(newUrl).openConnection();
				conn.setRequestProperty("Cookie", cookies);
				conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
				conn.addRequestProperty("User-Agent", "Mozilla");
				conn.addRequestProperty("Referer", "google.com");
		 
				System.out.println("Redirect to URL : " + newUrl);
		 
			}
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer html = new StringBuffer();
			
			while ((inputLine = in.readLine()) != null) {
				html.append(inputLine);
			}
			
			in.close();
			
			content = html.toString();
			System.out.println("Done");
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return content;
	}
	
	public String reportListing() {
		String temp = new String();
		
		// Make sure the JSAPI hasn't been changed
		if(reportListingMD5.equals(createHash(reportListing))) {
			temp = this.reportListing.replaceAll("username", this.decrypt(this.username));
			temp = temp.replaceAll("password", this.decrypt(this.password));
			temp = temp.replaceAll("host", this.decrypt(this.host));
			temp = temp.replaceAll("volume", this.decrypt(this.volume));
		}else{
			temp = "MD5 error!";
		}
		
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
