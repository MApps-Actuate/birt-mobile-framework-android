package com.actuate.developer.bmdkmobile;

import com.actuate.developer.BMDK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {
	
	private String jsReportName = null; 
	
	@JavascriptInterface
	public void receiveValueFromJs(String str) {
		Toast.makeText(this, "Recieved value from JS: " + str, Toast.LENGTH_SHORT).show();
	}
	
	@JavascriptInterface
	public void selectionChange(String reportName) {
		if(!reportName.contains("rptdesign")) { 
			jsReportName = null;
		}else if(jsReportName == null) {
			jsReportName = reportName;
			Toast.makeText(this, "Tap again to open " + jsReportName + " or use the menu", Toast.LENGTH_LONG).show();
		}else if(!jsReportName.equals(reportName)) {
			Toast.makeText(this, "Tap again to open " + jsReportName + " or use the menu", Toast.LENGTH_LONG).show();
			jsReportName = reportName;
		}else if(jsReportName.equals(reportName)) {
			Toast.makeText(this, "Opening " + jsReportName + "...", Toast.LENGTH_LONG).show();
			myWebView.loadUrl("javascript:displayReport()");
		}
	}
	
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	
	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;
	
	private BMDK    bmdk = new BMDK("kclark", "Connor14", "http://demo.actuate.com", "Default Volume");
	private WebView myWebView;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
		// Create the webiview and adjust settings as needed
		myWebView = (WebView) findViewById(R.id.webview);
		
		// Enable JavaScript
		myWebView.getSettings().setJavaScriptEnabled(true);
		
		myWebView.addJavascriptInterface(this, "MyAndroid");
		
				
		// Zoom controls
		myWebView.getSettings().setUseWideViewPort(true);
		myWebView.getSettings().setBuiltInZoomControls(true);
		myWebView.getSettings().setDisplayZoomControls(true);
		myWebView.setVerticalScrollBarEnabled(true);
		myWebView.setHorizontalScrollBarEnabled(true);
		
		// Force links to open in the same webview
		myWebView.setWebViewClient(new WebViewClient() {
		       @Override
		       public boolean shouldOverrideUrlLoading(WebView view, String url) {
		           view.loadUrl(url);
		           return false;
		       }
		});

		myWebView.loadDataWithBaseURL("http://demo.actuate.com/iportal/jsapi", bmdk.reportListing(), "text/html", "UTF-8", null);
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {		
		
		switch(position) {
		case 0:
			if(myWebView != null) {
				myWebView.loadDataWithBaseURL("http://demo.actuate.com/iportal/jsapi", bmdk.reportListing(), "text/html", "UTF-8", null);
			}
			break;
		case 1:
			if(myWebView != null) {
				myWebView.loadUrl("javascript:displayReport()");
			}
			break;
		case 2:
			if(myWebView != null) {
				Log.w("DOWNLOAD", "Download cliked!");
				myWebView.loadUrl("javascript:download()");
			}
			break;
		}
		
		Log.w("Pressed", Integer.toString(position));
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
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
	
	@Override
	public boolean onPreferenceClick(Preference preference) {
		return false;
	}
}
