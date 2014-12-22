package com.ta.polibatam.batamgis;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MainActivity extends ActionBarActivity {

    private WebView webView;
    private String LOG_TAG = "MainActivity";

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.web_view);
        getActionBar();
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
        //override the web client to open all links in the same webview
        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
        
        //Injects the supplied Java object into this WebView. The object is injected into the 
        //JavaScript context of the main frame, using the supplied name. This allows the 
        //Java object's public methods to be accessed from JavaScript.
        webView.addJavascriptInterface(new JavaScriptInterface(this), "Android");
        //load url
        webView.loadUrl(url);
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
		return true;
     
    }
    
    private void SearchAction(){
		Intent i = new Intent(MainActivity.this, SearchActivity.class);
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
    
  //customize your web view client to open links from your own site in the 
    //same web view otherwise just open the default browser activity with the URL
    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (Uri.parse(url).getHost().equals("localhost")) {
                return false;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }
     
    private class MyWebChromeClient extends WebChromeClient {
        
    	  //display alert message in Web View
    	  @Override
    	     public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
    	         Log.d(LOG_TAG, message);
    	         new AlertDialog.Builder(view.getContext())
    	          .setMessage(message).setCancelable(true).show();
    	         result.confirm();
    	         return true;
    	     }
    	 
    	 }
    public class JavaScriptInterface {
        Context mContext;
    
        // Instantiate the interface and set the context 
        JavaScriptInterface(Context c) {
            mContext = c;
        }
         
        //using Javascript to call the finish activity
        public void closeMyActivity() {
            finish();
        }
         
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}
