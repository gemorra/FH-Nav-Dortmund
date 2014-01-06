package FHNav.gui;

import java.util.ArrayList;

import FHNav.controller.IOManager;
import FHNav.controller.MainApplicationManager;
import FHNav.controller.PHPConnector;
import FHNav.controller.SettingsManager;
import FHNav.model.Stundenplan;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

public class Wizard extends Activity implements Runnable {

	public static final String PREFS_NAME = "settings";
	ArrayList<String> spinnerContent;
	Spinner spinner1;
	ProgressDialog dialog;
	boolean loadSpinner = true;
	boolean handle1 = true;

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (handle1) {
				if (spinnerContent.size() < 1) {
					// Verbindungsfehler Dialog:
					final Dialog error_dialog = new Dialog(Wizard.this);

					error_dialog
							.setContentView(R.layout.alert_dialog_connection_problem);
					error_dialog
							.setTitle(R.string.alert_dialog_connection_problem_title);
					// final EditText et = (EditText)
					// error_dialog.findViewById(R.id.alert_dialog_connection_problem_editText);
					Button btn = (Button) error_dialog
							.findViewById(R.id.alert_dialog_connection_problem_button);
					// et.setText(SettingsManager.getPathToFile());
					btn.setOnClickListener(new OnClickListener() {
						// Anderen Pfad angeben und neuer Versuch:
						public void onClick(View v) {
							// SettingsManager.setPathToFile(et.getText().toString());
							error_dialog.dismiss();
							loadSpinner = true;
							dialog = ProgressDialog.show(Wizard.this, "",
									"Download...", true);
							Thread t1 = new Thread(Wizard.this);
							t1.start();
						}
					});
					Button btn2 = (Button) error_dialog
							.findViewById(R.id.alert_dialog_connection_problem_buttonNext);
					btn2.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							new_blank_plan();
						}
					});

					dialog.dismiss();
					error_dialog.show();
				} else { // H1 Spinner laden
					spinner1 = (Spinner) Wizard.this
							.findViewById(R.id.Spinner01);
					ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
							Wizard.this,
							android.R.layout.simple_dropdown_item_1line,
							spinnerContent);
					adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					spinner1.setAdapter(adapter1);
					dialog.dismiss();
				}
			} else {
				// Verbindungsfehler Dialog:
				final Dialog error_dialog = new Dialog(Wizard.this);

				error_dialog
						.setContentView(R.layout.alert_dialog_connection_problem);
				error_dialog
						.setTitle(R.string.alert_dialog_connection_problem_title);
				// final EditText et = (EditText)
				// error_dialog.findViewById(R.id.alert_dialog_connection_problem_editText);
				Button btn = (Button) error_dialog
						.findViewById(R.id.alert_dialog_connection_problem_button);
				// et.setText(SettingsManager.getPathToFile());
				btn.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						// SettingsManager.setPathToFile(et.getText().toString());
						error_dialog.dismiss();
						loadSpinner = false;
						dialog = ProgressDialog.show(Wizard.this, "",
								"Download...", true);
						Thread t1 = new Thread(Wizard.this);
						t1.start();
					}
				});
				Button btn2 = (Button) error_dialog
						.findViewById(R.id.alert_dialog_connection_problem_buttonNext);
				btn2.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						new_blank_plan();
					}
				});
				dialog.dismiss();
				error_dialog.show();

			}
		}
	};

	public void new_blank_plan() {
		SettingsManager.setWizardDone(true, getApplicationContext());
		MainApplicationManager.setStundenplan(new Stundenplan());
		IOManager.saveStundenplan(MainApplicationManager.getStundenplan(),
				getApplicationContext());
		MainApplicationManager.setSelectedBranch("");
		startActivity(new Intent(Wizard.this, Menu.class));

	}

	@Override
	public void onBackPressed() {
		Log.e("Wizard", "Back");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("Wizard", "Create");

		setContentView(R.layout.wizard);
		getWindow().setBackgroundDrawableResource(R.drawable.bgunten);
		AlertDialog.Builder adb = new AlertDialog.Builder(Wizard.this);
		adb.setTitle(R.string.wizard_alert_title);
		adb.setMessage(R.string.wizard_alert_message);
		adb.setPositiveButton(R.string.wizard_alert_positiveButton,
				new DialogInterface.OnClickListener() {

					// Nach ok, wird versucht die Stundenplanliste zu laden
					// (siehe run()
					// unten) => T1
					public void onClick(DialogInterface dialog2, int which) {
						loadSpinner = true;
						dialog = ProgressDialog.show(Wizard.this, "",
								"Download...", true);
						Thread t1 = new Thread(Wizard.this);
						t1.start();

					}
				});
		adb.setNegativeButton(R.string.wizard_skip_button,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						new_blank_plan();
					}
				});
		adb.show();

		// Bestätigungsbutton => Laden des Stundenplans...
		Button btn = (Button) this.findViewById(R.id.WizardOK);
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (spinnerContent.size() >= 1) {
					loadSpinner = false;
					dialog = ProgressDialog.show(Wizard.this, "",
							"Download...", true);
					Thread t = new Thread(Wizard.this);
					t.start();
				}
			}
		});

		ImageButton btn1 = (ImageButton) this.findViewById(R.id.wizard_refresh);
		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				loadSpinner = true;
				dialog = ProgressDialog.show(Wizard.this, "", "Download...",
						true);
				Thread t1 = new Thread(Wizard.this);
				t1.start();
			}
		});
	}

	public void onStart() {
		super.onStart();
		Log.e(this.getClass().toString(), "Start");
		if (MainApplicationManager.isFinish()) {
			finish();
		}

		// FlurryAgent.onStartSession(this, "I7RRJ22MKL64Q9JLNZW8");

	}

	public void onStop() {
		super.onStop();
		// FlurryAgent.onEndSession(this);
		Log.e(this.getClass().toString(), "Stop");
	}

	public void run() {
		if (!loadSpinner) {
			try {
				String selectedBranch = (String) (spinner1.getSelectedItem());
				MainApplicationManager.setStundenplan(PHPConnector
						.getStundenplanFromMysql(
								(String) (spinner1.getSelectedItem()),
								getApplicationContext()));
				if (MainApplicationManager.getStundenplan()
						.getVeranstaltungen().size() > 0) {
					IOManager.saveStundenplan(
							MainApplicationManager.getStundenplan(),
							getApplicationContext());
					SettingsManager
							.setWizardDone(true, getApplicationContext());
					MainApplicationManager.setSelectedBranch(selectedBranch);
					startActivity(new Intent(Wizard.this, Menu.class));
				} else {
					Message msg = handler.obtainMessage();
					handle1 = false;
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
			}

		} else {
			// T1 Liste der Stundenpläne downloaden und bei => H1 in Spinner
			// packen
			try {
				spinnerContent = PHPConnector
						.getAllBranches(getApplicationContext());
				Message msg = handler.obtainMessage();
				handle1 = true;
				handler.sendMessage(msg);
			} catch (Exception e) {
			} finally {
				dialog.dismiss();
			}

		}
	}
}
