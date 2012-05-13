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

import java.util.HashMap;

import org.uab.deic.uabdroid.solutions.unit5.DatabaseAdapter;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.LiveFolders;

public class AppProvider extends ContentProvider 
{
	private final static String PROVIDER_AUTHORTITY = "org.uab.deic.uabdroid.solutions.unit5.provider";
	private final static String APPS_PATH = "apps";
	private final static String LIVE_FOLDER_PATH = "live_folder";
	private final static String SINGLE_APP_PATH = APPS_PATH + "/#";
	
	private final static String APPS_URI_STR = "content://" + PROVIDER_AUTHORTITY + "/" + APPS_PATH;
	private static final String LIVE_FOLDER_STR = "content://" + PROVIDER_AUTHORTITY +"/" + LIVE_FOLDER_PATH;
	private final static String SINGLE_APP_URI_STR = "content://" + PROVIDER_AUTHORTITY + "/" + SINGLE_APP_PATH;
	
	public final static Uri APPS_URI = Uri.parse(APPS_URI_STR);
	public final static Uri LIVE_FOLDER_URI = Uri.parse(LIVE_FOLDER_STR);
	public final static Uri SINGLE_APP_URI = Uri.parse(SINGLE_APP_URI_STR);
	
	private final static int APPS_CODE = 1;
	private final static int LIVE_FOLDER_CODE = 2;
	private final static int SINGLE_APP_CODE = 3;
	
	private static final UriMatcher mUriMatcher;
	
	static
	{
		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI(PROVIDER_AUTHORTITY, APPS_PATH, APPS_CODE);
		mUriMatcher.addURI(PROVIDER_AUTHORTITY, LIVE_FOLDER_PATH, LIVE_FOLDER_CODE);
		mUriMatcher.addURI(PROVIDER_AUTHORTITY, SINGLE_APP_PATH, SINGLE_APP_CODE);
	}
	
	static final HashMap<String, String> LIVE_FOLDER_PROJECTION;
	static 
	{
		LIVE_FOLDER_PROJECTION = new HashMap<String, String>();
		LIVE_FOLDER_PROJECTION.put(LiveFolders._ID, DatabaseAdapter.KEY_ID + " AS " + LiveFolders._ID);
		LIVE_FOLDER_PROJECTION.put(LiveFolders.NAME, DatabaseAdapter.KEY_NAME + " AS " + LiveFolders.NAME);
		LIVE_FOLDER_PROJECTION.put(LiveFolders.DESCRIPTION, DatabaseAdapter.KEY_DEVELOPER + " AS " + LiveFolders.DESCRIPTION);
	}
	
	DatabaseAdapter mDatabaseAdapter;
	
	@Override
	public boolean onCreate() 
	{
		mDatabaseAdapter = new DatabaseAdapter(getContext());
		mDatabaseAdapter.open();
		return false;
	}
	
	@Override
	public String getType(Uri _uri) 
	{
		switch(mUriMatcher.match(_uri))
		{
			case APPS_CODE:
			case LIVE_FOLDER_CODE:
				return "vnd.android.cursor.dir/vnd.uab.deic.app";
			case SINGLE_APP_CODE:
				return "vnd.android.cursor.item/vnd.uab.deic.apps";
			default:
				throw new IllegalArgumentException("Unsupported URI:" + _uri);
		}
	}

	@Override
	public Cursor query(Uri _uri, String[] _projection, String _selection, String[] _selectionArgs, String _sortOrder) 
	{
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(DatabaseAdapter.DB_TABLE_FORM);
		
		switch(mUriMatcher.match(_uri))
		{
			case APPS_CODE:
				break;
			case LIVE_FOLDER_CODE:
				builder.setProjectionMap(LIVE_FOLDER_PROJECTION);
				break;
			case SINGLE_APP_CODE:
				builder.appendWhere(DatabaseAdapter.KEY_ID+"="+_uri.getPathSegments().get(1));
			default:
				break;
		}
		
		Cursor cursor = builder.query(mDatabaseAdapter.getDatabase(), _projection, _selection, _selectionArgs, null, null, _sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), _uri);
		
		return cursor;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) 
	{
		return 0;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) 
	{
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) 
	{
		return 0;
	}
}
