package com.exina.android.calendar.model;

import java.util.Date;

//Model for database
public class Booking {

	private long id;
	private String strDateTimeSlot;
	private Date bookingDate;
	private Integer timeSlot;
	private String time;
	private Boolean selected;
	
	public Booking(String dateTmeSlot) {
		this.strDateTimeSlot= dateTmeSlot;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStrDateTimeSlot() {
		return strDateTimeSlot;
	}

	public void setStrDateTimeSLot(String strDateTimeSlot) {
		this.strDateTimeSlot = strDateTimeSlot;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return strDateTimeSlot;
	}
	
	public Date getBookingDate(){
		return bookingDate;
	}
	
	public void setBookingDate(Date dateObj){

		this.bookingDate = dateObj;
	}
	
	public Integer getTimeSlot(){
		return timeSlot;
	}
	
	public void setTimeSlot(Integer slot){
		this.timeSlot = slot;
	}
	
	public String gettime() {
		return time;
	}

	public void setName(String time) {
		this.time = time;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}