package com.oadigital.clientserverrestdemo;

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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	Button buttonGetData = null;
	EditText editTextSearchString = null;
	TextView textViewFirstName = null;
	TextView textViewLastName = null;
	TextView textViewAge = null;
	TextView textViewPoints = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		buttonGetData = (Button) findViewById(R.id.buttonGetData);
		editTextSearchString = (EditText) findViewById(R.id.editTextSearchString);
		textViewFirstName = (TextView) findViewById(R.id.textViewFirstName);
		textViewLastName = (TextView) findViewById(R.id.textViewLastName);
		textViewAge = (TextView) findViewById(R.id.textViewAge);
		textViewPoints = (TextView) findViewById(R.id.textViewPoints);

		//Setup the Button's OnClickListener
		buttonGetData.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Get the data
				DoPOST mDoPOST = new DoPOST(MainActivity.this, editTextSearchString.getText().toString());
				mDoPOST.execute("");
				buttonGetData.setEnabled(false);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class DoPOST extends AsyncTask<String, Void, Boolean>{

		Context mContext = null;
		String strNameToSearch = "";
		
		//Result data
		String strUid;
		
		Exception exception = null;
		private String strTitle;
		private String strLat;
		private String strLong;
		
		DoPOST(Context context, String uid){
			mContext = context;
			strUid = uid;
		}

		@Override
		protected Boolean doInBackground(String... arg0) {

			try{

				//Setup the parameters
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("uid", strUid));	
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
				strUid = jsonArray.getJSONObject(0).getString("uid");
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
			
			buttonGetData.setEnabled(true);
			
			if(exception != null){
				Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
			}
		}

	}
}
