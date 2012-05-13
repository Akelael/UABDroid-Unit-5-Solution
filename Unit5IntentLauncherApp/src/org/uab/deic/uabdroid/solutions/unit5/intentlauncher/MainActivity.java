package org.uab.deic.uabdroid.solutions.unit5.intentlauncher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity 
{    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button)findViewById(R.id.button_open_form);
        button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View _view) 
			{
				startActivity(new Intent("org.uab.deic.uabdroid.solutions.unit5.action.OPEN_FORM"));
			}
        });
        
        button = (Button)findViewById(R.id.button_call_form);
        button.setOnClickListener(new OnClickListener()
        {
			@Override
			public void onClick(View _view) 
			{
				sendBroadcast(new Intent("org.uab.deic.uabdroid.solutions.unit5.event.ADD_APP"));
			}
        });
    }
}