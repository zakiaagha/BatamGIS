package com.ta.polibatam.gisbatam;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.SearchView;


public class MainActivity extends ActionBarActivity {
	private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        webView = (WebView) findViewById(R.id.web_view);
        openBrowser(); 
    }
	
	
	//karena ada method static yang diakses oleh method non-static (openBrowser)
    @SuppressLint("SetJavaScriptEnabled") 
    private void openBrowser()
    {
        //memanggil URL, /// berguna untuk menandakan bahwa file yang diakses
        //sedangkan android_asset merefers ke folder assets yang ada di
        //proyek androidmu
        String url = "http://10.0.2.2:1993/geoserver/Batam/wms?service=WMS&version=1.1.0&request=GetMap&layers=Batam&styles=&bbox=103.88943658903702,0.9680605034743621,104.15031077055329,1.1995421846569343&width=300&height=432&srs=EPSG:4326&format=application/openlayers";
 
        //Menginstantiasi webchrome client baru, buat gaya-gayaan aja B)
        WebChromeClient wcc = new WebChromeClient();
        webView.setWebChromeClient(wcc);
        //mengaktifkan javascript, kalo nggak aktif... TRY IT!
        webView.getSettings().setJavaScriptEnabled(true);
        //mengaktifkan built in zoom controls
        webView.getSettings().setBuiltInZoomControls(true);
        //bagian ini akan dijelaskan
        webView.getSettings().setGeolocationEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        //meload URL
        webView.setWebChromeClient(new WebChromeClient() {
        	public void onGeolocationPermissionsShowPrompt(String origin, android.webkit.GeolocationPermissions.Callback callback) {
        	     callback.invoke(origin, true, false);
        	}
        	});
        webView.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	/**
	 * On selecting action bar icons
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Take appropriate action for each action item click
		switch (item.getItemId()) {
		case R.id.action_search:
			// search action
			return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
