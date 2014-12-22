package com.ta.polibatam.batamgis;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class GeoLocationActivity  extends ActionBarActivity {
	
	private WebView webView;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocation);
        webView = (WebView) findViewById(R.id.web_view);
        ActionBar actionBar = getActionBar();
                
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
        openBrowser();	

    }
	
    @SuppressLint("SetJavaScriptEnabled") 
    private void openBrowser()
    {
        
        String url = "file:///android_asset/www/maps.html";
 
        //inisialisasi webchrome client
        WebChromeClient wcc = new WebChromeClient();
        webView.setWebChromeClient(wcc);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //for geolocation
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setGeolocationEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
        	public void onGeolocationPermissionsShowPrompt(String origin, android.webkit.GeolocationPermissions.Callback callback) {
        	     callback.invoke(origin, true, false);
        	}
        	});
        // HTML5 API flags
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
       
        //load url
        webView.loadUrl(url);
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; goto parent activity.
	        	SearchAction();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
    
    private void SearchAction(){
		Intent i = new Intent(GeoLocationActivity.this, SearchActivity.class);
		startActivity(i);
	}
}
