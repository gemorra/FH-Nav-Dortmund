package FHNav.gui;

import java.util.Date;

import FHNav.controller.MainApplicationManager;
import FHNav.controller.SettingsManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;

public class SplashScreen extends Activity {

	long startTime;
	protected int _splashTime = 2000;
	int datarefresh = 12;

	public void onStart() {
		super.onStart();
		Log.e(this.getClass().toString(), "Start");
		FlurryAgent.onStartSession(this, "I7RRJ22MKL64Q9JLNZW8");
//		if(MainApplicationManager.isFinish())
//			finish();
	}

	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this);
		Log.e(this.getClass().toString(), "Stop");

	}

	TextView t1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		t1 = (TextView) findViewById(R.id.textView1);
		Date dt = new Date();
		startTime = dt.getTime();
		SettingsManager.loadSettings(this);
		MainApplicationManager.setCtx(getApplicationContext());
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				int waited = 0;

				
				
				try {
					while (waited < _splashTime) {
						sleep(100);
						waited += 100;
						Message msg = handler.obtainMessage();
						msg.arg1 = waited;
						handler.sendMessage(msg);
						//System.out.println("int:" + waited);
					}

				} catch (InterruptedException e) {
					// do nothing
				} finally {
					finish();
					startActivity(new Intent(SplashScreen.this, Menu.class));
				}

			}
		};
		splashTread.start();

		Thread splashTread2 = new Thread("Mensa Downloader Thread") {
			@Override
			public void run() {
				while (true) {
					try {
						MainApplicationManager.refreshData();
						int waited = 0;
						while (waited < datarefresh) {
							sleep(1000 * 3600);
							waited++;							
						}

					} catch (InterruptedException e) {
						// do nothing
					}
				}
			}
		};
		splashTread2.start();
	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
//System.out.println(msg.arg1);
			int waited = msg.arg1;
			if (t1 != null) {
				if (waited <= 1000) {
					t1.setText("Loading Data...");
				} else if (waited <= 1500) {
					t1.setText("Starting Daemons...");
				} else if (waited <= 2000) {
					t1.setText("Initialising GUI...");
				}
			}

		}
	};

}

// public void run() {
// boolean isRunning = true;
// while (isRunning) {
// Date dt = new Date();
// if (dt.getTime() - startTime > 3000) {
// isRunning = false;
// }
// else
// {
// try {
// Thread.sleep(100);
// } catch (InterruptedException e) {
// // TODO Auto-generated catch block
//
// }
// }
// }
// finish();
// startActivity(new Intent(SplashScreen.this, Menu.class));
// }

