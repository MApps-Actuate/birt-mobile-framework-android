package com.actuate.developer.bmdkmobile;

import com.actuate.developer.BMDK;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	public class JavaScriptInterface extends Activity {
		Context mContext;
		
		JavaScriptInterface(Context c) {
			mContext = c;
		}
		
		@JavascriptInterface
		public void receiveValueFromJs(String str) {
			Log.w("TOAST", "Make toast!");
			Toast.makeText(mContext, "Recieved value from JS: " + str, Toast.LENGTH_SHORT).show();
		}
		
		@JavascriptInterface
		public void selectionChange() {
			Log.w("TOAST", "Make toast!");
			Toast.makeText(mContext, "Report selection change!", Toast.LENGTH_SHORT).show();
			
			super.findViewById(R.id.drawer_layout).
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
		myWebView.addJavascriptInterface(new JavaScriptInterface(this), "MyAndroid");
				
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
		// update the main content by replacing fragments
		//FragmentManager fragmentManager = getFragmentManager();
		//fragmentManager
		//		.beginTransaction()
		//		.replace(R.id.container,
		//				PlaceholderFragment.newInstance(position + 1)).commit();
		
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
		Log.w("Prefs", "prefs clicked");
		return false;
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}
		
		

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}

}
