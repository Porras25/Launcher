package com.adquem.launcher;

import java.io.ByteArrayOutputStream;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

public class LanzadorActivity extends Activity {
	// private ListView mListAppInfo;
	private GridView gridGeneral;
	private PackageManager myotroPackage;
	public MyBaseAdapter myBaseAdapterGen;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_apps);

		myotroPackage = getPackageManager();
		final MainActivity myMainActivity = new MainActivity();
		myMainActivity.myDB = openOrCreateDatabase(myMainActivity.DBNAME,
				Context.MODE_PRIVATE, null);

		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> intentList = getPackageManager()
				.queryIntentActivities(intent, 0);

		gridGeneral = (GridView) findViewById(R.id.gridview2);
		myBaseAdapterGen = new MyBaseAdapter(this, intentList, myotroPackage);
		gridGeneral.setAdapter(myBaseAdapterGen);

		gridGeneral.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView parent, View view, int pos,
					long id) {
				// get the list adapter
				ResolveInfo resuelvelo = (ResolveInfo) myBaseAdapterGen
						.getItem(pos);
				// launch the selected application
				Utilities.launchApp(parent.getContext(), getPackageManager(),
						resuelvelo.activityInfo.packageName);

				// Get icon

				Bitmap bitmap = ((BitmapDrawable) resuelvelo
						.loadIcon(myotroPackage)).getBitmap();

				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] b = stream.toByteArray();
				String temp = Base64.encodeToString(b, Base64.DEFAULT);

				Log.i("Query", "INSERT INTO " + myMainActivity.TABLE2
						+ "(NAME,PAQUETE,ICON)VALUES(" + "'"
						+ resuelvelo.activityInfo.name.toString() + "'" + ","
						+ "'" + resuelvelo.activityInfo.packageName.toString()
						+ "'" + "," + "'" + temp + "'" + ");");
				myMainActivity.myDB.execSQL("INSERT INTO "
						+ myMainActivity.TABLE2 + "(NAME,PAQUETE,ICON)VALUES("
						+ "'" + resuelvelo.activityInfo.name.toString() + "'"
						+ "," + "'"
						+ resuelvelo.activityInfo.packageName.toString() + "'"
						+ "," + "'" + temp + "'" + ");");

			}
		});

	}

}
