package FHNav.gui;

import FHNav.controller.IOManager;
import FHNav.controller.MainApplicationManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {

	Intent show;
	Intent wizard;
	SharedPreferences pref;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MainApplicationManager.setCtx(getApplicationContext());
		show = new Intent(MainMenu.this, ShowAgenda.class);
		wizard = new Intent(MainMenu.this, Wizard.class);
		pref = PreferenceManager
			.getDefaultSharedPreferences(this);

		if (pref.getBoolean("wizardDone", false)) {
			setContentView(R.layout.mainmenu);
			getWindow().setBackgroundDrawableResource(R.drawable.logokloppo);
			MainApplicationManager.setStundenplan(IOManager.loadStundenplan());

		
			Button btn1;
			btn1 = (Button) findViewById(R.id.Button01);
			btn1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					startActivity(show);

				}
			});

			Button btn3;
			btn3 = (Button) findViewById(R.id.Button03);
			btn3.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					
					SharedPreferences.Editor editor = pref.edit();
					editor.putBoolean("wizardDone", false);
					editor.commit();
					startActivity(wizard);

				}
			});

			Button btn4;
			btn4 = (Button) findViewById(R.id.Button04);
			btn4.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					stopService(wizard);
					finish();
				}
			});
		} else {
			startActivity(new Intent(MainMenu.this, Wizard.class));
		}

	}

}
