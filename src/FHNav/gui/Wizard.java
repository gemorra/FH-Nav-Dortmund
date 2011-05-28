package FHNav.gui;

import java.util.ArrayList;

import FHNav.controller.IOManager;
import FHNav.controller.MainApplicationManager;
import FHNav.controller.PHPConnector;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Wizard extends Activity implements Runnable {

	public static final String PREFS_NAME = "settings";
	SharedPreferences preferences;
	ArrayList<String> spinnerContent;
	Spinner spinner1;
	ProgressDialog dialog;
	boolean loadSpinner = true;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.wizard);
		getWindow().setBackgroundDrawableResource(R.drawable.b2obenunten);
		AlertDialog.Builder adb = new AlertDialog.Builder(Wizard.this);
		adb.setTitle(R.string.wizard_alert_title);
		adb.setMessage(R.string.wizard_alert_message);
		adb.setPositiveButton(R.string.wizard_alert_positiveButton, new DialogInterface.OnClickListener() {


			public void onClick(DialogInterface dialog2, int which) {
				loadSpinner = true;
				dialog = ProgressDialog.show(Wizard.this, "", "Download...", true);
				Thread t1 = new Thread(Wizard.this);
				t1.start();
				
			}
		});
		adb.show();

		



		Button btn = (Button) this.findViewById(R.id.WizardOK);
		btn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (spinnerContent.size() >= 1) {

					Log.e("inhalt", (String) (spinner1.getSelectedItem()));

					loadSpinner = false;
					dialog = ProgressDialog.show(Wizard.this, "", "Download...", true);
					Thread t = new Thread(Wizard.this);
					t.start();
				}
			}
		});

	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			spinner1 = (Spinner) Wizard.this.findViewById(R.id.Spinner01);
			ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
					Wizard.this, android.R.layout.simple_dropdown_item_1line,
					spinnerContent);
			adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner1.setAdapter(adapter1);
			dialog.dismiss();
			if(spinnerContent.size()<1)
			{
				AlertDialog.Builder adb = new AlertDialog.Builder(Wizard.this);
				adb.setTitle(R.string.wizard_error_title);
				adb.setMessage(R.string.wizard_error_message);
				adb.setPositiveButton(R.string.wizard_error_positiveButton, null);
				adb.show();
			}
		}
	};

	public void run() {
		if (!loadSpinner) {
			MainApplicationManager.setStundenplan(PHPConnector
					.getStundenplanFromMysql((String) (spinner1
							.getSelectedItem())));

			if (MainApplicationManager.getStundenplan().getVeranstaltungen()
					.size() > 0) {
				IOManager.saveStundenplan(MainApplicationManager
						.getStundenplan());
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean("wizardDone", true);
				editor.commit();

				startActivity(new Intent(Wizard.this, MainMenu.class));
				
			}
			dialog.dismiss();
		} else {
			spinnerContent = PHPConnector.getAllBranches();
			Message msg = handler.obtainMessage();
			handler.sendMessage(msg);
		}
	}
}
