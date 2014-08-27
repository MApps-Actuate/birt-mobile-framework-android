package com.actuate.developer.bmdkmobileexample;

import com.actuate.developer.BMDK;
import com.actuate.developer.BMDK.OutputType;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends Activity {	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Create our BMDK and set our options
		final BMDK bmdk = new BMDK("kclark", "Connor14", "http://demo.actuate.com", "Default Volume");
		
		// Create the webiview and adjust settings as needed
		final WebView myWebView = (WebView) findViewById(R.id.webview);
		
		// Enable JavaScript
		myWebView.getSettings().setJavaScriptEnabled(true);
		
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
		
		final Button btnView = (Button) findViewById(R.id.btnView);
		final Button btnList = (Button) findViewById(R.id.btnList);
		
		btnView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				myWebView.loadUrl("javascript:displayReport()");
			}
		});
		
		btnList.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				myWebView.loadDataWithBaseURL("http://demo.actuate.com/iportal/jsapi", bmdk.reportListing(), "text/html", "UTF-8", null);
			}
		});

		
		myWebView.loadDataWithBaseURL("http://demo.actuate.com/iportal/jsapi", bmdk.reportListing(), "text/html", "UTF-8", null);
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
