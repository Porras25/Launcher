package com.adquem.launcher;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class LanzadorActivity extends Activity {
	//private ListView mListAppInfo;
	private GridView gridGeneral;
	private PackageManager myotroPackage; 
	public MyBaseAdapter myBaseAdapterGen;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_apps);
		
		
		myotroPackage = getPackageManager();
		
		Intent intent = new Intent(Intent.ACTION_MAIN,null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> intentList = getPackageManager().queryIntentActivities(intent, 0);
		
        gridGeneral = (GridView)findViewById(R.id.gridview2);
        myBaseAdapterGen =new MyBaseAdapter(this, intentList, myotroPackage);
        gridGeneral.setAdapter(myBaseAdapterGen);
        		
        
        
        
		// load list application
        //mListAppInfo = (ListView)findViewById(R.id.lvApps);
        
        // create new adapter
        //AppInfoAdapter adapter = new AppInfoAdapter(this, Utilities.getInstalledApplication(this), getPackageManager());
         
        
        // set adapter to list view
        //mListAppInfo.setAdapter(adapter);
        // implement event when an item on list view is selected
        gridGeneral.setOnItemClickListener(new OnItemClickListener() {
        	
        	public void onItemClick(AdapterView parent, View view, int pos, long id) {
                // get the list adapter
        		//AppInfoAdapter appInfoAdapter = (AppInfoAdapter)parent.getAdapter();      
        		
        		
        		// get selected item on the list
        		//ApplicationInfo appInfo = (ApplicationInfo)appInfoAdapter.getItem(pos); 

        		ResolveInfo resuelvelo = (ResolveInfo)myBaseAdapterGen.getItem(pos);
        		// launch the selected application
        		Utilities.launchApp(parent.getContext(), getPackageManager(), resuelvelo.activityInfo.packageName);
        	}
		});
            
		
		
		
	}

}
