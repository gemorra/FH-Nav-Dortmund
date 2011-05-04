package FHNav.gui;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShowPreviewOfSelection extends Activity {
	private ArrayList<Order> m_orders = null;
	private OrderAdapter m_adapter;
	ListView lv1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showpreviewofselection);
		getWindow().setBackgroundDrawableResource(R.drawable.bg2);
		m_orders = new ArrayList<Order>();
		getOrders();
		this.m_adapter = new OrderAdapter(this, R.layout.row, m_orders);

		lv1 = (ListView) findViewById(R.id.ListView01);
		lv1.setAdapter(m_adapter);

		lv1.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> a, View v, int position,
					long id) {

				Order o = (Order) lv1.getItemAtPosition(position);
				String text = o.getOrderStatus();
				text = text.substring(0, text.length() - 1);

				AlertDialog.Builder adb = new AlertDialog.Builder(
						ShowPreviewOfSelection.this);
				adb.setTitle("Veranstaltung hinzugefügt:");
				adb.setMessage(text);
				adb.setPositiveButton("Ok", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						Intent exit_intent = new Intent(
								ShowPreviewOfSelection.this, ShowAgenda.class);
						exit_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						exit_intent.putExtra("EXIT", true); // just value to
															// indicate i want
															// to exit the
															// application
						startActivity(exit_intent);

					}
				});
				adb.show();

			}
		});

	}

	private void getOrders() {
		m_orders = new ArrayList<Order>();
		Order o1 = new Order();
		o1.setOrderName("Montags 8:30 A.E.01");
		o1.setOrderStatus("Physik 1 V");
		Order o2 = new Order();
		o2.setOrderName("Montags 10:15 A.1.01");
		o2.setOrderStatus("Elektrotechnik für Informatiker V");
		Order o3 = new Order();
		o3.setOrderName("Dienstags 10:15 A.E.02");
		o3.setOrderStatus("Algebra V");
		Order o4 = new Order();
		o4.setOrderName("Donnerstags 14:15 A.E.02");
		o4.setOrderStatus("Analysis 1 V");

		m_orders.add(o1);
		m_orders.add(o2);
		m_orders.add(o3);
		m_orders.add(o4);
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

		public String getString2AtPosition(int position) {
			Order o = items.get(position);
			return o.getOrderStatus();
		}

		public String getString1AtPosition(int position) {
			Order o = items.get(position);
			return o.getOrderName();
		}

	}
}
