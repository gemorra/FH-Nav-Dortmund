package FHNav.gui;

import FHNav.controller.MainApplicationManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import de.fhdo.fhapp.timetable.TimeTableBeanService;
import de.fhdo.fhapp.timetable.test2;

public class Wizard extends Activity {
	
	public static final String PREFS_NAME = "settings";
	SharedPreferences preferences;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MainApplicationManager.setCtx(getApplicationContext());
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		setContentView(R.layout.wizard);
		
		AlertDialog.Builder adb = new AlertDialog.Builder(Wizard.this);
		adb.setTitle("Herzlich Willkommen");
		adb.setMessage("Dieser Wizard wird sie durch die erste Einrichtung führen.");
		adb.setPositiveButton("Los geht's!", null);
		adb.show();

		setContentView(R.layout.wizard);
		getWindow().setBackgroundDrawableResource(R.drawable.b2obenunten);
		Spinner spinner1 = (Spinner) this.findViewById(R.id.Spinner01);
		
		TimeTableBeanService ttbs = new TimeTableBeanService();
		test2 t2 = new test2();
//		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
//				android.R.layout.simple_dropdown_item_1line, ServiceConnector.getAllBranches());
//	
//		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinner1.setAdapter(adapter1);

		Spinner spinner2 = (Spinner) this.findViewById(R.id.Spinner02);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] { "1", "3",
						"5" });
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter2);

		Button btn = (Button) this.findViewById(R.id.WizardOK);
		btn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// SpeichernTest
				// Log.v("test", "test2");
				// Date d1 = new Date();
				// d1.setHours(12);
				// d1.setMinutes(0);
				// d1.setSeconds(0);
				// Date d2 = new Date();
				// d2.setHours(14);
				// d2.setMinutes(0);
				// d2.setSeconds(0);
				//
				//
				// Raum r1 = new Raum('C', 'E', 21);
				// Raum r2 = new Raum('C', 'E', 21);
				// Veranstaltung v1 = new Veranstaltung("Sachweh",
				// "SWT 2", 1, d1, d2, r1, "MI", 1);
				// Veranstaltung v2 = new Veranstaltung("Sachweh",
				// "SWT 1", 1, d1, d2, r2, "WI", 3);
				// Stundenplan stundenplan = new Stundenplan();
				// stundenplan.addVeranstaltung(v1);
				// stundenplan.addVeranstaltung(v2);
				// MainApplicationManager.setStundenplan(stundenplan);
				// IOManager.saveStundenplan(stundenplan);

				// LadenTest
				// MainApplicationManager.setStundenplan(IOManager.loadStundenplan());
				// int i = 1;
				// for(Veranstaltung ver:
				// MainApplicationManager.getStundenplan().getVeranstaltungen())
				// {
				//
				// Log.v("FHNAV",i + ".:" + ver.getName() + " bei " +
				// ver.getDozent());
				// i++;
				// }

				// IOManager.saveStundenplan(stundenplan);

				// Normal
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean("wizardDone", true);
				editor.commit();
				startActivity(new Intent(Wizard.this, MainMenu.class));

			}
		});

	}
}
