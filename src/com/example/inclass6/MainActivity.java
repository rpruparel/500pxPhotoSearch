package com.example.inclass6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.Toast;

public class MainActivity extends Activity {
	EditText et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		et = (EditText) findViewById(R.id.editText1);

		Button b = (Button) findViewById(R.id.button1);

		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isConnectedOnline()) {
					if (et.getText() != null && !et.getText().equals("") && et.getText().length() > 0) {
						Intent intent = new Intent(MainActivity.this,
						GalleryActivity.class);
						intent.putExtra("searchTerm", et.getText().toString());
						startActivity(intent);
					} else {
						Toast.makeText(MainActivity.this,
						R.string.enter_text, Toast.LENGTH_LONG)
							.show();
					}
				} else {
					Toast.makeText(MainActivity.this, R.string.no_connection,
					Toast.LENGTH_LONG).show();
				}
			}
		});

	}

	private boolean isConnectedOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			return true;
		}
		return false;
	}

}