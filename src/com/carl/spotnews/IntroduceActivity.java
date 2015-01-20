package com.carl.spotnews;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class IntroduceActivity extends Activity implements OnClickListener {

	public ImageButton btnCheck;
	public Button btnStart;

	boolean isChecked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		isChecked = true;

		setContentView(R.layout.activity_introduce);

		SharedPreferences preference = getSharedPreferences("SpotNews",
				MODE_PRIVATE);
		if (preference.getBoolean("checked", true) == false) {
			Intent main = new Intent(this, MainActivity.class);
			startActivity(main);
			overridePendingTransition(0, 0);
		}

		btnCheck = (ImageButton) findViewById(R.id.btnCheck);
		btnStart = (Button) findViewById(R.id.btnStart);

		btnCheck.setOnClickListener(this);
		btnStart.setOnClickListener(this); 
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnCheck) {
			if (isChecked) {
				btnCheck.setImageResource(R.drawable.btnunchecked);
			} else {
				btnCheck.setImageResource(R.drawable.btnchecked);
			}
			isChecked = !isChecked;
			SharedPreferences preference = getSharedPreferences("SpotNews",
					MODE_PRIVATE);
			SharedPreferences.Editor editor = preference.edit();
			editor.putBoolean("checked", isChecked);
			editor.commit();  
		} else if (v.getId() == R.id.btnStart) {
			Intent main = new Intent(this, MainActivity.class);
			startActivity(main);
		}

	}

}
