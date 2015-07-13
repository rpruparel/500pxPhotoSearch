package com.example.inclass6;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PhotoAdapter extends ArrayAdapter < Photo > {
	Context context;
	ArrayList < Photo > objects;

	public PhotoAdapter(Context context, ArrayList < Photo > objects) {
		super(context, R.layout.item_row_layout, objects);
		this.context = context;
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.item_row_layout, parent, false);
			holder = new ViewHolder();
			holder.colorName = (TextView) convertView.findViewById(R.id.colorName);
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();
		TextView colorName = holder.colorName;
		colorName.setText(objects.get(position).getPhotoTitle());

		return convertView;
	}

	static class ViewHolder {
		TextView colorName;
	}
}