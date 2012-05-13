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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.DatePicker;
import android.widget.EditText;

public class FormAppActivity extends FragmentActivity 
{	
	public static final String STATE_NOT_SAVED = "STATE_NOT_SAVED";
	public static final String FORM_FIELD_NAME = "FORM_FIELD_NAME";
	public static final String FORM_FIELD_DEVELOPER = "FORM_FIELD_DEVELOPER";
	public static final String FORM_FIELD_DAY = "FORM_FIELD_DAY";
	public static final String FORM_FIELD_MONTH = "FORM_FIELD_MONTH";
	public static final String FORM_FIELD_YEAR = "FORM_FIELD_YEAR";
	public static final String FORM_FIELD_URL = "FORM_FIELD_URL";
	
	private boolean mButtonPressed = false; 

	@Override
	protected void onCreate(Bundle _savedInstanceState) 
	{
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.form_activity_layout);
		
		mButtonPressed = false;
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FormFragment fragment = (FormFragment) fragmentManager.findFragmentById(R.id.framelayout_form_activity);
	
		if (fragment == null)
		{
			fragment = new FormFragment();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
			fragmentTransaction.replace(R.id.framelayout_form_activity, fragment);
			fragmentTransaction.commit();
		}
	}

	@Override
	public void onBackPressed() 
	{
		storeCurrentState();
		
		super.onBackPressed();
	}

	@Override
	protected void onStop() 
	{
		if (!mButtonPressed)
		{
			storeCurrentState();
		}
		
		super.onStop();
	}
	
	public void setButtonPressed() 
	{
		mButtonPressed = true;
	}
	
	private void storeCurrentState()
	{
		SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putBoolean(STATE_NOT_SAVED,true);
		
		EditText editText = (EditText) findViewById(R.id.edittext_form_name);
		editor.putString(FORM_FIELD_NAME, editText.getText().toString());
		
		editText = (EditText) findViewById(R.id.edittext_form_developer);
		editor.putString(FORM_FIELD_DEVELOPER, editText.getText().toString());
		
		DatePicker datePicker = (DatePicker) findViewById(R.id.datepicker_form_date); 
		editor.putInt(FORM_FIELD_DAY, datePicker.getDayOfMonth());
		editor.putInt(FORM_FIELD_MONTH, datePicker.getMonth());
		editor.putInt(FORM_FIELD_YEAR, datePicker.getYear());
		
		editText = (EditText) findViewById(R.id.edittext_form_url);
		editor.putString(FORM_FIELD_URL, editText.getText().toString());
		
		editor.commit();
	}
	
}
