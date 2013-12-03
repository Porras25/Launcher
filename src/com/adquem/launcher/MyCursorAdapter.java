package com.adquem.launcher;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.CursorAdapter;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter {

	public MyCursorAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View retView = inflater.inflate(R.layout.layout_gridappinfo, parent,
				false);
		return retView;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		ImageView iconoApp = (ImageView) view.findViewById(R.id.imageView);
		
		view.setTag(cursor.getString(cursor.getColumnIndex(cursor
				.getColumnName(2))));

		String icon = cursor.getString(cursor.getColumnIndex(cursor
				.getColumnName(3)));
		byte[] encodeByte = Base64.decode(icon, Base64.DEFAULT);

		Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
				encodeByte.length);
		iconoApp.setImageBitmap(bitmap);

		TextView nombreIcon = (TextView) view.findViewById(R.id.myTextView);
		nombreIcon.setText(cursor.getString(cursor.getColumnIndexOrThrow(cursor
				.getColumnName(1))));
	}

}
