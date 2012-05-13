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

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;

public class AppListActivity extends FragmentActivity
{
	private static final String[] COLUMNS_FROM = {DatabaseAdapter.KEY_NAME,DatabaseAdapter.KEY_DEVELOPER, DatabaseAdapter.KEY_DATE};
	private static final int[] VIEWS_TO = {R.id.item_textview_result_name, R.id.item_textview_result_developer, R.id.item_textview_result_date};
	
	private SimpleCursorAdapter mCursorAdapter; 
			
	@Override
	public void onCreate(Bundle _savedInstanceState) 
	{
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.listactivity);
		
		mCursorAdapter = new SimpleCursorAdapter(getBaseContext(), 
												 R.layout.results_list_item, 
												 null, 
												 COLUMNS_FROM, 
												 VIEWS_TO,
												 0);

		ListView listView = (ListView)findViewById(R.id.list_view);
		listView.setAdapter(mCursorAdapter);
		
		getSupportLoaderManager().initLoader(0, null, new CursorLoaderCallback());	
	}
	
	private class CursorLoaderCallback implements LoaderManager.LoaderCallbacks<Cursor>
	{
		@Override
		public Loader<Cursor> onCreateLoader(int _id, Bundle _args) 
		{
			return new DatabaseCursorLoader(getBaseContext());
		}

		@Override
		public void onLoadFinished(Loader<Cursor> _loader, Cursor _cursor) 
		{
			mCursorAdapter.swapCursor(_cursor);
		}

		@Override
		public void onLoaderReset(Loader<Cursor> _loader) 
		{
			mCursorAdapter.swapCursor(null);
		}
	}
}
