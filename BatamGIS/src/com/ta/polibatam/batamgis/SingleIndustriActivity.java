package com.ta.polibatam.batamgis;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.ta.polibatam.batamgis.adapter.CustomHttpClient;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SingleIndustriActivity  extends Activity{
	private final String USER_AGENT = "Mozilla/5.0";
	// JSON node keys
	private static final String TAG_NAMA = "nama";
	private static final String TAG_ALAMAT = "alamat";
	private static final String TAG_LAT = "lat";
	private static final String TAG_LNG = "lng";
	private static final String TAG_KATEGORI = "kategori";
	private static final String TAG_TELP = "telp";
	private static final String TAG_IMAGE = "image";
	String urlpic = "http://server.jelastic.skali.net/gisserver/img/";
	Button btnpeta;
	private String tes;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_industri);
        ActionBar actionBar = getActionBar();
        
        actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
        
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String nama = in.getStringExtra(TAG_NAMA);
        String alamat = in.getStringExtra(TAG_ALAMAT);
        String lat = in.getStringExtra(TAG_LAT);
        String lng = in.getStringExtra(TAG_LNG);
        String kategori = in.getStringExtra(TAG_KATEGORI);
        String telp = in.getStringExtra(TAG_TELP);
        String image = in.getStringExtra(TAG_IMAGE);
        
        // Displaying all values on the screen
        TextView lblNama = (TextView) findViewById(R.id.name_label);
        TextView lblAlamat = (TextView) findViewById(R.id.alamat_label);
        final TextView lblLat = (TextView) findViewById(R.id.lat_label);
        final TextView lblLng = (TextView) findViewById(R.id.lng_label);
        TextView lblKat = (TextView) findViewById(R.id.kategori_label);
        TextView lblTelp = (TextView) findViewById(R.id.telp_label);
        ImageView lblImage = (ImageView) findViewById(R.id.image_view);
        
        lblNama.setText(nama);
        lblAlamat.setText(alamat);
        lblLat.setText(lat);
        lblLng.setText(Double.valueOf(lng).toString());
        lblKat.setText(kategori);
        lblTelp.setText(telp);
        new DownloadImageTask(lblImage).execute(urlpic+image);
        
        btnpeta = (Button) findViewById(R.id.btn_view_map);
        btnpeta.setOnClickListener(new OnClickListener(){
        	// getting intent data
            Intent in = getIntent();
            
            // Get JSON values from previous intent
            String v_latitude = in.getStringExtra(TAG_LAT);
            String v_longitude = in.getStringExtra(TAG_LNG);
            
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("latitude", v_latitude));
				postParameters.add(new BasicNameValuePair("longitude", v_longitude));
				String response="";
				
	            try {
	                
	                response = CustomHttpClient.executeHttpPost("http://10.0.2.2/gisserver/tes.php?latitude="+v_latitude, postParameters);
	                
	                String res = response.toString();
	           
	               res = res.trim();
	              
		            
		            res = res.replaceAll("\n+","");
		           Toast.makeText(SingleIndustriActivity.this, res, Toast.LENGTH_SHORT).show();
		 } catch (Exception e) {
        	 e.getMessage();  
	            
         }
			
			}

			private void Exception() {
				// TODO Auto-generated method stub
				
			}
						        	
       });
       
    }
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            // app icon in action bar clicked; goto parent activity.
	            this.finish();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
    
    
	public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				Bitmap bmp2 = Bitmap.createScaledBitmap(result, 600, 600, true);
				bmImage.setImageBitmap(bmp2);
			}

		}
	}

	private void geolocation(){
		Intent i = new Intent(SingleIndustriActivity.this, GeoLocationActivity.class);
		startActivity(i);
	}
}
	 
