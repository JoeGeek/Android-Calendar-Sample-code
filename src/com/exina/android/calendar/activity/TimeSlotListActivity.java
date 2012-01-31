package com.exina.android.calendar.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.exina.android.calendar.adapter.InteractiveArrayAdapter;
import com.exina.android.calendar.dso.BookingsDataSource;
import com.exina.android.calendar.model.Booking;
import com.exina.android.calendar.model.Model;

//Activity to views available timeslots/booking for the day
public class TimeSlotListActivity extends ListActivity{
	
	private BookingsDataSource datasource;
	String tempDateTme;
	public static Integer year;
	public static Integer month;
	public static Integer day;
	
	private List<Model> list;
	public static Booking globalBooking;
	
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		// Create an array of Strings, that will be put to our ListActivity
		
		//setContentView(R.layout.lv_layout);
		Bundle extras = getIntent().getExtras();
		
		//get the date
		if(extras !=null) {
			year = extras.getInt("year", 0);
			month = extras.getInt("month", 0);
			month++;
			day = extras.getInt("day", 0);
			
			//display the current date
			Toast.makeText(TimeSlotListActivity.this, day + " " + DateUtils.getMonthString(month, DateUtils.LENGTH_SHORT) + " "+ year, Toast.LENGTH_SHORT).show();
		}
		
		//get the list view model list
		list = getModel();
		//marked checked
		markCheckedBookings(list);
		
		//attach adapter
		ArrayAdapter<Model> adapter = new InteractiveArrayAdapter(this,
				list);

		setListAdapter(adapter);
		
	}
	
	//create list of the Model, to be displayed in the listview
	private List<Model> getModel() {
		List<Model> list = new ArrayList<Model>();
		list.add(get("8:00-9:00pm"));
		list.add(get("9:00-10:00pm"));
		list.add(get("10:00-11:00pm"));
		list.add(get("11:00-12:00pm"));
		list.add(get("12:00-1:00am"));
		list.add(get("1:00-2:00am"));
		list.add(get("2:00-3:00am"));
		list.add(get("3:00-4:00am"));
		list.add(get("4:00-5:00am"));
		list.add(get("5:00-6:00am"));
		list.add(get("6:00-7:00am"));
		list.add(get("7:00-8:00am"));
		list.add(get("8:00-9:00am"));
		list.add(get("9:00-10:00am"));
		return list;
	}

	//get the existing bookings for the day, marked the ones checked
	private void markCheckedBookings(List<Model> list) {

		String currDateTme = getDateTimeString();
		
		datasource = new BookingsDataSource(this);
		datasource.open();
		List<Booking> bookingsList  = datasource.getAllBookingsForDate();
		
		SimpleDateFormat postFormater = new SimpleDateFormat("yyyy-MM-dd");
		for(Integer i=0; i<bookingsList.size(); i++)
		{
			
			Date tempDate = bookingsList.get(i).getBookingDate();
			String strDate = postFormater.format(tempDate);
			
			//assign check
			if(strDate.equals(currDateTme)){
				Integer tempLocation = bookingsList.get(i).getTimeSlot();
				list.get(tempLocation).setSelected(true);
				
			}
			
		}
	}

	public static String getDateTimeString() {
		String strMonth=TimeSlotListActivity.month.toString();
		String strDay=TimeSlotListActivity.day.toString();
		if(TimeSlotListActivity.month.toString().length()!=2) strMonth = "0"+TimeSlotListActivity.month.toString();
		if(TimeSlotListActivity.day.toString().length()!=2)strDay = "0"+TimeSlotListActivity.day.toString();
	
		return TimeSlotListActivity.year.toString()+"-"+strMonth+"-"+strDay;
	}

	//create new Models, to populate in the list, to be displayed in the listview
	private Model get(String s) {
		return new Model(s);
	}
	
//	public Boolean getFromSharedPreference(String dateTime){
//		SharedPreferences checkState = getSharedPreferences(PREFS_NAME, 0);
//		return checkState.getBoolean(dateTime, false);
//	}
//	
//	public void saveInSharedPreference(String dateTime, Boolean state){
//		SharedPreferences checkState = getSharedPreferences(PREFS_NAME, 0);
//		SharedPreferences.Editor editor = checkState.edit();
//	      editor.putBoolean(dateTime, state);
//
//	      // Don't forget to commit your edits!!!
//	      editor.commit();
//		return;
//	}
//
//	private void saveCheckListState() {
//		for(Integer i=0; i<list.size(); i++)
//		{
//			String tempDateTime = getDateTimeString();
//			saveInSharedPreference(tempDateTime, list.get(i).isSelected());
//		}
//	}
//	
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//	    if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
//	        saveCheckListState();
//	    }
//	    return super.onKeyDown(keyCode, event);
//	}
//
//	

}
