/* BMDK is a Mobile Development Kit that gives developers an easy way
 * to write BIRT application with Mobile platforms in mind.  It makes
 * heavy use of Actuate's JSAPI.
 * 
 * @author Actuate Corporation
 * @version 0.1 Build 1 July 2, 2014
 * 
 */

package com.actuate.developer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

public class BMDK {
	/**
	 *  @param OutputType An enum that's used to tell BMDK what output type to use.
	 */
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
	//private final String   reportListing        = "<html><head><meta http-equiv='content-type' content='text/html;charset=utf-8' /><title>Report Explorer Page</title></head><body onload='init( )'><input type='button' style='width: 150pt;' value='View Report' onclick='javascript:displayReport( )' /><input type='button' style='width: 150pt;' value='Report Listing' onclick='javascript:runReportExplorer()' /><hr width='75%' id='myHR'/><div id='explorerpane'><script type='text/javascript' language='JavaScript' src='host/iportal/jsapi'></script><script type='text/javascript' language='JavaScript'>var file = 'unknown';var divID;function generateDivID() {var tempDivID = 'myContainer' + Math.floor((Math.random() * 100000) + 1);return tempDivID;}function removeDiv(tempDivID) {var containerDiv = document.getElementById(tempDivID);if (containerDiv) {containerDiv.parentNode.removeChild(containerDiv);}}function addDiv(tempDivID) {var containerDiv = document.getElementById(tempDivID);var myHR = document.getElementById('myHR');if (!containerDiv) {	containerDiv = document.createElement('div');containerDiv.setAttribute('id',tempDivID);containerDiv.setAttribute('style','border-width: 1px; border-style: solid;');myHR.parentNode.insertBefore(containerDiv, myHR);}}function addRemoveDiv() {removeDiv(divID);divID = generateDivID();addDiv(divID);}function init() {addRemoveDiv();actuate.load('reportexplorer');actuate.load('viewer');actuate.load('dialog');requestOpts = new actuate.RequestOptions();actuate.initialize('http://demo.actuate.com/iportal', requestOpts, 'username', 'password', runReportExplorer);}function runReportExplorer() {var explorer = new actuate.ReportExplorer(divID);explorer.registerEventHandler(actuate.reportexplorer.EventConstants.ON_SELECTION_CHANGED, selectionChanged);explorer.setFolderName('/Home/devSite');var resultDef = 'Name|FileType|Version|VersionName|Description';explorer.setResultDef(resultDef.split('|'));explorer.submit();}function selectionChanged(selectedItem, pathName) {file = pathName;}function displayReport() {addRemoveDiv();var y = document.getElementById(divID),child;while (child = y.firstChild) {y.removeChild(child);}var viewer = new actuate.Viewer(divID);try {viewer.setReportName(file);viewer.submit();} catch (e) {alert('Selected file is not viewable: ' + file);runReportExplorer();}}</script></div></body></html>";
	private final String   reportListing        = "<html> <head> <meta http-equiv='content-type' content='text/html;charset=utf-8' /> <title>Report Explorer Page</title> </head> <body onload='init( )'> <div id='explorerpane'> <script type='text/javascript' language='JavaScript' src='host/iportal/jsapi'></script> <script type='text/javascript' language='JavaScript'> function init() { actuate.load('reportexplorer'); actuate.load('viewer'); actuate.load('dialog'); requestOpts = new actuate.RequestOptions(); actuate.initialize('host/iportal', requestOpts, 'username', 'password', runReportExplorer); } function runReportExplorer() { var explorer = new actuate.ReportExplorer('explorerpane'); explorer.registerEventHandler(actuate.reportexplorer.EventConstants.ON_SELECTION_CHANGED, selectionChanged); explorer.setFolderName('/Home/devSite'); var resultDef = 'Name|FileType|Version|VersionName|Description'; explorer.setResultDef(resultDef.split('|')); explorer.submit(); } function selectionChanged(selectedItem, pathName) { file = pathName; } function displayReport() { var viewer = new actuate.Viewer('explorerpane'); viewer.setReportName(file); var report = 'host/iportal/executereport.do?__executableName=' + file + '&invokeSubmit=true&__format=html'; window.location = report; } </script> </div> </body> </html>";
	private final String   downloadReport       = "<html><head><meta http-equiv='content-type' content='text/html;charset=utf-8' /><title>Report Explorer Page</title></head><body onload='init( )'><div id='explorerpane'><script type='text/javascript' language='JavaScript' src='host/iportal/jsapi'></script><script type='text/javascript' language='JavaScript'>function init() {actuate.load('reportexplorer');actuate.load('viewer');actuate.load('dialog');requestOpts = new actuate.RequestOptions();actuate.initialize('host/iportal', requestOpts, 'username', 'password', runReportExplorer);viewer.downloadReport('contenttype', null, null);}</script></div></body></html>";
	private final String   reportListingMD5     = createHash(reportListing);
	private final String   viewReport           = "<script type='text/javascript' language='JavaScript' src='host/iportal/jsapi'></script><script type='text/javascript'>actuate.load('viewer');var reqOps = new actuate.RequestOptions();reqOps.setVolume('volume');reqOps.setCustomParameters({});actuate.initialize('http://demo.actuate.com/iportal/', reqOps == undefined ? null : reqOps, 'username', 'password', myInit);function myInit() {viewer1 = new actuate.Viewer('container1');viewer1.setReportDesign('report');var options = new actuate.viewer.UIOptions();viewer1.setUIOptions(options);viewer1.submit();}</script><div id='container1' style='border-width: 0px; border-style: solid;'></div>";
	@SuppressWarnings("unused")
	private final String   viewReportMD5        = createHash(viewReport);
	private final String   viewReportType       = "<script type='text/javascript' language='JavaScript' src='host/iportal/jsapi'></script><script type='text/javascript'>var viewer1, viewer2;actuate.load('viewer');var reqOps = new actuate.RequestOptions();reqOps.setVolume('Default Volume');reqOps.setCustomParameters({});actuate.initialize('host/iportal', reqOps == undefined ? null : reqOps, 'username', 'password', myInit);function myInit() {viewer2 = new actuate.Viewer('container2');viewer2.setReportDesign('report');var options2 = new actuate.viewer.UIOptions();viewer2.setUIOptions(options2);viewer2.submit();document.getElementById('container2').innerHTML = 'Please wait, rendering report....';var testtesttest = setTimeout(myDownload, '10000');}function myDownload() {viewer2.downloadReport('format', null, null);}</script><div id='container2' style='border-width: 0px; border-style: solid;'></div>";
	private final String   viewReportTypeMD5    = createHash(viewReportType);

	/**
	 * BMDK constructor.  This will create encryption/decryption
	 * keys that persist durring the life of this Class
	 * through the live of the Class.
	 */
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
	
	/**
	 * BMDK constructor with parameters.  This allows you to create
	 * a new BMDK object and pass in username, password, host,
	 * and volume at the same time.
	 * @param username
	 * @param password
	 * @param host
	 * @param volume
	 */
	public BMDK(String username, String password, String host, String volume) {
		try {
			// Create DES key
			this.key = KeyGenerator.getInstance("DES").generateKey();
			
			// Create the ciphers
			ecipher = Cipher.getInstance("DES");
			dcipher = Cipher.getInstance("DES");
			
			// Init the ciphers with our new key
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher.init(Cipher.DECRYPT_MODE, key);
			
			// Set the parameters that were passed to us
			setUsername(username);
			setPassword(password);
			setHost(host);
			setVolume(volume);
		}catch(Exception ex) {
			ex.printStackTrace();
		}		
	}
	
	/**
	 * Creates a hash for security.  This allows us to make sure
	 * that none of the private variables have been tampered with
	 * @param toHash The string that will be hashed
	 * @return The final hash of what has been passed in
	 */
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
	
	/**
	 * Generates a report using Actuate's JSAPI and returns it in the format
	 * given via OutputType
	 * @param reportName Name of the report to be exported.  This must be the full path.  IE /Home/devSite/test.rptdesign
	 * @param outputType The format the report needs to be rendered in.  IE xls, pdf, doc, etc.
	 * @return Return's the report as a string for the developer to decide how to handle the contents
	 */
	public String exportReport(String reportName, OutputType outputType) {
		// TODO: Implement login once it's been created.
		// Login() will execute JSAPI to login to the users
		// iHub, then store the authenticated session
		// if the login has timed out then automatically
		// log back in.
		
		String temp   = new String();
		
		if(viewReportTypeMD5.equals(createHash(viewReportType))) {			
			switch(outputType) {
			case WebViewer:
				temp = viewReport;
				temp = temp.replaceAll("host", getHost());
				temp = temp.replaceAll("volume", getVolume());
				temp = temp.replaceAll("username", getUsername());
				temp = temp.replaceAll("password", decrypt(password));
				temp = temp.replaceAll("report", reportName);
				System.out.println(temp);
				break;
			case HTML:
				temp   = getContent(regexJsapi("html", reportName));
				break;
			case PDF:
				//temp   = getContent(regexJsapi("pdf", reportName));
				temp = "<script type='text/javascript' language='JavaScript' src='http://demo.actuate.com/iportal/jsapi'></script><script type='text/javascript'>var viewer2;actuate.load('viewer');var reqOps = new actuate.RequestOptions();reqOps.setVolume('Default Volume');reqOps.setCustomParameters({});actuate.initialize('http://demo.actuate.com/iportal/', reqOps == undefined ? null : reqOps, null, null, myInit);function myInit() {viewer2 = new actuate.Viewer('container2');viewer2.setReportDesign('/Home/devSite/test.rptdesign;1');var options2 = new actuate.viewer.UIOptions();viewer2.setUIOptions(options2);document.getElementById('container2').innerHTML = 'Please wait, rendering report....';viewer2.submit();var testtesttest = setTimeout(myDownload, '10000');}function myDownload() {viewer2.downloadReport('pdf', null, null);}</script><div id='container2' style='border-width: 0px; border-style: solid;'></div>";
				break;
			case XLS:
				//temp   = getContent(regexJsapi("xls", reportName));
				temp = "<script type='text/javascript' language='JavaScript' src='http://demo.actuate.com/iportal/jsapi'></script><script type='text/javascript'>var viewer2;actuate.load('viewer');var reqOps = new actuate.RequestOptions();reqOps.setVolume('Default Volume');reqOps.setCustomParameters({});actuate.initialize('http://demo.actuate.com/iportal/', reqOps == undefined ? null : reqOps, null, null, myInit);function myInit() {viewer2 = new actuate.Viewer('container2');viewer2.setReportDesign('/Home/devSite/test.rptdesign;1');var options2 = new actuate.viewer.UIOptions();viewer2.setUIOptions(options2);document.getElementById('container2').innerHTML = 'Please wait, rendering report....';viewer2.submit();var testtesttest = setTimeout(myDownload, '10000');}function myDownload() {viewer2.downloadReport('xls', null, null);}</script><div id='container2' style='border-width: 0px; border-style: solid;'></div>";
				break;
			case XLSX:
				//temp   = getContent(regexJsapi("xlsx", reportName));
				temp = "<script type='text/javascript' language='JavaScript' src='http://demo.actuate.com/iportal/jsapi'></script><script type='text/javascript'>var viewer2;actuate.load('viewer');var reqOps = new actuate.RequestOptions();reqOps.setVolume('Default Volume');reqOps.setCustomParameters({});actuate.initialize('http://demo.actuate.com/iportal/', reqOps == undefined ? null : reqOps, null, null, myInit);function myInit() {viewer2 = new actuate.Viewer('container2');viewer2.setReportDesign('/Home/devSite/test.rptdesign;1');var options2 = new actuate.viewer.UIOptions();viewer2.setUIOptions(options2);document.getElementById('container2').innerHTML = 'Please wait, rendering report....';viewer2.submit();var testtesttest = setTimeout(myDownload, '10000');}function myDownload() {viewer2.downloadReport('xlsx', null, null);}</script><div id='container2' style='border-width: 0px; border-style: solid;'></div>";
				break;
			case ODP:
				//temp   = getContent(regexJsapi("odp", reportName));
				temp = "<script type='text/javascript' language='JavaScript' src='http://demo.actuate.com/iportal/jsapi'></script><script type='text/javascript'>var viewer2;actuate.load('viewer');var reqOps = new actuate.RequestOptions();reqOps.setVolume('Default Volume');reqOps.setCustomParameters({});actuate.initialize('http://demo.actuate.com/iportal/', reqOps == undefined ? null : reqOps, null, null, myInit);function myInit() {viewer2 = new actuate.Viewer('container2');viewer2.setReportDesign('/Home/devSite/test.rptdesign;1');var options2 = new actuate.viewer.UIOptions();viewer2.setUIOptions(options2);document.getElementById('container2').innerHTML = 'Please wait, rendering report....';viewer2.submit();var testtesttest = setTimeout(myDownload, '10000');}function myDownload() {viewer2.downloadReport('odp', null, null);}</script><div id='container2' style='border-width: 0px; border-style: solid;'></div>";
				break;
			case ODS:
				//temp   = getContent(regexJsapi("ods", reportName));
				temp = "<script type='text/javascript' language='JavaScript' src='http://demo.actuate.com/iportal/jsapi'></script><script type='text/javascript'>var viewer2;actuate.load('viewer');var reqOps = new actuate.RequestOptions();reqOps.setVolume('Default Volume');reqOps.setCustomParameters({});actuate.initialize('http://demo.actuate.com/iportal/', reqOps == undefined ? null : reqOps, null, null, myInit);function myInit() {viewer2 = new actuate.Viewer('container2');viewer2.setReportDesign('/Home/devSite/test.rptdesign;1');var options2 = new actuate.viewer.UIOptions();viewer2.setUIOptions(options2);document.getElementById('container2').innerHTML = 'Please wait, rendering report....';viewer2.submit();var testtesttest = setTimeout(myDownload, '10000');}function myDownload() {viewer2.downloadReport('ods', null, null);}</script><div id='container2' style='border-width: 0px; border-style: solid;'></div>";
				break;
			case ODT:
				//temp   = getContent(regexJsapi("odt", reportName));
				temp = "<script type='text/javascript' language='JavaScript' src='http://demo.actuate.com/iportal/jsapi'></script><script type='text/javascript'>var viewer2;actuate.load('viewer');var reqOps = new actuate.RequestOptions();reqOps.setVolume('Default Volume');reqOps.setCustomParameters({});actuate.initialize('http://demo.actuate.com/iportal/', reqOps == undefined ? null : reqOps, null, null, myInit);function myInit() {viewer2 = new actuate.Viewer('container2');viewer2.setReportDesign('/Home/devSite/test.rptdesign;1');var options2 = new actuate.viewer.UIOptions();viewer2.setUIOptions(options2);document.getElementById('container2').innerHTML = 'Please wait, rendering report....';viewer2.submit();var testtesttest = setTimeout(myDownload, '10000');}function myDownload() {viewer2.downloadReport('odt', null, null);}</script><div id='container2' style='border-width: 0px; border-style: solid;'></div>";
				break;
			case PS:
				//temp   = getContent(regexJsapi("ps", reportName));
				temp = "<script type='text/javascript' language='JavaScript' src='http://demo.actuate.com/iportal/jsapi'></script><script type='text/javascript'>var viewer2;actuate.load('viewer');var reqOps = new actuate.RequestOptions();reqOps.setVolume('Default Volume');reqOps.setCustomParameters({});actuate.initialize('http://demo.actuate.com/iportal/', reqOps == undefined ? null : reqOps, null, null, myInit);function myInit() {viewer2 = new actuate.Viewer('container2');viewer2.setReportDesign('/Home/devSite/test.rptdesign;1');var options2 = new actuate.viewer.UIOptions();viewer2.setUIOptions(options2);document.getElementById('container2').innerHTML = 'Please wait, rendering report....';viewer2.submit();var testtesttest = setTimeout(myDownload, '10000');}function myDownload() {viewer2.downloadReport('ps', null, null);}</script><div id='container2' style='border-width: 0px; border-style: solid;'></div>";
				break;
			case PPT:
				//temp   = getContent(regexJsapi("ppt", reportName));
				temp = "<script type='text/javascript' language='JavaScript' src='http://demo.actuate.com/iportal/jsapi'></script><script type='text/javascript'>var viewer2;actuate.load('viewer');var reqOps = new actuate.RequestOptions();reqOps.setVolume('Default Volume');reqOps.setCustomParameters({});actuate.initialize('http://demo.actuate.com/iportal/', reqOps == undefined ? null : reqOps, null, null, myInit);function myInit() {viewer2 = new actuate.Viewer('container2');viewer2.setReportDesign('/Home/devSite/test.rptdesign;1');var options2 = new actuate.viewer.UIOptions();viewer2.setUIOptions(options2);document.getElementById('container2').innerHTML = 'Please wait, rendering report....';viewer2.submit();var testtesttest = setTimeout(myDownload, '10000');}function myDownload() {viewer2.downloadReport('ppt', null, null);}</script><div id='container2' style='border-width: 0px; border-style: solid;'></div>";
				break;
			case PPTX:
				//temp   = getContent(regexJsapi("pptx", reportName));
				temp = "<script type='text/javascript' language='JavaScript' src='http://demo.actuate.com/iportal/jsapi'></script><script type='text/javascript'>var viewer2;actuate.load('viewer');var reqOps = new actuate.RequestOptions();reqOps.setVolume('Default Volume');reqOps.setCustomParameters({});actuate.initialize('http://demo.actuate.com/iportal/', reqOps == undefined ? null : reqOps, null, null, myInit);function myInit() {viewer2 = new actuate.Viewer('container2');viewer2.setReportDesign('/Home/devSite/test.rptdesign;1');var options2 = new actuate.viewer.UIOptions();viewer2.setUIOptions(options2);document.getElementById('container2').innerHTML = 'Please wait, rendering report....';viewer2.submit();var testtesttest = setTimeout(myDownload, '10000');}function myDownload() {viewer2.downloadReport('pptx', null, null);}</script><div id='container2' style='border-width: 0px; border-style: solid;'></div>";
				break;
			case DOC:
				//temp   = getContent(regexJsapi("doc", reportName));
				temp = "<script type='text/javascript' language='JavaScript' src='http://demo.actuate.com/iportal/jsapi'></script><script type='text/javascript'>var viewer2;actuate.load('viewer');var reqOps = new actuate.RequestOptions();reqOps.setVolume('Default Volume');reqOps.setCustomParameters({});actuate.initialize('http://demo.actuate.com/iportal/', reqOps == undefined ? null : reqOps, null, null, myInit);function myInit() {viewer2 = new actuate.Viewer('container2');viewer2.setReportDesign('/Home/devSite/test.rptdesign;1');var options2 = new actuate.viewer.UIOptions();viewer2.setUIOptions(options2);document.getElementById('container2').innerHTML = 'Please wait, rendering report....';viewer2.submit();var testtesttest = setTimeout(myDownload, '10000');}function myDownload() {viewer2.downloadReport('doc', null, null);}</script><div id='container2' style='border-width: 0px; border-style: solid;'></div>";
				break;
			case DOCX:
				//temp   = getContent(regexJsapi("docx", reportName));
				temp = "<script type='text/javascript' language='JavaScript' src='http://demo.actuate.com/iportal/jsapi'></script><script type='text/javascript'>var viewer2;actuate.load('viewer');var reqOps = new actuate.RequestOptions();reqOps.setVolume('Default Volume');reqOps.setCustomParameters({});actuate.initialize('http://demo.actuate.com/iportal/', reqOps == undefined ? null : reqOps, null, null, myInit);function myInit() {viewer2 = new actuate.Viewer('container2');viewer2.setReportDesign('/Home/devSite/test.rptdesign;1');var options2 = new actuate.viewer.UIOptions();viewer2.setUIOptions(options2);document.getElementById('container2').innerHTML = 'Please wait, rendering report....';viewer2.submit();var testtesttest = setTimeout(myDownload, '10000');}function myDownload() {viewer2.downloadReport('docx', null, null);}</script><div id='container2' style='border-width: 0px; border-style: solid;'></div>";
				break;
			default:
				
			}
		}else{
			temp = "MD5 Error!";
		}
		
		// TODO: Add spudsoft emitter, decide what to call it
		// in the enum
		
		return temp;
	}

	//
	private String regexJsapi(String format, String reportName) {
		String newURL = new String();
		String temp   = new String();
		
		newURL = downloadReport;
		newURL = newURL.replaceAll("host", getHost());
		newURL = newURL.replaceAll("volumename", getVolume());
		newURL = newURL.replaceAll("reportname", reportName);
		newURL = newURL.replaceAll("formattype", format);
		newURL = newURL.replaceAll(" ", "%20");
		newURL = newURL.replaceAll("contenttype", format);
		temp   = getContent(newURL);
		//newURL = newURL.replaceAll(" ", "%20");
		//temp   = getContent(newURL);
		return temp;
	}
	
	/**
	 * This will login to the iHub and save the authenticated session in methods
	 * such as exportReport()
	 * @return Authenticated session
	 */
	@SuppressWarnings("unused")
	private Object login() {
		// TODO: Use the JSAPI to login and store the
		// authenticated session to add security to
		// exportReport()
		Object session = null;
		
		return session;
	}
	
	/**
	 * Grabs the report in whatever format has been specified
	 * @param url URL that forces the iHub to generate the report
	 * @return The report in it's specified format
	 */
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
	
	/**
	 * 
	 * @return Report listing in the specified volume as HTML string
	 */
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
	
	/**
	 * Takes a string, encrpyts it, then returns the encrypted string
	 * @param password Text to be encrypted
	 * @return Encrypted text
	 */
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
	
	/**
	 * Decrypts the string that has been passed to it
	 * @param password String to decrypt
	 * @return Decrypted string
	 */
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
	
	/**
	 * Sets the username
	 * @param username Username
	 */
	public void setUsername(String username) {
		String temp = this.encrypt(username);
		this.username = temp;
	}
	
	/**
	 * Sets the password
	 * @param password Password
	 */
	public void setPassword(String password) {		
		String temp = this.encrypt(password);
		this.password = temp;
	}
	
	/**
	 * Sets the host
	 * @param host Hostname
	 */
	public void setHost(String host) {
		String temp = this.encrypt(host);
		this.host = temp;
	}
	
	/**
	 * Sets the volume
	 * @param volume Volume Name
	 */
	public void setVolume(String volume) {
		String temp = this.encrypt(volume);
		this.volume = temp;
	}
	
	/**
	 * Gets the username
	 * @return Username
	 */
	public String getUsername() {
		return this.decrypt(this.username);
	}
	
	/**
	 * Check's to see if the password is set
	 * @return true/false
	 */
	public boolean getPassword() {
		if(this.password == null) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Gets the hostname
	 * @return Hostname
	 */
	public String getHost() {
		return this.decrypt(this.host);
	}
	
	/**
	 * Gets the volume name
	 * @return Volume Name
	 */
	public String getVolume() {
		return this.decrypt(this.volume);
	}
}