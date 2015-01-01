package com.ta.polibatam.batamgis;

import java.io.InputStream;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleIndustriActivity extends Activity {
	// JSON node keys
	private static final String TAG_NAMA = "nama";
	private static final String TAG_ALAMAT = "alamat";
	private static final String TAG_LAT = "lat";
	private static final String TAG_LNG = "lng";
	private static final String TAG_KATEGORI = "kategori";
	@SuppressWarnings({ })
	private static final String TAG_IMAGE = "image";
	String urlpic = "http://10.0.2.2/gisserver/img/";
	Button btnpeta;
	
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
        final String lat = in.getStringExtra(TAG_LAT);
        final String lng = in.getStringExtra(TAG_LNG);
        String kategori = in.getStringExtra(TAG_KATEGORI);
        String image = in.getStringExtra(TAG_IMAGE);
        
        // Displaying all values on the screen
        TextView lblNama = (TextView) findViewById(R.id.name_label);
        TextView lblAlamat = (TextView) findViewById(R.id.alamat_label);
        TextView lblLat = (TextView) findViewById(R.id.lat_label);
        TextView lblLng = (TextView) findViewById(R.id.lng_label);
        TextView lblKat = (TextView) findViewById(R.id.kategori_label);
        ImageView lblImage = (ImageView) findViewById(R.id.image_view);
        
        lblNama.setText(nama);
        lblAlamat.setText(alamat);
        lblLat.setText(lat);
        lblLng.setText(lng);
        lblKat.setText(kategori);
        if ((getResources().getConfiguration().screenLayout &      
        		Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {     
        	FrameLayout.LayoutParams paramImage = new FrameLayout.LayoutParams(400,400, Gravity.CENTER);
            lblImage.setLayoutParams(paramImage);

        }
        else if ((getResources().getConfiguration().screenLayout &      
        		Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {     
        	FrameLayout.LayoutParams paramImage = new FrameLayout.LayoutParams(300,300, Gravity.CENTER);
            lblImage.setLayoutParams(paramImage);
        } 
        else if ((getResources().getConfiguration().screenLayout &      
        		Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {     
        	FrameLayout.LayoutParams paramImage = new FrameLayout.LayoutParams(426, 320, Gravity.CENTER);
            lblImage.setLayoutParams(paramImage);
        }
        else {
        	FrameLayout.LayoutParams paramImage = new FrameLayout.LayoutParams(400, 400,  Gravity.CENTER);
        	lblImage.setLayoutParams(paramImage);
        }
        
        
        new DownloadImageTask(lblImage).execute(urlpic+image);
        
        btnpeta = (Button) findViewById(R.id.btn_view_map);
        btnpeta.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String lat = ((TextView) findViewById(R.id.lat_label))
						.getText().toString();
				String lng = ((TextView) findViewById(R.id.lng_label))
						.getText().toString();
				Intent in = new Intent(SingleIndustriActivity.this, GeoLocationActivity.class);
				in.putExtra(TAG_LAT, lat);
				in.putExtra(TAG_LNG, lng);
				startActivity(in);
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

	
}
	 
