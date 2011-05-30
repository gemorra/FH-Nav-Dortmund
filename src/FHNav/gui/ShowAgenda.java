package FHNav.gui;

import java.util.ArrayList;

import FHNav.controller.MainApplicationManager;
import FHNav.gui.helper.ExtendedListAdapter;
import FHNav.model.Veranstaltung;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class ShowAgenda extends Activity {
	/** Called when the activity is first created. */
	ListView lv1;
	private ArrayList<Veranstaltung> veranstaltungen;
	public ExtendedListAdapter extendedListAdapter;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showagenda);
		getWindow().setBackgroundDrawableResource(R.drawable.bg2);
		
		veranstaltungen = getVeranstaltungen();

		this.extendedListAdapter = new ExtendedListAdapter(this, veranstaltungen);
		
		lv1 = (ListView) findViewById(R.id.ListView01);
		lv1.setAdapter(extendedListAdapter);

	}
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
		super.onCreateContextMenu(menu, v, menuInfo);  
		    menu.setHeaderTitle("Aktion wählen");  
		    menu.add(0, v.getId(), 0, "Navigieren");  
		    menu.add(0, v.getId(), 0, "Ausblenden");
		    menu.add(0, v.getId(), 0, "Entfernen");
		    menu.add(0, v.getId(), 0, "Abbruch");
		}  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.new_game:
	    	startActivity(new Intent(ShowAgenda.this,AddVeranstaltung.class));
	    	break;
	    case R.id.quit:
	    	startActivity(new Intent(ShowAgenda.this,ShowPlan.class));
	    }
	    
	    return true;
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
