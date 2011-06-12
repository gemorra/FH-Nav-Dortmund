package FHNav.gui;

import java.util.ArrayList;

import FHNav.controller.IOManager;
import FHNav.controller.MainApplicationManager;
import FHNav.controller.PHPConnector;
import FHNav.gui.helper.NormalListAdapter;
import FHNav.model.Veranstaltung;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class Einstellungen extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.einstellungen);

		Spinner spinner1 = (Spinner) this.findViewById(R.id.Spinner01);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, new String[] {
						"Ba. Praktische Informatik",
						"Ma. Praktische Informatik",
						"Ba. Wirtschaftsinformatik",
						"Ma. Wirtschaftsinformatik" });

		Spinner spinner2 = (Spinner) this.findViewById(R.id.Spinner02);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, new String[] {
						"klein", "mittel", "groﬂ" });

		final CheckBox checkBox01 = (CheckBox) findViewById(R.id.checkbox01);
		if (checkBox01.isChecked()) {
			checkBox01.setChecked(false);
		}

		final CheckBox checkBox02 = (CheckBox) findViewById(R.id.checkbox02);
		if (checkBox02.isChecked()) {
			checkBox02.setChecked(false);
		}

		final CheckBox checkBox03 = (CheckBox) findViewById(R.id.checkbox03);
		if (checkBox03.isChecked()) {
			checkBox03.setChecked(false);
		}

		EditText et = (EditText) this.findViewById(R.id.editText01);
		et.setText("Eingabe");

	}
}
