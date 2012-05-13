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

import org.uab.deic.uabdroid.solutions.unit5.FormAppActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class FormReceiver extends BroadcastReceiver 
{
	private static final String ADD_APP = "org.uab.deic.uabdroid.solutions.unit5.event.ADD_APP";
	
	@Override
	public void onReceive(Context _context, Intent _intent) 
	{
		String action = _intent.getAction();
		
		if (action.equalsIgnoreCase(ADD_APP))
		{
			Intent intent = new Intent(_context, FormAppActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			_context.startActivity(intent);
		}
	}
}
