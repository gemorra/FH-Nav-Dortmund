package FHNav.gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import FHNav.controller.IOManager;
import FHNav.controller.MainApplicationManager;
import FHNav.controller.SettingsManager;
import FHNav.controller.Tools;
import FHNav.gui.helper.NormalListAdapter;
import FHNav.gui.helper.SeparatedListAdapter;
import FHNav.model.Veranstaltung;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Menu extends Activity {

	Intent settings;
	Intent adaptstundenplan;
	Intent addVorlesung;
	Intent wizard;
	
	private ArrayList<Veranstaltung> veranstaltungen;
	BaseAdapter listAdapter;
	boolean agenda = false;
	ListView lv1;

	public void onResume() {
		super.onResume();
		Log.e("Menu", "Resume");
	}

	public void onStart() {
		super.onStart();
		Log.e("Menu", "Start");
		if(SettingsManager.isWizardDone())
		refresListView();
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("Menu", "Create");
		SettingsManager.loadSettings(this);
		MainApplicationManager.setCtx(getApplicationContext());
		addVorlesung = new Intent(Menu.this, AddVorlesung.class);
		adaptstundenplan = new Intent(Menu.this, AdaptStundenplan.class);
		settings = new Intent(Menu.this, Settings.class);
		wizard = new Intent(Menu.this, Wizard.class);
		
		if (SettingsManager.isWizardDone()) {
			setContentView(R.layout.menu);
			MainApplicationManager.setStundenplan(IOManager.loadStundenplan());

			veranstaltungen = MainApplicationManager.getVeranstaltungen();

			refresListView();

			Button btn1;
			btn1 = (Button) findViewById(R.id.Button01);
			btn1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					final CharSequence[] items = { getString(R.string.editmenu_change_view), getString(R.string.editmenu_add),
							getString(R.string.editmenu_delete), getString(R.string.editmenu_commit) };

					AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
					builder.setTitle("Stundenplan anpassen");
					builder.setItems(items, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							if (item == 0) {
								agenda = !agenda;
								refresListView();
							}
							if (item == 2) {
								startActivity(adaptstundenplan);
							}
							if (item == 1) {
								startActivity(addVorlesung);
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
					AlertDialog.Builder adb = new AlertDialog.Builder(Menu.this);
					adb.setTitle("Navigation");
					adb.setMessage("Comming soon...");
					adb.setPositiveButton("  OK  ", new DialogInterface.OnClickListener() {
	

						public void onClick(DialogInterface dialog2, int which) {
							}
						});
					adb.show();
				}
			});

			Button btn3;
			btn3 = (Button) findViewById(R.id.Button03);
			btn3.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					startActivity(settings);

				}
			});
		} else {
			startActivity(wizard);
		}

	}

	private BaseAdapter build_normal(ArrayList<Veranstaltung> veranstaltungen2) {
		SeparatedListAdapter separatedListAdapter = new SeparatedListAdapter(this);
		NormalListAdapter[] els = new NormalListAdapter[7];
		for (int i = 0; i < els.length; i++) {
			els[i] = new NormalListAdapter(this, new ArrayList<Veranstaltung>());
		}
		for (Veranstaltung ver : veranstaltungen) {
			els[ver.getWochentag()].getItems().add(ver);
		}
		for (int i = 0; i < els.length; i++) {
			if (els[i].getItems().size() > 0) {
				String header = getString(Tools.getWeekday(els[i].getItems().get(0).getWochentag()));
				separatedListAdapter.addSection(header, els[i]);
			}
		}
		return separatedListAdapter;
	}

	private BaseAdapter build_agenda(ArrayList<Veranstaltung> veranstaltungen) {

		SeparatedListAdapter separatedListAdapter = new SeparatedListAdapter(this);

		NormalListAdapter[] els = new NormalListAdapter[8];

		String pattern = "dd.MM.yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar c1 = Calendar.getInstance();
		Date dt = new Date();
		c1.set(dt.getYear() + 1900, dt.getMonth(), dt.getDate());
		c1.setFirstDayOfWeek(Calendar.MONDAY);
		int currentday = c1.getTime().getDay();
		for (int i = 0; i < els.length; i++) {
			els[i] = new NormalListAdapter(this, new ArrayList<Veranstaltung>());
		}

		for (Veranstaltung ver : veranstaltungen) {
			if (ver.getWochentag() < currentday) {
				els[ver.getWochentag() + 7 - currentday].getItems().add(ver);
			} else if (ver.getWochentag() == currentday) {
				Date dt2 = new Date();
				dt2.setDate(dt.getDate());
				dt2.setHours(ver.getEndTime().getHours());
				dt2.setMinutes(ver.getEndTime().getMinutes());

				if (dt2.after(dt)) {
					els[ver.getWochentag() - currentday].getItems().add(ver);
				} else {
					els[7].getItems().add(ver);
				}
			} else
				els[ver.getWochentag() - currentday].getItems().add(ver);
		}
		for (int i = 0; i < els.length; i++) {
			if (els[i].getItems().size() > 0) {
				String header;
				if (i == 0) {
					header = "Heute (" + getString(Tools.getWeekday(els[i].getItems().get(0).getWochentag())) + "," + sdf.format(c1.getTime()) + ")";
				} else if (i == 1) {
					header = "Morgen (" + getString(Tools.getWeekday(els[i].getItems().get(0).getWochentag())) + "," + sdf.format(c1.getTime()) + ")";
				} else
					header = getString(Tools.getWeekday(els[i].getItems().get(0).getWochentag())) + "," + sdf.format(c1.getTime());
				separatedListAdapter.addSection(header, els[i]);
			}
			c1.add(Calendar.DATE, 1);
		}

		return separatedListAdapter;
	}

	public void refresListView() {
		if (agenda)
			listAdapter = build_agenda(veranstaltungen);
		else
			listAdapter = build_normal(veranstaltungen);

		lv1 = (ListView) findViewById(R.id.listView1);
		lv1.setAdapter(listAdapter);
		lv1.setEmptyView(findViewById(R.id.empty));
	}
 
	@Override
	public void onBackPressed()
	{
		Log.e("Menu","Back");
	}
	
}
