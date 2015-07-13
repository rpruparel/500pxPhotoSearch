package com.example.inclass6;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class PhotoUtil {
	static class PhotoParser {
		static public ArrayList < Photo > parseJSON(String in ) throws JSONException {


			ArrayList < Photo > photos = new ArrayList < Photo > ();

			//overall object holding photo object array
			JSONObject root = new JSONObject( in );


			//gives me an array of photo objects
			JSONArray photoArray = root.getJSONArray("photos");

			for (int i = 0; i < photoArray.length(); i++) {
				JSONObject p = photoArray.getJSONObject(i);

				Photo photo = new Photo();
				photo.setPhotoURL(p.getString("image_url"));
				photo.setPhotoTitle(p.getString("name"));

				JSONObject user = (JSONObject) p.get("user");
				StringBuilder sb = new StringBuilder();

				sb.append(user.get("firstname"));
				sb.append(" ");
				sb.append(user.get("lastname"));

				photo.setOwnerName(sb.toString());
				photos.add(photo);
			}


			return photos;
		}
	}
}