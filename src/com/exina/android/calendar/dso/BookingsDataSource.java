package com.exina.android.calendar.dso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.exina.android.calendar.activity.TimeSlotListActivity;
import com.exina.android.calendar.helper.MySQLiteHelper;
import com.exina.android.calendar.model.Booking;

//open, close, create, delete Bookings
public class BookingsDataSource {

	Integer year, month, day;
	
	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
		
	private String[] dateTimeSlotColumn = {
			MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_DATE,
			MySQLiteHelper.COLUMN_TIMESLOT,
			MySQLiteHelper.COLUMN_DATETIMESLOT
			};

	public BookingsDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public void createBooking(Booking booking) {

		ContentValues values = new ContentValues();
		
		values.put(MySQLiteHelper.COLUMN_DATETIMESLOT, booking.getStrDateTimeSlot());
		
		values.put(MySQLiteHelper.COLUMN_DATE, TimeSlotListActivity.getDateTimeString());
		
		values.put(MySQLiteHelper.COLUMN_TIMESLOT, booking.getTimeSlot());
		
		long insertId = database.insert(MySQLiteHelper.TABLE_BOOKINGS, null,
				values);
		
		// To show how to query
		Cursor cursor = database.query(MySQLiteHelper.TABLE_BOOKINGS,
				dateTimeSlotColumn, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		
		 cursorToBooking(cursor);
		 cursor.close();
	}

	public void deleteBooking(Booking booking) {
		
		String strDateTimeSlot = booking.getStrDateTimeSlot();
		
		database.delete(MySQLiteHelper.TABLE_BOOKINGS, MySQLiteHelper.COLUMN_DATETIMESLOT
				+ " = '" + strDateTimeSlot+"'", null);
	}

	public List<Booking> getAllBookingsForDate() {
		
		List<Booking> bookings = new ArrayList<Booking>();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_BOOKINGS,
				dateTimeSlotColumn, MySQLiteHelper.COLUMN_DATE
				+ " like '" + TimeSlotListActivity.getDateTimeString()+ "%'", null, null, null, null);
		
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Booking booking = cursorToBooking(cursor);
			bookings.add(booking);
			cursor.moveToNext();
		}
		// Make sure to close the cursor
		cursor.close();
		return bookings;
	}

	private Booking cursorToBooking(Cursor cursor) {
		
		Booking booking = new Booking("");
		//booking ID
		booking.setId(cursor.getLong(0));
		
		SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd"); 
		
		//booking date
		String tempStrDate = cursor.getString(1);
		try {
			Date dateObj = curFormater.parse(tempStrDate);
			booking.setBookingDate(dateObj);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		//time slot
		booking.setTimeSlot(cursor.getInt(2));
		
		//Date Time Slot
		booking.setStrDateTimeSLot(cursor.getString(3));
		
		return booking;
	}
}
