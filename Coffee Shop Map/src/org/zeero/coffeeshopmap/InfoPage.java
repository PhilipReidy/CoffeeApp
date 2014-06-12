package org.zeero.coffeeshopmap;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class InfoPage extends Activity {
	

	TextView textViewFirstName = null;
	TextView textViewLastName = null;
	TextView textViewAge = null;
	TextView textViewPoints = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_page);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		
		double Latitude = extras.getDouble("Latitude");
		double Longitude = extras.getDouble("Longitude");
		
		DoPOST mDoPOST = new DoPOST(InfoPage.this, Latitude, Longitude);
		mDoPOST.execute("");
		
		textViewFirstName = (TextView) findViewById(R.id.textView1);
		textViewLastName = (TextView) findViewById(R.id.textView2);
		

	}
	
	public class DoPOST extends AsyncTask<String, Void, Boolean>{

		Context mContext = null;
		String strNameToSearch = "";
		
		//Result data
		String strUid;
		
		Exception exception = null;
		private String strTitle;
		private double strLat;
		private double strLong;
		
		DoPOST(Context context, double latitude, double longitude){
			mContext = context;
			strLat = latitude;
			strLong = longitude;
		}

		@Override
		protected Boolean doInBackground(String... arg0) {

			try{

				//Setup the parameters
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("latitude", strLat + ""));	
				//Add more parameters as necessary

				//Create the HTTP request
				HttpParams httpParameters = new BasicHttpParams();

				//Setup timeouts
				HttpConnectionParams.setConnectionTimeout(httpParameters, 15000);
				HttpConnectionParams.setSoTimeout(httpParameters, 15000);			

				HttpClient httpclient = new DefaultHttpClient(httpParameters);
				HttpPost httppost = new HttpPost("http://flahool.com/community/data/coffee.json");
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));        
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();

				String result = EntityUtils.toString(entity);

				// Create a JSON object from the request response
				JSONArray jsonArray = new JSONArray(result);

				//Retrieve the data from the JSON object
//				strUid = jsonArray.getJSONObject(0).getString("uid");
				strTitle = jsonArray.getJSONObject(0).getString("title");
//				strLat = JSONArray.getString("latitude");
//				strLong = JSONArray.getString("longitude");
				

			}catch (Exception e){
				Log.e("ClientServerDemo", "Error:", e);
				exception = e;
			}

			return true;
		}

		@Override
		protected void onPostExecute(Boolean valid){
			//Update the UI
			textViewFirstName.setText("UID: " + strUid);
			textViewLastName.setText("Title: " + strTitle);
//			textViewAge.setText("Latitude: " + strLat);
//			textViewPoints.setText("Longitude: " + strLong);	
			
//			buttonGetData.setEnabled(true);
			
			if(exception != null){
				Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
			}
		}

	}
}

