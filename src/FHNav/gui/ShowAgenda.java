package FHNav.gui;

import java.util.ArrayList;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShowAgenda extends Activity {
	/** Called when the activity is first created. */
	ListView lv1;
	private ArrayList<Order> m_orders = null;
	public OrderAdapter m_adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showagenda);
		getWindow().setBackgroundDrawableResource(R.drawable.bg2);
		
		m_orders = new ArrayList<Order>();
		getOrders();
		if (getIntent().getBooleanExtra("EXIT", false)) {
			Order o = new Order();
			o.setOrderName("11.1.2011 10:15 A.E.02");
			o.setOrderStatus("Algebra 1 V");
			m_orders.add(2, o);
			}
		
		this.m_adapter = new OrderAdapter(this, R.layout.row, m_orders);
		
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
	
	private void getOrders() {
		m_orders = new ArrayList<Order>();
		Order o1 = new Order();
		o1.setOrderName("10.1.2011 8:30 A.E.01");
		o1.setOrderStatus("Softwaretechnik 1 V");
		Order o2 = new Order();
		o2.setOrderName("10.1.2011 10:15 A.1.01");
		o2.setOrderStatus("DV-Infrastruktur V");
		Order o3 = new Order();
		o3.setOrderName("11.1.2011 10:15 A.E.02");
		o3.setOrderStatus("Datenbanken 1 ");
		Order o4 = new Order();
		o4.setOrderName("11.1.2011 14:15 A.E.02");
		o4.setOrderStatus("Statistik V");
		Order o5 = new Order();
		o5.setOrderName("13.1.2011 14:15 B.E.21");
		o5.setOrderStatus("Technisches Englisch");
		Order o6 = new Order();
		o6.setOrderName("13.1.2011 10:15 A.1.01");
		o6.setOrderStatus("Softwaretechnik 1 P");
		Order o7 = new Order();
		o7.setOrderName("14.1.2011 8:30 A.E.02");
		o7.setOrderStatus("Präsentationstechniken");
		Order o8 = new Order();
		o8.setOrderName("14.1.2011 10:15 A.E.02");
		o8.setOrderStatus("KEER");
		Order o9 = new Order();
		o9.setOrderName("14.1.2011 14:15 A.E.02");
		o9.setOrderStatus("Mikro/Makro");
		m_orders.add(o1);
		m_orders.add(o2);
//		m_orders.add(o3);
		m_orders.add(o4);
		m_orders.add(o6);
//		m_orders.add(o7);
//		m_orders.add(o8);
//		m_orders.add(o9);
	}

	private class OrderAdapter extends ArrayAdapter<Order> {

		private ArrayList<Order> items;

		public OrderAdapter(Context context, int textViewResourceId,
				ArrayList<Order> items) {
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
			Order o = items.get(position);
			if (o != null) {
				TextView tt = (TextView) v.findViewById(R.id.toptext);
				TextView bt = (TextView) v.findViewById(R.id.bottomtext);
				if (tt != null) {
					tt.setText(o.getOrderName());
				}
				if (bt != null) {
					bt.setText(o.getOrderStatus());
				}
			}
			return v;
		}
	}
}
