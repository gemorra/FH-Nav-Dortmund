package FHNav.gui;

import FHNav.controller.IOManager;
import FHNav.controller.MainApplicationManager;
import FHNav.controller.SettingsManager;
import FHNav.model.Stundenplan;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;

public class Settings extends Activity {

	Intent wizard;

	Button restartWizardButton;
	Spinner textSizeSpinner;
	CheckBox lecturerCheckbox;
	CheckBox groupletterCheckbox;
	CheckBox typeCheckbox;

	// EditText pathToFileEditText;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.settings);

		wizard = new Intent(Settings.this, Wizard.class);
		restartWizardButton = (Button) findViewById(R.id.settings_restart_wizard_button);
		restartWizardButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				AlertDialog.Builder adb = new AlertDialog.Builder(Settings.this);
				adb.setTitle(R.string.settings_alert_title);
				adb.setMessage(R.string.settings_alert_message);
				adb.setPositiveButton(R.string.settings_alert_positiveButton,
						new DialogInterface.OnClickListener() {

							// Nach ok, wird versucht die Stundenplanliste zu
							// laden
							// (siehe run()
							// unten) => T1
							public void onClick(DialogInterface dialog2,
									int which) {
								SettingsManager.setWizardDone(false,
										getApplicationContext());
								MainApplicationManager
										.setStundenplan(new Stundenplan());
								IOManager.saveStundenplan(
										MainApplicationManager.getStundenplan(),
										getApplicationContext());
								startActivity(wizard);

							}
						});
				adb.setNegativeButton(R.string.settings_alert_negativeButton,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog2,
									int which) {
							}
						});
				adb.show();

			}
		});

		textSizeSpinner = (Spinner) this
				.findViewById(R.id.settings_text_size_spinner);

		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, new String[] {
						getString(R.string.settings_test_size_0),
						getString(R.string.settings_test_size_1),
						getString(R.string.settings_test_size_2) });

		textSizeSpinner.setPromptId(R.string.settings_text_size);
		textSizeSpinner.setAdapter(adapter2);
		textSizeSpinner.setSelection(SettingsManager
				.getText_size(getApplicationContext()));
		textSizeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		groupletterCheckbox = (CheckBox) findViewById(R.id.settings_lecture_details_groupletter_checkbox);
		groupletterCheckbox.setChecked(SettingsManager
				.isLecture_details_groupletter(getApplicationContext()));
		groupletterCheckbox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

					}
				});

		lecturerCheckbox = (CheckBox) findViewById(R.id.settings_lecture_details_lecturer_checkbox);
		lecturerCheckbox.setChecked(SettingsManager
				.isLecture_details_lecturer(getApplicationContext()));
		lecturerCheckbox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {

					}
				});

		typeCheckbox = (CheckBox) findViewById(R.id.settings_lecture_details_type_checkbox);
		typeCheckbox.setChecked(SettingsManager
				.isLecture_details_type(getApplicationContext()));
		typeCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

			}
		});

		Button btn1;
		btn1 = (Button) findViewById(R.id.Button01);
		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				save();
				startActivity(new Intent(Settings.this, Menu.class));

			}

		});

		Button btn2;
		btn2 = (Button) findViewById(R.id.Button02);
		btn2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				restore();

			}

		});

		Button btn3;
		btn3 = (Button) findViewById(R.id.Button03);
		btn3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				onBackPressed();

			}
		});
		// pathToFileEditText = (EditText)
		// findViewById(R.id.settings_pathToFile_edit_text);
		// pathToFileEditText.setText(SettingsManager.getPathToFile());

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

	public void restore() {
		textSizeSpinner.setSelection(1);
		groupletterCheckbox.setChecked(true);
		lecturerCheckbox.setChecked(true);
		typeCheckbox.setChecked(true);
		save();
	}

	public void save() {

		SettingsManager.setText_size(textSizeSpinner.getSelectedItemPosition(),
				getApplicationContext());
		SettingsManager.setLecture_details_groupletter(
				groupletterCheckbox.isChecked(), getApplicationContext());
		SettingsManager.setLecture_details_lecturer(
				lecturerCheckbox.isChecked(), getApplicationContext());
		SettingsManager.setLecture_details_type(typeCheckbox.isChecked(),
				getApplicationContext());
	}
}
