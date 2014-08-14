package com.actuate.developer.bmdkmobileexample;

import com.actuate.developer.BMDK;
import com.actuate.developer.BMDK.OutputType;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Create our BMDK and set our options
		//String username, String password, String host, String volume
		
		BMDK bmdk = new BMDK("kclark", "Connor14", "http://demo.actuate.com", "Default Volume");
		
		//BMDK bmdk = new BMDK(/*params*/);
		//bmdk.setHost("http://demo.actuate.com");
		//bmdk.setUsername("kclark");
		//bmdk.setPassword("Connor14");
		//bmdk.setVolume("Default Volume");
		
		// Create the webiview and adjust settings as needed
		WebView myWebView = (WebView) findViewById(R.id.webview);
		myWebView.getSettings().setAllowFileAccess(true);
		myWebView.getSettings().setJavaScriptEnabled(true);
		myWebView.getSettings().setSupportMultipleWindows(true);
		myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		
		//Working
		//myWebView.loadDataWithBaseURL("http://demo.actuate.com/iportal/jsapi", bmdk.reportListing(), "text/html", "UTF-8", null);
		
		//Must have full report path
		myWebView.loadDataWithBaseURL("http://demo.actuate.com/iportal/jsapi", bmdk.exportReport("/Home/devSite/test.rptdesign", OutputType.DOC) , "text/html", "UTF-8", null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
