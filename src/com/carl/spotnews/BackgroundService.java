package com.carl.spotnews;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.widget.Toast;

public class BackgroundService extends Service {

	MediaPlayer mediaPlayer;

	Timer timer;
	MyTimerTask myTimerTask;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();

		timer = new Timer();
		myTimerTask = new MyTimerTask();

	}

	class MyTimerTask extends TimerTask {

		@Override
		public void run() {

			mediaPlayer = new MediaPlayer();

			AssetFileDescriptor descriptor;
			try {
				descriptor = getAssets().openFd("01.mp3");
				mediaPlayer.setDataSource(descriptor.getFileDescriptor(),
						descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close();

				mediaPlayer.prepare();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

			int result = am.requestAudioFocus(focusChangeListener,
			// Use the music stream.
					AudioManager.STREAM_MUSIC,
					// Request permanent focus.
					AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

			if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
				mediaPlayer.setVolume(1f, 1f);
				mediaPlayer.start();
			}
			
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

					am.abandonAudioFocus(focusChangeListener);
				}
			});

		}

	}

	@Override
	public void onStart(Intent intent, int startId) {
		// Perform your long running operations here.

		Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();

		timer.schedule(myTimerTask, 20000);

	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
		AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		am.abandonAudioFocus(focusChangeListener);
		mediaPlayer.pause();
	}

	private OnAudioFocusChangeListener focusChangeListener = new OnAudioFocusChangeListener() {
		public void onAudioFocusChange(int focusChange) {
			AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			switch (focusChange) {

			case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK):
				// Lower the volume while ducking.
				mediaPlayer.setVolume(0.2f, 0.2f);
				break;
			case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT):
				mediaPlayer.pause();
				break;

			case (AudioManager.AUDIOFOCUS_LOSS):
				mediaPlayer.stop();
				break;

			case (AudioManager.AUDIOFOCUS_GAIN):
				// Return the volume to normal and resume if paused.
				mediaPlayer.setVolume(1f, 1f);
				mediaPlayer.start();
				break;
			default:
				break;
			}
		}
	};

}
