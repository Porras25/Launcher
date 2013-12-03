package com.adquem.launcher;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyBaseAdapter extends BaseAdapter {

	private Context myContext;
	private List<ResolveInfo> MyAppList;
	private PackageManager myPackageManager;

	public MyBaseAdapter(Context c, List<ResolveInfo> l, PackageManager s) {
		myContext = c;
		MyAppList = l;
		myPackageManager = s;
	}

	public int getCount() {
		return MyAppList.size();
	}

	public Object getItem(int position) {
		return MyAppList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) myContext)
					.getLayoutInflater();
			row = inflater.inflate(R.layout.layout_gridappinfo, parent, false);
			holder = new RecordHolder();
			holder.txtTitle = (TextView) row.findViewById(R.id.myTextView);
			holder.txtTitle.setTextColor(Color.WHITE);

			holder.imageItem = (ImageView) row.findViewById(R.id.imageView);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
		ResolveInfo resolveInfo = MyAppList.get(position);

		holder.txtTitle.setText(resolveInfo.loadLabel(myPackageManager));
		Log.i("Testo------------------", resolveInfo
				.loadLabel(myPackageManager).toString());
		holder.imageItem.setImageDrawable(resolveInfo
				.loadIcon(myPackageManager));
		return row;
	}

	static class RecordHolder {
		TextView txtTitle;
		ImageView imageItem;
	}

}
