package FHNav.gui;

import java.util.Date;

import FHNav.controller.AktuellesParser;
import FHNav.controller.MainApplicationManager;
import FHNav.controller.SettingsManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class SplashScreen extends Activity {

	long startTime;
	protected int _splashTime = 2000;
	int datarefresh = 12;

	PendingIntent pendingIntent;
	AktuellesParser aktuelles;

	TextView t1;

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// System.out.println(msg.arg1);
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

	private void initAlarm() {
		Intent myIntent = new Intent("FHNav.aktuellesbroadcast");
		pendingIntent = PendingIntent.getBroadcast(SplashScreen.this, 0,
				myIntent, 0);
		AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, 0,
				AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
		Log.d(this.getClass().toString(), "Set alarm");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(this.getClass().toString(), "Create");
		MainApplicationManager.setFinish(false);
		setContentView(R.layout.splashscreen);
		t1 = (TextView) findViewById(R.id.textView1);
		Date dt = new Date();
		startTime = dt.getTime();

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
						// System.out.println("int:" + waited);
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

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initAlarm();
	}

	public void onStart() {
		super.onStart();
		Log.e(this.getClass().toString(), "Start");

		if (MainApplicationManager.isFinish()) {
			finish();
		} else {
			// FlurryAgent.onStartSession(this, "I7RRJ22MKL64Q9JLNZW8");
			SettingsManager.loadSettings(getApplicationContext());
		}
	}

	public void onStop() {
		super.onStop();
		// FlurryAgent.onEndSession(this);
		Log.e(this.getClass().toString(), "Stop");

	}

}

