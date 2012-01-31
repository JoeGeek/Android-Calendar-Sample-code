/*
 * Copyright (C) 2011 Chris Gao <chris@exina.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.exina.android.calendar.activity;

import com.exina.android.calendar.CalendarView;
import com.exina.android.calendar.Cell;
import com.exina.android.calendar.R;
import com.exina.android.calendar.CalendarView.OnCellTouchListener;
import com.exina.android.calendar.R.id;
import com.exina.android.calendar.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalendarActivity extends Activity  implements CalendarView.OnCellTouchListener, OnClickListener{
	public static final String MIME_TYPE = "vnd.android.cursor.dir/vnd.exina.android.calendar.date";
	CalendarView mView = null;
	TextView mHit;
	Handler mHandler = new Handler();
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button btn_next_month = (Button)findViewById(R.id.btn_next_month);
        Button btn_previous_month = (Button)findViewById(R.id.btn_previous_month);
        mView = (CalendarView)findViewById(R.id.calendar);
        btn_next_month.setOnClickListener(this);
        btn_previous_month.setOnClickListener(this);
        
        
        mView.setOnCellTouchListener(this);
        
        //if picking a date, hide the "hint"
        if(getIntent().getAction().equals(Intent.ACTION_PICK))
        	findViewById(R.id.hint).setVisibility(View.INVISIBLE);
    }
///////////////////////on Cell touch, quit this activity
	public void onTouch(Cell cell) {
		Intent intent = getIntent();
		String action = intent.getAction();
		//check which intent.action was requested
		if(action.equals(Intent.ACTION_PICK) || action.equals(Intent.ACTION_GET_CONTENT)) {
			Intent ret = new Intent();
			ret.putExtra("year", mView.getYear());
			ret.putExtra("month", mView.getMonth());
			ret.putExtra("day", cell.getDayOfMonth());
			this.setResult(RESULT_OK, ret);
			finish(); //activity should be closed, then go to onActivity Result
			return;
		} 
		else if(action.equals(Intent.ACTION_VIEW)){
			Intent ret = new Intent(Intent.ACTION_VIEW);
			ret.putExtra("year", mView.getYear());
			ret.putExtra("month", mView.getMonth());
			ret.putExtra("day", cell.getDayOfMonth());
			ret.setClass(this, TimeSlotListActivity.class);
			
//			finish(); //activity should be closed, then go to onActivity Result
			startActivity(ret);
			return;
		}
		
//		int day = cell.getDayOfMonth();
//		
////		 change the calendar view accordingly
//		if(mView.firstDay(day)) 
//			mView.previousMonth();
//		else if(mView.lastDay(day)) 
//			mView.nextMonth();
//		else
//			return; //do nothing
////
//		mHandler.post(new Runnable() {
//			public void run() {
//				//show toast on the next or previous month
//				Toast.makeText(CalendarActivity.this, DateUtils.getMonthString(mView.getMonth(), DateUtils.LENGTH_LONG) + " "+mView.getYear(), Toast.LENGTH_SHORT).show();
//			}
//		});
	}
	public void onClick(View v) {
		if(v==(View)findViewById(R.id.btn_next_month)) mView.nextMonth();
		else if(v==(View)findViewById(R.id.btn_previous_month)) mView.previousMonth();
		mHandler.post(new Runnable() {
			public void run() {
				//show toast on the next or previous month
				Toast.makeText(CalendarActivity.this, DateUtils.getMonthString(mView.getMonth(), DateUtils.LENGTH_SHORT) + " "+mView.getYear(), Toast.LENGTH_SHORT).show();
			}
		});
	}

    
}