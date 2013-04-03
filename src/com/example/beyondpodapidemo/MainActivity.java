/*
** Copyright 2013, BeyondPod Team
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
**     http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the License for the specific language governing permissions and
** limitations under the License.
*
*  You can find the latest version of BeyondPod's public API at: 
*  http://www.beyondpod.mobi/android/help/FAQAPIs.htm 
*/


package com.example.beyondpodapidemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity
{

	TextView _Console;
	Button _Play, _Pause;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_Console = (TextView) findViewById(R.id.console);
		
		_Play = (Button)findViewById(R.id.play);
		_Pause = (Button)findViewById(R.id.pause);
		
		
		_Play.setOnClickListener(_TrasportControlsListener);
		_Pause.setOnClickListener(_TrasportControlsListener);
		
	}
	
	
	protected void onResume() {
		
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction("mobi.beyondpod.action.PLAYBACK_STATUS");
		registerReceiver(mReceiver, filter);
	};
	
	protected void onPause() {
		
		super.onPause();
		unregisterReceiver(mReceiver);
	};

	View.OnClickListener _TrasportControlsListener = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			String cmd= null;
			switch(v.getId())
			{
			case R.id.play:
				cmd = "mobi.beyondpod.command.PLAY";
				break;
				
			case R.id.pause:
				cmd = "mobi.beyondpod.command.PAUSE";
				break;
			}
			
			if(cmd != null)
			{
				Intent ibp = new Intent(cmd);
				sendBroadcast(ibp);
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			boolean playing = intent.getBooleanExtra("playing", false);
			String feedname = intent.getStringExtra("feed-name");
			String feedurl = intent.getStringExtra("feed-url");
			String episodeName = intent.getStringExtra("episode-name");
			String episodeUrl = intent.getStringExtra("episode-url");
			String episodeFile = intent.getStringExtra("episode-file");
			String episodePostUrl = intent.getStringExtra("episode-post-url");
			String episodeMime = intent.getStringExtra("episode-mime");
			String episodeSummary = intent.getStringExtra("episode-summary");
			long episodeDuration = intent.getLongExtra("episode-duration", -1);
			long episodePosition = intent.getLongExtra("episode-position", -1);

			_Console.setText("Playing          :" + playing + "\n" + "Feed Name        :" + feedname + "\n"
					+ "Feed Url         :" + feedurl + "\n" + "Episode Name     :" + episodeName + "\n"
					+ "Episode Url      :" + episodeUrl + "\n" + "Episode File     :" + (episodeFile == null ? "N/A" : episodeFile) + "\n"
					+ "Episode Post Url :" + episodePostUrl + "\n" + "Episode Mime     :" + episodeMime + "\n"
					+ "Episode Summary  :" + episodeSummary + "\n" + "Episode Duration :" + episodeDuration + " s.\n"
					+ "Episode Position :" + episodePosition + " s.\n");
		}
	};
}
