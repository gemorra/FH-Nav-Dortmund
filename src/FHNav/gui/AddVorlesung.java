package FHNav.gui;

import java.util.ArrayList;

import FHNav.controller.MainApplicationManager;
import FHNav.gui.helper.ExtendedListAdapter;
import FHNav.model.Veranstaltung;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class AddVorlesung extends Activity
{
	private ArrayList<Veranstaltung> veranstaltungen;
	public ExtendedListAdapter extendedListAdapter;
	ListView lv1;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.addvorlesung);
		
		Spinner spinner1 = (Spinner)this.findViewById(R.id.Spinner01);  
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,  
		         android.R.layout.simple_dropdown_item_1line,  
		         new String[] { "Ba. Praktische Informatik", "Ma. Praktische Informatik", "Ba. Wirtschaftsinformatik", "Ma. Wirtschaftsinformatik"});  
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		spinner1.setAdapter(adapter1); 
		
		veranstaltungen = MainApplicationManager.getVeranstaltungen();

		extendedListAdapter = new ExtendedListAdapter(this, veranstaltungen);			
		lv1 = (ListView) findViewById(R.id.addvorlesung_list);
		lv1.setAdapter(extendedListAdapter);
		
	}
}
