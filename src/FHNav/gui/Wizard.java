package FHNav.gui;

import java.util.ArrayList;

import FHNav.controller.MainApplicationManager;
import FHNav.controller.PHPConnector;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Wizard extends Activity {

	public static final String PREFS_NAME = "settings";
	SharedPreferences preferences;
	ArrayList<String> spinnerContent;
	Spinner spinner1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MainApplicationManager.setCtx(getApplicationContext());
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.wizard);

		AlertDialog.Builder adb = new AlertDialog.Builder(Wizard.this);
		adb.setTitle(R.string.wizard_alert_title);
		adb.setMessage(R.string.wizard_alert_message);
		adb.setPositiveButton(R.string.wizard_alert_positiveButton, null);
		adb.show();

		setContentView(R.layout.wizard);
		getWindow().setBackgroundDrawableResource(R.drawable.b2obenunten);

		spinnerContent = PHPConnector.getAllBranches();
		spinner1 = (Spinner) this.findViewById(R.id.Spinner01);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, spinnerContent);

		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter1);

		Spinner spinner2 = (Spinner) this.findViewById(R.id.Spinner02);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] { "1", "3",
						"5" });
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter2);

		Button btn = (Button) this.findViewById(R.id.WizardOK);
		btn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if (spinnerContent.size() >= 1) {
					
					Log.e("inhalt", (String) (spinner1.getSelectedItem()));
					SharedPreferences.Editor editor = preferences.edit();
					MainApplicationManager.setStundenplan(PHPConnector.getStundenplanFromMysql((String) (spinner1.getSelectedItem())));
					if (MainApplicationManager.getStundenplan().getVeranstaltungen().size() >0 ) {
						editor.putBoolean("wizardDone", true);
						editor.commit();
						startActivity(new Intent(Wizard.this, MainMenu.class));

					}
				}
			}
		});

	}
}
