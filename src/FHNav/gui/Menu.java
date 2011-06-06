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
import android.widget.Button;
import android.widget.ListView;

public class Menu extends Activity {
	
	Intent wizard;
	Intent adaptstundenplan;
	SharedPreferences pref;
	private ArrayList<Veranstaltung> veranstaltungen;
	public NormalListAdapter normalListAdapter;
	ListView lv1;
	
	public void onResume()
	{
		super.onResume();
		if(normalListAdapter!=null)
		{
			normalListAdapter.notifyDataSetChanged();
		}
	}
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MainApplicationManager.setCtx(getApplicationContext());

		adaptstundenplan = new Intent(Menu.this, AdaptStundenplan.class);
		wizard = new Intent(Menu.this, Wizard.class);
		pref = PreferenceManager
			.getDefaultSharedPreferences(this);
		PHPConnector.setPathToFile(pref.getString("pathToFile", "http://gemorra.de/test.php"));
		
				
		if (pref.getBoolean("wizardDone", false)) {
			setContentView(R.layout.menu);
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
					final CharSequence[] items = {"Ansicht wechseln", "Neu", "Löschen", "Übertragen"};

					AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
					builder.setTitle("Stundenplan anpassen");
					builder.setItems(items, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	if(items[item].equals("Löschen")){
					    		startActivity(adaptstundenplan);
					        }
					    	if(items[item].equals("Neu")){
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
			startActivity(new Intent(Menu.this, Wizard.class));
		}

	}
	
	
}
