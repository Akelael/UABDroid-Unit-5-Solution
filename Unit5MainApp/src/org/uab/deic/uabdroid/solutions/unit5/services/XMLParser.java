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

import java.net.URL;

import org.xml.sax.Attributes;

import android.sax.Element;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Xml;

public class XMLParser 
{
	public static String parseText()
	{
		try 
		{
			URL url = new URL("http://deic.uab.cat/~rserrano/android/app/info.xml");
			final Result controlAndResult = new Result(false); 
			
			RootElement root = new RootElement("info");
			
			Element item = root.getChild("versio");
			item.setStartElementListener(new StartElementListener()
			{
				@Override
				public void start(Attributes _attributes) 
				{
					if (_attributes.getValue("lang").equalsIgnoreCase("es_ES"))
					{
						controlAndResult.setControl(true);
					}
					else
					{
						controlAndResult.setControl(false);
					}
				}
			});
			
			item.getChild("general").setEndTextElementListener(new EndTextElementListener()
			{
				@Override
				public void end(String _text) 
				{
					if (controlAndResult.getControl())
					{
						controlAndResult.setResult(_text);
					}
				}
			});
			
			Xml.parse(url.openConnection().getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
			
			return controlAndResult.getResult();		
		} 
		catch (Exception e) 
		{
			return null;
		}
	}
	
	private static class Result
	{
		private boolean mControl = false;
		private String mResult = null;
		
		public Result(boolean _control)
		{
			setControl(_control);
		}

		public void setResult(String _text) 
		{
			this.mResult = _text;
		}

		public String getResult() 
		{
			return mResult;
		}

		public void setControl(boolean _control) 
		{
			this.mControl = _control;
		}

		public boolean getControl() 
		{
			return mControl;
		}

	}
}
