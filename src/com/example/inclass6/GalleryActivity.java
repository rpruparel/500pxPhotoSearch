package com.example.inclass6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class GalleryActivity extends Activity {

	ArrayList < Photo > photoList;
	Photo photo;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		String search = "";

		if (getIntent().getExtras().containsKey("searchTerm")) {
			search = getIntent().getExtras().getString("searchTerm");
		};

		StringBuilder sb = new StringBuilder();
		sb.append("https://api.500px.com/v1/photos/search?");
		sb.append("consumer_key=");
		sb.append(getResources().getString(R.string.consumer_key));
		sb.append("&term=");
		try {
			search = URLEncoder.encode(search, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sb.append(search);
		sb.append("&image_size=4");
		sb.append("&rpp=50");

		new ConnectionAsync().execute(sb.toString());

		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView <? > parent, View view,
			int position, long id) {
				// TextView tv = (TextView) view.findViewById(R.id.colorName);
				// Log.d("demo1", tv.getText() + "");
				photo = photoList.get(position);
				Intent intent = new Intent(GalleryActivity.this,
				DetailsActivity.class);
				intent.putExtra("photo", photo);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
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

	public class ConnectionAsync extends
	AsyncTask < String, Void, ArrayList < Photo >> {
		ArrayList < String > photoURLs;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(GalleryActivity.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage(GalleryActivity.this.getResources().getString(
			R.string.progress));
			dialog.show();
		}

		@Override
		protected ArrayList < Photo > doInBackground(String...params) {
			try {
				URL url = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("GET");

				Log.d("connectin", url.toString());

				con.connect();

				int responseCode = con.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					Log.d("connection", "connection is OK");

					BufferedReader reader = new BufferedReader(
					new InputStreamReader(con.getInputStream()));

					StringBuilder sb = new StringBuilder();
					String line = reader.readLine();

					while (line != null) {
						sb.append(line);
						Log.d("connection line", line);
						line = reader.readLine();
					}

					return PhotoUtil.PhotoParser.parseJSON(sb.toString());
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(ArrayList < Photo > result) {
			// TODO Auto-generated method stub

			Log.d("post execute", result.get(0).toString());
			photoList = result;
			dialog.dismiss();
			ListView listView = (ListView) findViewById(R.id.listView1);
			PhotoAdapter adapter = new PhotoAdapter(GalleryActivity.this,
			photoList);
			listView.setAdapter(adapter);
			super.onPostExecute(result);

		}

	}
}