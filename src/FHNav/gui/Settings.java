package FHNav.gui;

import FHNav.controller.SettingsManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;

public class Settings extends Activity {

	Intent wizard;
	
	Button restartWizardButton;
	Spinner textSizeSpinner;
	CheckBox lecturerCheckbox;
	CheckBox groupletterCheckbox;
	CheckBox typeCheckbox;
	EditText pathToFileEditText;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.settings);
		
		wizard = new Intent(Settings.this, Wizard.class);
		restartWizardButton = (Button) findViewById(R.id.settings_restart_wizard_button);
		restartWizardButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				SettingsManager.setWizardDone(false);
				startActivity(wizard);		
			}
		});
		
		
		textSizeSpinner = (Spinner) this.findViewById(R.id.settings_text_size_spinner);

		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new String[] {
				getString(R.string.settings_test_size_0), getString(R.string.settings_test_size_1), getString(R.string.settings_test_size_2) });
		textSizeSpinner.setPromptId(R.string.settings_text_size);
		textSizeSpinner.setAdapter(adapter2);
		textSizeSpinner.setSelection(SettingsManager.getText_size());
		textSizeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				SettingsManager.setText_size(arg2);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		groupletterCheckbox = (CheckBox) findViewById(R.id.settings_lecture_details_groupletter_checkbox);
		groupletterCheckbox.setChecked(SettingsManager.isLecture_details_groupletter());
		groupletterCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SettingsManager.setLecture_details_groupletter(isChecked);
			}
		});

		lecturerCheckbox = (CheckBox) findViewById(R.id.settings_lecture_details_lecturer_checkbox);
		lecturerCheckbox.setChecked(SettingsManager.isLecture_details_lecturer());
		lecturerCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SettingsManager.setLecture_details_lecturer(isChecked);
			}
		});

		typeCheckbox = (CheckBox) findViewById(R.id.settings_lecture_details_type_checkbox);
		typeCheckbox.setChecked(SettingsManager.isLecture_details_type());
		typeCheckbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				SettingsManager.setLecture_details_type(isChecked);
			}
		});

		pathToFileEditText = (EditText) findViewById(R.id.settings_pathToFile_edit_text);
		pathToFileEditText.setText(SettingsManager.getPathToFile());
		

	}

}
