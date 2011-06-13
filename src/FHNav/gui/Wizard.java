package FHNav.gui;

import java.util.ArrayList;

import FHNav.controller.IOManager;
import FHNav.controller.MainApplicationManager;
import FHNav.controller.PHPConnector;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

		// Vorbereitungen und Dialog anzeigen
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.wizard);
		getWindow().setBackgroundDrawableResource(R.drawable.b2obenunten);
		AlertDialog.Builder adb = new AlertDialog.Builder(Wizard.this);
		adb.setTitle(R.string.wizard_alert_title);
		adb.setMessage(R.string.wizard_alert_message);
		adb.setPositiveButton(R.string.wizard_alert_positiveButton, new DialogInterface.OnClickListener() {

			// Nach ok, wird versucht die Stundenplanliste zu laden (siehe run()
			// unten) => T1
			public void onClick(DialogInterface dialog2, int which) {
				loadSpinner = true;
				dialog = ProgressDialog.show(Wizard.this, "", "Download...", true);
				Thread t1 = new Thread(Wizard.this);
				t1.start();

			}
		});
		adb.show();

		//Bestätigungsbutton => Laden des Stundenplans...
		Button btn = (Button) this.findViewById(R.id.WizardOK);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (spinnerContent.size() >= 1) {
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
			if (spinnerContent.size() < 1) {
				// Verbindungsfehler Dialog:
				final Dialog error_dialog = new Dialog(Wizard.this);

				error_dialog.setContentView(R.layout.alert_dialog_connection_problem);
				error_dialog.setTitle(R.string.alert_dialog_connection_problem_title);
				final EditText et = (EditText) error_dialog.findViewById(R.id.alert_dialog_connection_problem_editText);
				Button btn = (Button) error_dialog.findViewById(R.id.alert_dialog_connection_problem_button);
				et.setText(PHPConnector.getPathToFile());
				btn.setOnClickListener(new OnClickListener() {
					//Anderen Pfad angeben und neuer Versuch:
					public void onClick(View v) {
						SharedPreferences.Editor editor = preferences.edit();
						editor.putString("pathToFile", et.getText().toString());
						editor.commit();
						PHPConnector.setPathToFile(preferences.getString("pathToFile", "http://gemorra.de/test.php"));
						error_dialog.dismiss();
						loadSpinner = true;
						dialog = ProgressDialog.show(Wizard.this, "", "Download...", true);
						Thread t1 = new Thread(Wizard.this);
						t1.start();
					}
				});

				error_dialog.show();
			} else { // H1 Spinner laden
				spinner1 = (Spinner) Wizard.this.findViewById(R.id.Spinner01);
				ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Wizard.this, android.R.layout.simple_dropdown_item_1line, spinnerContent);
				adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner1.setAdapter(adapter1);
				dialog.dismiss();
			}
		}
	};

	public void run() {
		if (!loadSpinner) {
			MainApplicationManager.setStundenplan(PHPConnector.getStundenplanFromMysql((String) (spinner1.getSelectedItem())));

			if (MainApplicationManager.getStundenplan().getVeranstaltungen().size() > 0) {
				IOManager.saveStundenplan(MainApplicationManager.getStundenplan());
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean("wizardDone", true);
				editor.commit();

				startActivity(new Intent(Wizard.this, Menu.class));

			}
			dialog.dismiss();
		} else {
			// T1 Liste der Stundenpläne downloaden und bei => H1 in Spinner
			// packen
			spinnerContent = PHPConnector.getAllBranches();
			Message msg = handler.obtainMessage();
			handler.sendMessage(msg);

		}
	}
}
