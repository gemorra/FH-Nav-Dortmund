package FHNav.gui;

import java.util.ArrayList;

import FHNav.controller.MainApplicationManager;
import FHNav.model.Veranstaltung;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShowAgenda extends Activity {
	/** Called when the activity is first created. */
	ListView lv1;
	private ArrayList<Veranstaltung> m_orders = null;
	public VeranstaltungAdapter m_adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showagenda);
//		getWindow().setBackgroundDrawableResource(R.drawable.bg2);
		
		m_orders = getVeranstaltungen();
		
		this.m_adapter = new VeranstaltungAdapter(this, R.layout.row, m_orders);
		
		lv1 = (ListView) findViewById(R.id.ListView01);
		lv1.setAdapter(m_adapter);
		registerForContextMenu(lv1);
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
		m_orders = new ArrayList<Veranstaltung>();
		
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

	private class VeranstaltungAdapter extends ArrayAdapter<Veranstaltung> {

		private ArrayList<Veranstaltung> items;

		public VeranstaltungAdapter(Context context, int textViewResourceId,
				ArrayList<Veranstaltung> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}
			Veranstaltung ve = items.get(position);
			if (ve != null) {
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt = (TextView) v.findViewById(R.id.bottomtext);
				if (tt != null) {
					tt.setText(ve.getName()+"["+ve.getType()+"]");
				}
				if (bt != null) {
					bt.setText(ve.getRaum() + "  " + ve.getDozent());
				}
			}
			return v;
		}
	}
}
