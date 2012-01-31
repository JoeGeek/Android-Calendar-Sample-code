package com.exina.android.calendar.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.exina.android.calendar.R;
import com.exina.android.calendar.activity.TimeSlotListActivity;
import com.exina.android.calendar.dso.BookingsDataSource;
import com.exina.android.calendar.model.Booking;
import com.exina.android.calendar.model.Model;

public class InteractiveArrayAdapter extends ArrayAdapter<Model> {

	Date dateObj;
	private BookingsDataSource datasource;
	private final List<Model> list;
	private final Activity context;

	public InteractiveArrayAdapter(Activity context, List<Model> list) {
		super(context, R.layout.rowbuttonlayout, list);
		this.context = context;
		this.list = list;
	}

	static class ViewHolder {
		protected TextView text;
		protected CheckBox checkbox;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.rowbuttonlayout, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) view.findViewById(R.id.label);
			viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
			viewHolder.checkbox
					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							Model element = (Model) viewHolder.checkbox
									.getTag();

							//save in database
							datasource = new BookingsDataSource(getContext());
							datasource.open();
							
							if(buttonView.isChecked()) {
								
								Booking booking = new Booking("");
								booking.setTimeSlot(getSlotId(element.getName()));
								
								String strDateTime = TimeSlotListActivity.getDateTimeString();
								
								SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd"); 
								try {
									dateObj = curFormater.parse(strDateTime);
									
									booking.setBookingDate(dateObj);
									
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
								String strdateTimeSlot = strDateTime+"-"+getSlotId(element.getName());
								booking.setStrDateTimeSLot(strdateTimeSlot);
								datasource.createBooking(booking);
								datasource.close();
								
							}
							else {
								Booking booking = new Booking("");
								booking.setTimeSlot(getSlotId(element.getName()));
								
								String strDateTime = TimeSlotListActivity.getDateTimeString();
								SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd"); 
								try {
									dateObj = curFormater.parse(strDateTime);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 
								String strdateTimeSlot = strDateTime+"-"+getSlotId(element.getName());
								booking.setStrDateTimeSLot(strdateTimeSlot);
								booking.setBookingDate(dateObj);
								booking.setTimeSlot(getSlotId(element.getName()));
								datasource.deleteBooking(booking);
								datasource.close();
								
							}
							
							
							//check or un-check the view
							element.setSelected(buttonView.isChecked());
							
						}
						
						/////getSlotId
						private int getSlotId(String name) {
							
							int i=-1;
							
							if(name=="8:00-9:00pm") i = 0;
							if(name=="9:00-10:00pm")i = 1;
								if(name=="10:00-11:00pm")i = 2;
								if(name=="11:00-12:00pm")i = 3;
								if(name=="12:00-1:00am")i = 4;
								if(name=="1:00-2:00am")i = 5;
								if(name=="2:00-3:00am")i = 6;
								if(name=="3:00-4:00am")i = 7;
								if(name=="4:00-5:00am")i = 8;
								if(name=="5:00-6:00am")i = 9;
								if(name=="6:00-7:00am")i = 10;
								if(name=="7:00-8:00am")i = 11;
								if(name=="8:00-9:00am")i = 12;
								if(name=="9:00-10:00am")i = 13;
								return i;
							
						}
						
						///////////end of getSlotId()
					});
			view.setTag(viewHolder);
			viewHolder.checkbox.setTag(list.get(position));
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
		}
		
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.text.setText(list.get(position).getName());
		holder.checkbox.setChecked(list.get(position).isSelected());
		return view;
	}
}