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
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

public class DatabaseCursorLoader extends AsyncTaskLoader<Cursor> 
{
	private DatabaseAdapter mDatabaseAdapter;
	private Cursor mCursor;
	
	public DatabaseCursorLoader(Context _context) 
	{
		super(_context);
		mDatabaseAdapter = new DatabaseAdapter(getContext());
		mDatabaseAdapter.open();
	}

	@Override
	protected void onStartLoading() 
	{	
		forceLoad();
		super.onStartLoading();
	}

	@Override
	public Cursor loadInBackground() 
	{	
		if ((mCursor != null)&&(!mCursor.isClosed()))
		{
			mCursor.close();
			mCursor = null;
		}
		
		mCursor = mDatabaseAdapter.getAllFormRegisters();
		
		return mCursor;
	}
	
	@Override 
	protected void onReset() 
	{
		super.onReset();

		onStopLoading();

		if ((mCursor != null)&&(!mCursor.isClosed()))
		{
			mCursor.close();
			mCursor = null;
		}
		
		mDatabaseAdapter.close();
		mDatabaseAdapter = null;
     }
}
