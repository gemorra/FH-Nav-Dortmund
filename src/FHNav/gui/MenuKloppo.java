package FHNav.gui;

import java.util.ArrayList;

import FHNav.controller.IOManager;
import FHNav.controller.MainApplicationManager;
import FHNav.gui.helper.ExtendedListAdapter;
import FHNav.gui.helper.NormalListAdapter;
import FHNav.model.Veranstaltung;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MenuKloppo extends Activity {
	
	Intent show;
	Intent wizard;
	SharedPreferences pref;
	private ArrayList<Veranstaltung> veranstaltungen;
	public NormalListAdapter normalListAdapter;
	ListView lv1;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MainApplicationManager.setCtx(getApplicationContext());
		show = new Intent(MenuKloppo.this, ShowAgenda.class);
		wizard = new Intent(MenuKloppo.this, Wizard.class);
		pref = PreferenceManager
			.getDefaultSharedPreferences(this);

		if (pref.getBoolean("wizardDone", false)) {
			setContentView(R.layout.menukloppo);
			getWindow().setBackgroundDrawableResource(R.drawable.bg2);
			MainApplicationManager.setStundenplan(IOManager.loadStundenplan());
			
			veranstaltungen = getVeranstaltungen();

			this.normalListAdapter = new NormalListAdapter(this, veranstaltungen);
//			
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
					    	ExtendedListAdapter ead = new ExtendedListAdapter(MenuKloppo.this, veranstaltungen);
					        lv1.setAdapter(ead);
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
	private ArrayList<Veranstaltung> getVeranstaltungen() {
		ArrayList<Veranstaltung> m_orders = new ArrayList<Veranstaltung>();
		
		if(MainApplicationManager.getStundenplan().getVeranstaltungen().size()>990){		
			for(int i=0; i<990; i++)
			{
				m_orders.add(MainApplicationManager.getStundenplan().getVeranstaltungen().get(i));
			}
			return m_orders;
		}
		else
		return MainApplicationManager.getStundenplan().getVeranstaltungen();
			
		
	}
}
