package com.adquem.launcher;

import java.io.ByteArrayOutputStream;
import java.util.Timer;
import java.util.TimerTask;

import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity {
	ImageView IV_carrusel;
	Timer longClickTimer;
	AdquemPlayer vv;
	SharedPreferences sp;
	public SQLiteDatabase myDB;
	Editor editor;
	public static String DBNAME = "MyDATABASE";
	public static String TABLE = "Table_Favoritos";
	public static String TABLE2 = "Table_UserXp";

	public Cursor myCursor = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.googleplus2);

		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, bytestream);
		byte[] bi = bytestream.toByteArray();
		String tempo = Base64.encodeToString(bi, Base64.DEFAULT);

		sp = getSharedPreferences("LauncherPreferences", Context.MODE_PRIVATE);
		editor = sp.edit();
		myDB = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
		if (sp.getBoolean("Primera_vez", true)) {

			Log.i("Table1",
					"CREATE TABLE IF  NOT EXISTS "
							+ TABLE
							+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PAQUETE TEXT, ICON TEXT);");
			myDB.execSQL("CREATE TABLE IF  NOT EXISTS "
					+ TABLE
					+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PAQUETE TEXT, ICON TEXT);");
			Log.i("Table1",
					"CREATE TABLE IF  NOT EXISTS "
							+ TABLE2
							+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PAQUETE TEXT, ICON TEXT);");
			myDB.execSQL("CREATE TABLE IF  NOT EXISTS "
					+ TABLE2
					+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PAQUETE TEXT, ICON TEXT);");
			editor.putBoolean("Primera_vez", false);
			myDB.execSQL("INSERT INTO " + TABLE
					+ "(NAME,PAQUETE,ICON)VALUES('Mas','sinpaquete'," + "'"
					+ tempo + "');");
			editor.commit();
		}

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.layouy_launcher);
		IV_carrusel = (ImageView) findViewById(R.id.imagen1);
		IV_carrusel.setImageResource(R.drawable.imagen1);
		IV_carrusel.setTag(R.drawable.imagen1);
		Cambio_Imagen carrusel = new Cambio_Imagen();
		Timer tiempo = new Timer();
		tiempo.schedule(carrusel, 3000, 3000);
		vv = (AdquemPlayer) findViewById(R.id.videoView1);
		vv.setClickable(true);
		vv.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN
						&& longClickTimer == null) {
					if (vv.getSound()) {
						vv.mute();
					} else {
						vv.unmute();
					}
					Log.d("TouchAction", "ACTION_DOWN");
					longClickTimer = new Timer();
					longClickTimer.schedule(new LongClickTask(), 1000, 4000);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					Log.d("TouchAction", "ACTION_UP");

					if (longClickTimer != null) {
						longClickTimer.cancel();
						longClickTimer.purge();
						longClickTimer = null;
					}
				}

				return true;

			}
		});

		vv.setVideoPath("android.resource://" + getPackageName() + "/"
				+ R.raw.gorditayoo);

		final VideoView vv = (VideoView) findViewById(R.id.videoView1);

		vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				vv.start();
			}
		});

		vv.requestFocus();
		vv.start();


		
		WebView webview1 = (WebView) findViewById(R.id.vistaweb1);
		if (isNetworkAvailable()) {			
			webview1.loadUrl("https://sitioseguro.cablevision.net.mx/");
		}else {
			//webview1.getSettings().setJavaScriptEnabled(true);
			webview1.setClickable(true);
	        webview1.loadUrl("file:///android_asset/Index.html");	 
	        webview1.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
					return false;
				}
			});
	     /*   webview1.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
				}
			});*/
		}
		
	

		
		
		
		
		
		DigitalClock dc = (DigitalClock) findViewById(R.id.DigitalClock);
		AnalogClock ac = (AnalogClock) findViewById(R.id.analogClock1);

		final String appName = "com.adquem";
		final Button botonPlayStore = (Button) findViewById(R.id.boton1);
		botonPlayStore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Intent LaunchIntent = getPackageManager()
							.getLaunchIntentForPackage("com.android.vending");
					startActivity(LaunchIntent);

				} catch (android.content.ActivityNotFoundException anfe) {
					startActivity(new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://play.google.com/store/apps/details?id="
									+ appName)));
				}
			}
		});

		final Button botonApps = (Button) findViewById(R.id.boton2);
		botonApps.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// startActivity(new Intent(Intent.ACTION_PICK).setType("*/*"));
				startActivity(new Intent(MainActivity.this,
						LanzadorActivity.class));

			}
		});

		final Button botonMedia = (Button) findViewById(R.id.boton3);
		botonMedia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Intent.ACTION_PICK).setType("image/*"));
			}
		});

		final Button botonWeb = (Button) findViewById(R.id.boton4);
		botonWeb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String url = "https://sitioseguro.cablevision.net.mx/";

				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
			}
		});

		final Button botonSettings = (Button) findViewById(R.id.boton5);
		botonSettings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Settings.ACTION_SETTINGS));
			}
		});
		
		final Button botonUsuario = (Button)findViewById(R.id.boton6);
		botonUsuario.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Cursor i = myDB.rawQuery("SELECT * FROM " + TABLE2 + ";",null);
				
				if (i.moveToFirst()) {
					do {
						Intent userIntent = getPackageManager()
								.getLaunchIntentForPackage(i.getString(i.getColumnIndex(i.getColumnName(2))));
						startActivity(userIntent);
						
					} while (i.moveToNext());
					
				}
				
				
				myDB.execSQL("DELETE FROM "+TABLE2 +";");
				Toast toastUser = Toast.makeText(MainActivity.this,
						"Lanzando ultimas apps abiertas", Toast.LENGTH_LONG);
				toastUser.show();			
				
			}
		});
		
		
		Log.i("Query Cursor", "SELECT * FROM " + TABLE + ";");
		myCursor = myDB.rawQuery("SELECT * FROM " + TABLE + ";", null);

		final GridView gridLauncher = (GridView) findViewById(R.id.VistaGrid);
		MyCursorAdapter myCursorAdapter = new MyCursorAdapter(getBaseContext(),
				myCursor);

		if (myCursor.requery()) {
			myCursorAdapter.notifyDataSetChanged();
			gridLauncher.setAdapter(myCursorAdapter);
		}
		gridLauncher.setAdapter(myCursorAdapter);

		gridLauncher.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
		
//				MyBaseAdapter infoApps = (MyBaseAdapter)arg0.getAdapter();
//			
//				Bean_AppFavoritas appselecc = (Bean_AppFavoritas)infoApps.getItem(arg2);

				if (arg1.getTag().toString().equalsIgnoreCase("sinpaquete")) {
					Toast toast = Toast.makeText(MainActivity.this,
							"Coloca tus apps preferidas", Toast.LENGTH_LONG);
					toast.show();
					startActivity(new Intent(MainActivity.this,
							Lanzadorgrid.class));

				} else {

					Log.i("package name", arg1.getTag().toString());
					Intent LaunchIntent = getPackageManager()
							.getLaunchIntentForPackage(arg1.getTag().toString());
					startActivity(LaunchIntent);
					
					
					Log.i("Query", "INSERT INTO " + TABLE2
							+ "(NAME,PAQUETE,ICON)VALUES(" + "'"
							+ "NOMBRE" + "'" + ","
							+ "'" + arg1.getTag().toString()
							+ "'" + "," + "'" + "ICONO" + "'" + ");");
					myDB.execSQL("INSERT INTO "
							+ TABLE2 + "(NAME,PAQUETE,ICON)VALUES("
							+ "'" + "NOMBRE" + "'"
							+ "," + "'"
							+ arg1.getTag().toString() + "'"
							+ "," + "'" + "ICONO" + "'" + ");");				
					
				}

			}
		});
	}
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		vv.start();
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		vv.start();

		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public class Cambio_Imagen extends TimerTask {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					switch (Integer.parseInt(IV_carrusel.getTag().toString())) {
					case R.drawable.yoo:
						IV_carrusel.setImageResource(R.drawable.imagen1);
						IV_carrusel.setTag(R.drawable.imagen1);
						break;
					case R.drawable.imagen1:
						IV_carrusel.setImageResource(R.drawable.yoo);
						IV_carrusel.setTag(R.drawable.yoo);
						break;
					default:
						break;
					}
				}
			});

		}

	}

	class LongClickTask extends TimerTask {

		@Override
		public void run() {
			Log.d("Action", "Long Click");
			longClickTimer.cancel();
			longClickTimer.purge();
			longClickTimer = null;
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://sitioseguro.cablevision.net.mx/")));
		}
	}
}
