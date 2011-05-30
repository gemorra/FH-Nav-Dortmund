package FHNav.gui;

import java.util.ArrayList;

import FHNav.controller.IOManager;
import FHNav.controller.MainApplicationManager;
import FHNav.gui.helper.ExtendedListAdapter;
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
import android.widget.Button;
import android.widget.ListView;

public class MenuKloppo extends Activity {
	
	Intent show;
	Intent wizard;
	Intent adaptstundenplan;
	SharedPreferences pref;
	private ArrayList<Veranstaltung> veranstaltungen;
	public NormalListAdapter normalListAdapter;
	ListView lv1;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MainApplicationManager.setCtx(getApplicationContext());
		show = new Intent(MenuKloppo.this, ShowAgenda.class);
		adaptstundenplan = new Intent(MenuKloppo.this, AdaptStundenplan.class);
		wizard = new Intent(MenuKloppo.this, Wizard.class);
		pref = PreferenceManager
			.getDefaultSharedPreferences(this);

		if (pref.getBoolean("wizardDone", false)) {
			setContentView(R.layout.menukloppo);
			getWindow().setBackgroundDrawableResource(R.drawable.bg2);
			MainApplicationManager.setStundenplan(IOManager.loadStundenplan());
			
			veranstaltungen = MainApplicationManager.getVeranstaltungen();

			normalListAdapter = new NormalListAdapter(this, veranstaltungen);			
			lv1 = (ListView) findViewById(R.id.listView1);
			lv1.setAdapter(normalListAdapter);
		
			Button btn1;
			btn1 = (Button) findViewById(R.id.Button01);
			btn1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					final CharSequence[] items = {"Ansicht wechseln", "Bearbeiten", "Übertragen"};

					AlertDialog.Builder builder = new AlertDialog.Builder(MenuKloppo.this);
					builder.setTitle("Stundenplan anpassen");
					builder.setItems(items, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	if(items[item].equals("Bearbeiten")){
					    		startActivity(adaptstundenplan);
					        }
					    }
					});
					AlertDialog alert = builder.create();
					alert.show();
				}
			});


			Button btn2;
			btn2 = (Button) findViewById(R.id.Button02);
			btn2.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					stopService(wizard);
					finish();
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
		} else {
			startActivity(new Intent(MenuKloppo.this, Wizard.class));
		}

	}
	
	
}
