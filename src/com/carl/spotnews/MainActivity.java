package com.carl.spotnews;

import java.io.IOException;

import android.R.bool;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity implements OnClickListener {

	ImageButton btnPlay;
	ImageButton btnLike;
	ImageButton btnDislike;
	
	ImageButton btn1;
	ImageButton btn2;
	ImageButton btn3;
	
	boolean b1;
	boolean b2;
	boolean b3;

	boolean isPlaying;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);

		isPlaying = false;		

		btnPlay = (ImageButton) findViewById(R.id.btnPlay);
		btnLike = (ImageButton) findViewById(R.id.btnLike);
		btnDislike = (ImageButton) findViewById(R.id.btnDisLike);

		btnPlay.setOnClickListener(this);
		btnLike.setOnClickListener(this);
		btnDislike.setOnClickListener(this);
		
		btn1 = (ImageButton)findViewById(R.id.img1);
		btn2 = (ImageButton)findViewById(R.id.img2);
		btn3 = (ImageButton)findViewById(R.id.img3);
		
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		
		b1 = true;
		b2 = false;
		b3 = false;

	}

	
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnPlay) {
			if (!isPlaying) {
				
				startService(new Intent(this, BackgroundService.class));

				
				btnPlay.setImageResource(R.drawable.btnpaused);
			}else {
				
				stopService(new Intent(this, BackgroundService.class));
				
				// AudioManager am = (AudioManager)
				// getSystemService(Context.AUDIO_SERVICE);
				
				btnPlay.setImageResource(R.drawable.btnplay);
			}
			
			isPlaying = !isPlaying;
			
		}
		if(v.getId() == R.id.img1){
			if(b1)
				btn1.setImageResource(R.drawable.analysys_off);
			else
				btn1.setImageResource(R.drawable.analysys_on);
			b1 = !b1;
		}
		if(v.getId() == R.id.img2){
			if(b2)
				btn2.setImageResource(R.drawable.traffic_off);
			else
				btn2.setImageResource(R.drawable.traffic_on);
			b2 = !b2;
		}
		if(v.getId() == R.id.img3){
			if(b3)
				btn3.setImageResource(R.drawable.news_off);
			else
				btn3.setImageResource(R.drawable.news_on);
			b3 = !b3;
		}
	}

}
