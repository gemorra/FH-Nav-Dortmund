package FHNav.gui;

import java.util.Date;

import FHNav.controller.MainApplicationManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends Activity {

	long startTime;
	protected int _splashTime = 1000;
	int datarefresh = 12;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);

		Date dt = new Date();
		startTime = dt.getTime();

		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					int waited = 0;
					while (waited < _splashTime) {
						sleep(100);
						waited += 100;
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

