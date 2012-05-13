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

package org.uab.deic.uabdroid.solutions.unit5.services;

import org.uab.deic.uabdroid.solutions.unit5.DatabaseAdapter;
import org.uab.deic.uabdroid.solutions.unit5.R;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;

public class AppWidget extends AppWidgetProvider 
{
	public static final String OWN_UPDATES = "org.uab.deic.uabdroid.solutions.unit5.UPDATE_WIDGET_NOW";

	@Override
	public void onUpdate(Context _context, AppWidgetManager _appWidgetManager, int[] _appWidgetIds) 
	{
		super.onUpdate(_context, _appWidgetManager, _appWidgetIds);
		updateSessionInfo(_context, _appWidgetManager, _appWidgetIds);
	}

	@Override
	public void onReceive(Context _context, Intent _intent) 
	{
		super.onReceive(_context, _intent);
		if (_intent.getAction().equals(OWN_UPDATES)) 
		{
			updateSessionInfo(_context);
		}
	}
	
	public void updateSessionInfo(Context _context) 
	{
		ComponentName thisWidget = new ComponentName(_context, AppWidget.class);
		AppWidgetManager appWidgetManager =	AppWidgetManager.getInstance(_context);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		
		updateSessionInfo(_context, appWidgetManager, appWidgetIds);
	}
	
	private void updateSessionInfo(Context _context, AppWidgetManager _appWidgetManager, int[] _appWidgetIds) 
	{
		final int appWidgetsArrayLength = _appWidgetIds.length;
		
		for (int i = 0; i < appWidgetsArrayLength; i++) 
		{
			RemoteViews views = new RemoteViews(_context.getPackageName(),R.layout.widget_layout);
			
			int appWidgetId = _appWidgetIds[i];
			
			ContentResolver contentResolver = _context.getContentResolver();
		    Cursor cursor = contentResolver.query(Uri.withAppendedPath(AppProvider.APPS_URI, "/1"), null, null, null, null);
		  
		    String name = null;
		    String developer = null;
		    
		    if (cursor.getCount() > 0)
		    {	
		    	cursor.moveToFirst();
				name = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_NAME)); 
		    	developer = cursor.getString(cursor.getColumnIndex(DatabaseAdapter.KEY_DEVELOPER));
		    }
		    else
		    {
		    	name = _context.getResources().getString(R.string.widget_no_name);
		    	developer = _context.getResources().getString(R.string.widget_no_developer);
		    }		    
		    
		    cursor.close();
			
			views.setTextViewText(R.id.app_widget_name, name);
			views.setTextViewText(R.id.app_widget_developer, developer);
			_appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
}
