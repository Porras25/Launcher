package com.adquem.launcher;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


public class LanzadorActivity extends Activity {
	private ListView mListAppInfo;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);
		
		// load list application
        mListAppInfo = (ListView)findViewById(R.id.lvApps);
        // create new adapter
        AppInfoAdapter adapter = new AppInfoAdapter(this, Utilities.getInstalledApplication(this), getPackageManager());
        // set adapter to list view
        mListAppInfo.setAdapter(adapter);
        // implement event when an item on list view is selected
        mListAppInfo.setOnItemClickListener(new OnItemClickListener() {
        	
        	public void onItemClick(AdapterView parent, View view, int pos, long id) {
                // get the list adapter
        		AppInfoAdapter appInfoAdapter = (AppInfoAdapter)parent.getAdapter();
        		// get selected item on the list
        		ApplicationInfo appInfo = (ApplicationInfo)appInfoAdapter.getItem(pos);
        		// launch the selected application
        		Utilities.launchApp(parent.getContext(), getPackageManager(), appInfo.packageName);
        	}
		});
            
		
		
		
	}

}
