/*
   Copyright 2012 Ruben Serrano

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package org.uab.deic.uabdroid.solutions.unit5;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class FormFragment extends Fragment 
{
	private EditText mEditTextName;
	private EditText mEditTextDeveloper;
	private DatePicker mDatePickerDate;
	private EditText mEditTextURL;
	
	@Override
	public View onCreateView(LayoutInflater _inflater, ViewGroup _container, Bundle _savedInstanceState) 
	{
		View view = _inflater.inflate(R.layout.form_layout, _container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle _savedInstanceState) 
	{
		super.onActivityCreated(_savedInstanceState);
		
		final Activity parentActivity = getActivity();
		
		SharedPreferences activityPreferences = parentActivity.getPreferences(Context.MODE_PRIVATE);
		
		mEditTextName = (EditText)parentActivity.findViewById(R.id.edittext_form_name);
		mEditTextDeveloper = (EditText)parentActivity.findViewById(R.id.edittext_form_developer);
		mDatePickerDate = (DatePicker)parentActivity.findViewById(R.id.datepicker_form_date);
		mEditTextURL = (EditText)parentActivity.findViewById(R.id.edittext_form_url);
		
		if (activityPreferences.getBoolean(FormAppActivity.STATE_NOT_SAVED, false))
		{
			mEditTextName.setText(activityPreferences.getString(FormAppActivity.FORM_FIELD_NAME, ""));
			
			mEditTextDeveloper.setText(activityPreferences.getString(FormAppActivity.FORM_FIELD_DEVELOPER, ""));
			
			Calendar calendar = Calendar.getInstance();
			int day = activityPreferences.getInt(FormAppActivity.FORM_FIELD_DAY, calendar.get(Calendar.DAY_OF_MONTH));
			int month = activityPreferences.getInt(FormAppActivity.FORM_FIELD_MONTH, calendar.get(Calendar.MONTH)+1);
			int year = activityPreferences.getInt(FormAppActivity.FORM_FIELD_YEAR, calendar.get(Calendar.YEAR));
			
			mDatePickerDate.updateDate(year, month, day);
			
			mEditTextURL.setText(activityPreferences.getString(FormAppActivity.FORM_FIELD_URL, ""));
			
			SharedPreferences.Editor editor = activityPreferences.edit();
			editor.putBoolean(FormAppActivity.STATE_NOT_SAVED, false);
			editor.commit();
		}
		
		Button cleanButton = (Button) parentActivity.findViewById(R.id.button_form_clean);
		cleanButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				mEditTextName.setText("");
				mEditTextDeveloper.setText("");
				Calendar calendar = Calendar.getInstance();
				mDatePickerDate.updateDate(calendar.get(Calendar.YEAR),
									   calendar.get(Calendar.MONTH)+1,
									   calendar.get(Calendar.DAY_OF_MONTH)
									  );
				mEditTextURL.setText("");
			}
		});
		
		Button acceptButton = (Button) parentActivity.findViewById(R.id.button_form_accept);
		acceptButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.DAY_OF_MONTH, mDatePickerDate.getDayOfMonth());
				calendar.set(Calendar.MONTH, mDatePickerDate.getMonth()-1);
				calendar.set(Calendar.YEAR, mDatePickerDate.getYear());
				
				String name = mEditTextName.getText().toString();
				String developer = mEditTextDeveloper.getText().toString();
				String url = mEditTextURL.getText().toString();
				
				DatabaseAdapter databaseAdapter = new DatabaseAdapter(parentActivity);
				databaseAdapter.open();
				databaseAdapter.insertApp(name, developer, calendar, url);
				databaseAdapter.close();
				
				((FormAppActivity)parentActivity).setButtonPressed();
				
				parentActivity.finish();
			}
		});
		
		Button cancelButton = (Button) parentActivity.findViewById(R.id.button_form_cancel);
		cancelButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				((FormAppActivity)parentActivity).setButtonPressed();
				parentActivity.finish();
			}
		});
	}
	
	
	
}