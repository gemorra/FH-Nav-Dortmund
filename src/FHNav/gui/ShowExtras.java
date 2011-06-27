package FHNav.gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import FHNav.controller.CanteenBeanTest;
import FHNav.controller.IOManager;
import FHNav.controller.MainApplicationManager;
import FHNav.controller.Tools;
import FHNav.gui.helper.NormalListAdapterForMenu;
import FHNav.gui.helper.SeparatedListAdapter;
import FHNav.model.CanteenMenu;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ShowExtras extends Activity {

	BaseAdapter listAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.extras);
		MainApplicationManager.setStundenplan(IOManager.loadStundenplan());

		listAdapter = build_normal();
		ListView lv1 = (ListView) findViewById(R.id.listView1);
		lv1.setAdapter(listAdapter);
		lv1.setEmptyView(findViewById(R.id.empty));
		
		Button btn1;
		btn1 = (Button) findViewById(R.id.Button01);
		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {}
				
		});

		Button btn2;
		btn2 = (Button) findViewById(R.id.Button02);
		btn2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				CanteenBeanTest.getMensaplanJSON();
				// AlertDialog.Builder adb = new AlertDialog.Builder(Menu.this);
				// adb.setTitle("Navigation");
				// adb.setMessage("Comming soon...");
				// adb.setPositiveButton("  OK  ", new
				// DialogInterface.OnClickListener() {
				//
				// public void onClick(DialogInterface dialog2, int which) {
				// }
				// });
				// adb.show();
			}
		});

		Button btn3;
		btn3 = (Button) findViewById(R.id.Button03);
		btn3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

			}
		});

	}
	private BaseAdapter build_normal() {
		SeparatedListAdapter separatedListAdapter = new SeparatedListAdapter(this);
		ArrayList<CanteenMenu> menus = CanteenBeanTest.getMenus();		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		
		NormalListAdapterForMenu[] els = new NormalListAdapterForMenu[7];
		for (int i = 0; i < els.length; i++) {
			els[i] = new NormalListAdapterForMenu(this, new ArrayList<CanteenMenu>());
		}
		for (CanteenMenu cm : menus) {
			els[cm.getDate().getDay()].getItems().add(cm);
		}
		for (int i = 0; i < els.length; i++) {
			if (els[i].getItems().size() > 0) {
				String header = getString(Tools.getWeekday(els[i].getItems().get(0).getDate().getDay()))+", der " + sdf.format(els[i].getItems().get(0).getDate());
				separatedListAdapter.addSection(header, els[i]);
			}
		}
		return separatedListAdapter;
	}
}
