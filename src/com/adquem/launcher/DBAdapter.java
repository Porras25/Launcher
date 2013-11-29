package com.adquem.launcher;

public class DBAdapter {
	
	static final String KEY_ROWID = "_id";
	static final String NAME = "name";
	static final String PACKAGE = "pakete";
	static final String ICON = "iconos";
	
	static final String DATABASE_NAME="MyDB";
	static final String DATABASE_TABLE="favoriteapps";
	static final int DATABASE_VERSION = 1;
	
	static final String DATABASE_CREATE = "create table favoriteapps (_id integer primary key autoincrement, "
			+"name text not null, pakete text not null, iconos,);";

}
