package com.ta.polibatam.batamgis;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class GeoLocationActivity extends ActionBarActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_geolocation);
		@SuppressWarnings("unused")
		ActionBar actionBar = getActionBar();
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onStart()
	{
		super.onStart();
		
		WebView webView = (WebView) findViewById(R.id.web_view);
		
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient());
		
		webView.loadUrl("file:///android_asset/www/index.html");
		
		Java2JSAgent jsAgent = new Java2JSAgent();
		
		webView.addJavascriptInterface(jsAgent, "java2JSAgentVar");
	}
	
	private void SearchAction(){
		Intent i = new Intent(GeoLocationActivity.this, SearchActivity.class);
		startActivity(i);
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
        	SearchAction();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
}



class Java2JSAgent extends GeoLocationActivity
{

	public String lat ="1.058611";
	public String lng = "104.037222";
		
    @JavascriptInterface 
    public String getNameLat()
    {
        return lat;
    }
    @JavascriptInterface
    public String getNameLng()
    {
        return lng;
    }
}