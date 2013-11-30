package com.adquem.launcher;

import java.io.ByteArrayOutputStream;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;


public class Lanzadorgrid extends Activity {
public PackageManager myPackageManager;
	
		
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_layout);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		myPackageManager = getPackageManager();
		
		Intent intent = new Intent(Intent.ACTION_MAIN,null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> intentList = getPackageManager().queryIntentActivities(intent, 0);
        
        GridView gridGrande = (GridView)findViewById(R.id.gridview);
        MyBaseAdapter nuevoBase = new MyBaseAdapter(this,intentList,myPackageManager);
        gridGrande.setAdapter(nuevoBase);
        
        
        gridGrande.setOnItemClickListener(new OnItemClickListener() {
        	
        	public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
        		ResolveInfo cleckedResolveInfo = 
						(ResolveInfo)parent.getItemAtPosition(position);
				MainActivity mn = new MainActivity();
				mn.myDB=openOrCreateDatabase(mn.DBNAME, Context.MODE_PRIVATE, null);
				
				
				
				Bitmap bitmap = ((BitmapDrawable)cleckedResolveInfo.loadIcon(myPackageManager)).getBitmap();
				
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] byteArray = stream.toByteArray();

				
				
				Log.i("Query", "INSERT INTO "+mn.TABLE+"(NAME,PQUETE,ICON)VALUES("+cleckedResolveInfo.loadLabel(myPackageManager)+","+cleckedResolveInfo.activityInfo.packageName+","+byteArray+")");
				mn.myDB.execSQL("INSERT INTO "+mn.TABLE+"(NAME,PQUETE,ICON)VALUES("+cleckedResolveInfo.loadLabel(myPackageManager)+","+cleckedResolveInfo.activityInfo.packageName+","+byteArray+")");
				
        	}
		});
        
        
	}

}
