package com.ta.polibatam.batamgis;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.ta.polibatam.batamgis.adapter.ServiceHandler;

public class SearchActivity extends ListActivity {

	private ProgressDialog pDialog;

	// URL to get contacts JSON
	private static String url = "http://10.0.2.2/gisserver/detail-info.php";

	// JSON Node names
	private static final String TAG_INDUSTRI = "industri";
	private static final String TAG_NAMA = "nama";
	private static final String TAG_ALAMAT = "alamat";
	private static final String TAG_LAT = "lat";
	private static final String TAG_LNG = "lng";
	private static final String TAG_KATEGORI = "kategori";
	private static final String TAG_TELP = "telp";
	private static final String TAG_IMAGE = "image";
	
	EditText mEditText;
    ListView lv;
    
	// contacts JSONArray
	JSONArray industri = null;

	// Hashmap for ListView
	ArrayList<HashMap<String, String>> industriList;

	private EditText inputSearch;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_industri);
		ActionBar actionBar = getActionBar();

		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		industriList = new ArrayList<HashMap<String, String>>();
		inputSearch = (EditText) findViewById(R.id.inputSearch);
		
		lv = getListView();
        // Listview on item click listener
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String nama = ((TextView) view.findViewById(R.id.txt_nama))
						.getText().toString();
				String alamat = ((TextView) view.findViewById(R.id.txt_alamat))
						.getText().toString();
				String lat = ((TextView) view.findViewById(R.id.txt_lat))
						.getText().toString();
				String lng = ((TextView) view.findViewById(R.id.txt_lng))
						.getText().toString();
				String kategori = ((TextView) view.findViewById(R.id.txt_kategori))
						.getText().toString();
				String telp = ((TextView) view.findViewById(R.id.txt_telp))
						.getText().toString();
				String image = ((TextView) view.findViewById(R.id.txt_image))
						.getText().toString();

				// Starting single industri activity
				Intent in = new Intent(getApplicationContext(),
						SingleIndustriActivity.class);
				in.putExtra(TAG_NAMA, nama);
				in.putExtra(TAG_ALAMAT, alamat);
				in.putExtra(TAG_LAT, lat);
				in.putExtra(TAG_LNG, lng);
				in.putExtra(TAG_KATEGORI, kategori);
				in.putExtra(TAG_TELP, telp);
				in.putExtra(TAG_IMAGE, image);
				startActivity(in);

			}
		});
		
		       
		// Calling async task to get json
		new GetIndustri().execute();
		/**
         * Enabling Search Filter
         * */
		inputSearch.addTextChangedListener(new TextWatcher() {
	        public void afterTextChanged(Editable s) {
	            if(s.length()==0){
	                lv.clearTextFilter();
	            }
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){
	        }
	        public void onTextChanged(CharSequence s, int start, int before, int count)
	        {
	            lv.setTextFilterEnabled(true);
	            lv.setFilterText(s.toString());
	        }

	    });
		
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetIndustri extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(SearchActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					
					// Getting JSON Array node
					industri = jsonObj.getJSONArray(TAG_INDUSTRI);

					// looping through All Contacts
					for (int i = 0; i < industri.length(); i++) {
						JSONObject c = industri.getJSONObject(i);
						
						String nama = c.getString(TAG_NAMA);
						String alamat = c.getString(TAG_ALAMAT);
						String lat = c.getString(TAG_LAT);
						String lng = c.getString(TAG_LNG);
						String kategori = c.getString(TAG_KATEGORI);
						String telp = c.getString(TAG_TELP);
						String image = c.getString(TAG_IMAGE);

						// tmp hashmap for single contact
						HashMap<String, String> industri = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						industri.put(TAG_NAMA, nama);
						industri.put(TAG_ALAMAT, alamat);
						industri.put(TAG_LAT, lat);
						industri.put(TAG_LNG, lng);
						industri.put(TAG_KATEGORI, kategori);
						industri.put(TAG_TELP, telp);
						industri.put(TAG_IMAGE, image);

						// adding contact to contact list
						industriList.add(industri);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */ 
			ListAdapter adapter = new SimpleAdapter(
					SearchActivity.this, industriList,
					R.layout.list_item, new String[] { TAG_NAMA, TAG_ALAMAT, TAG_LAT, TAG_LNG, TAG_KATEGORI, TAG_TELP, TAG_IMAGE}, 
					new int[] { R.id.txt_nama, R.id.txt_alamat, R.id.txt_lat, R.id.txt_lng, R.id.txt_kategori, R.id.txt_telp, R.id.txt_image});

			setListAdapter(adapter);
		}

	}
	@Override
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
	
	

}