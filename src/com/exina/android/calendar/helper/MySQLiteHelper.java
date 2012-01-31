package com.exina.android.calendar.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper{
	public static final String TABLE_BOOKINGS = "bookings";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DATETIMESLOT = "datetimeslot";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_TIMESLOT = "timeslot";

	private static final String DATABASE_NAME = "bookings.db";
	private static final int DATABASE_VERSION = 6;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_BOOKINGS + "( " 
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_DATE+ " TEXT,"
			+ COLUMN_TIMESLOT+ " INTEGER,"
			+ COLUMN_DATETIMESLOT + " text not null);";

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_BOOKINGS);
		onCreate(db);
	}

}