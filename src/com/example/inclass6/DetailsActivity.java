package com.example.inclass6;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends Activity {

	ImageView image;
	Photo photo;
	ProgressDialog dialog;
	TextView tv;@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		image = (ImageView) findViewById(R.id.imageView1);
		image.setVisibility(View.INVISIBLE);
		tv = (TextView) findViewById(R.id.textView1);
		tv.setVisibility(View.INVISIBLE);
		tv = (TextView) findViewById(R.id.textView3);
		tv.setVisibility(View.INVISIBLE);

		if (getIntent().getExtras().containsKey("photo")) {
			photo = (Photo) getIntent().getExtras().getSerializable("photo");
		}
		String photoUrl = photo.getPhotoURL();
		new LoadImage().execute(photoUrl);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class LoadImage extends AsyncTask < String, Void, Bitmap > {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(DetailsActivity.this);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dialog.setCancelable(false);
			dialog.setMessage(DetailsActivity.this.getResources().getString(
			R.string.progress_message));
			dialog.show();
		}

		@Override
		protected Bitmap doInBackground(String...params) {
			AndroidHttpClient client = AndroidHttpClient.newInstance("Android Agent");
			InputStream in = null;
			HttpGet request = new HttpGet(params[0]);
			try {
				HttpResponse response = client.execute(request); in = response.getEntity().getContent();

				Bitmap image = BitmapFactory.decodeStream( in );
				return image;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if ( in != null) {
					try { in .close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				image = (ImageView) findViewById(R.id.imageView1);
				image.setVisibility(View.VISIBLE);
				image.setImageBitmap(result);
				dialog.dismiss();
				tv = (TextView) findViewById(R.id.textView1);
				tv.setText(photo.getPhotoTitle());
				tv.setVisibility(View.VISIBLE);
				tv = (TextView) findViewById(R.id.textView3);
				tv.setText("By: " + photo.getOwnerName());
				tv.setVisibility(View.VISIBLE);
			}
		}

	}@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
	}
}