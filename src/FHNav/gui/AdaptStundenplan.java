package FHNav.gui;

import java.util.ArrayList;

import FHNav.controller.MainApplicationManager;
import FHNav.gui.helper.ExtendedListAdapter;
import FHNav.gui.helper.NormalListAdapter;
import FHNav.model.Veranstaltung;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

public class AdaptStundenplan extends Activity{
	
	private ArrayList<Veranstaltung> veranstaltungen;
	public ExtendedListAdapter extendedListAdapter;
	ListView lv1;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.adaptstundenplan);
		
		veranstaltungen = MainApplicationManager.getVeranstaltungen();

		extendedListAdapter = new ExtendedListAdapter(this, veranstaltungen);			
		lv1 = (ListView) findViewById(R.id.adaptstundenplan_list);
		lv1.setAdapter(extendedListAdapter);
		
		
		
//		Spinner s1=(Spinner) findViewById(R.id.);
//		ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
//			new String[] {"Agenda Ansicht", "Stundenplan Ansicht"});
//			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//			s1.setAdapter(adapter);
			

	}
}
