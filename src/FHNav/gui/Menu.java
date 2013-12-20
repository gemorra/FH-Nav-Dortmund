package FHNav.gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import FHNav.controller.BreadthFirstSearchTest;
import FHNav.controller.BreadthFirstSearchTest.Node;
import FHNav.controller.CalendarAdapter;
import FHNav.controller.IOManager;
import FHNav.controller.MainApplicationManager;
import FHNav.controller.SettingsManager;
import FHNav.controller.Tools;
import FHNav.gui.helper.NormalListAdapter;
import FHNav.gui.helper.SeparatedListAdapter;
import FHNav.model.Veranstaltung;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends Activity {

	Intent settings;
	Intent adaptstundenplan;
	Intent addVorlesung;
	Intent wizard;

	private ArrayList<Veranstaltung> veranstaltungen;
	SeparatedListAdapter listAdapter;
	boolean agenda = false;
	ListView lv1;
	TextView header;

	int visibleFirst = 0;

	int topy = 0;

	private SeparatedListAdapter build_agenda(
			ArrayList<Veranstaltung> veranstaltungen) {

		SeparatedListAdapter separatedListAdapter = new SeparatedListAdapter(
				this);

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
				els[(ver.getWochentag() + 7) - currentday].getItems().add(ver);
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
			} else {
				els[ver.getWochentag() - currentday].getItems().add(ver);
			}
		}
		for (int i = 0; i < els.length; i++) {
			if (els[i].getItems().size() > 0) {
				String header;
				if (i == 0) {
					header = "Heute ("
							+ getString(Tools.getWeekday(els[i].getItems()
									.get(0).getWochentag())) + ","
							+ sdf.format(c1.getTime()) + ")";
				} else if (i == 1) {
					header = getString(R.string.tomorrow)
							+ " ("
							+ getString(Tools.getWeekday(els[i].getItems()
									.get(0).getWochentag())) + ","
							+ sdf.format(c1.getTime()) + ")";
				} else {
					header = getString(Tools.getWeekday(els[i].getItems()
							.get(0).getWochentag()))
							+ "," + sdf.format(c1.getTime());
				}
				separatedListAdapter.addSection(header, els[i]);
			}
			c1.add(Calendar.DATE, 1);
		}

		return separatedListAdapter;
	}

	public void build_calendar_dialog() {
		final CalendarAdapter ca = new CalendarAdapter(getApplicationContext());
		ca.setSelectedid(0);
		AlertDialog.Builder builder;
		@SuppressWarnings("unused")
		AlertDialog alertDialog;

		Context mContext = Menu.this;
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.calendardialog,
				(ViewGroup) findViewById(R.id.layout_root));

		final Calendar c = Calendar.getInstance();
		int fYear = 2011;
		int fMonth = 10;
		int fDay = 28;
		int tYear = 2012;
		int tMonth = 10;
		int tDay = 28;
		Date frd = new Date(c.getTime().getYear(), 2, 15);
		Date tod = new Date(c.getTime().getYear(), 9, 1);

		if (c.getTime().after(frd) && c.getTime().before(tod)) {
			fYear = c.getTime().getYear() + 1900;
			fMonth = 3;
			fDay = 1;

			tYear = c.getTime().getYear() + 1900;
			tMonth = 7;
			tDay = 15;
		} else {
			fYear = c.getTime().getYear() + 1900;
			fMonth = 9;
			fDay = 15;

			tYear = fYear + 1;
			tMonth = 2;
			tDay = 10;
		}

		final TextView textFrom = (TextView) layout
				.findViewById(R.id.dateDisplayFrom);
		textFrom.setText(fDay + "." + fMonth + "." + fYear);
		final TextView textTo = (TextView) layout
				.findViewById(R.id.dateDisplayTo);
		textTo.setText(tDay + "." + tMonth + "." + tYear);

		final DatePickerDialog.OnDateSetListener fDateSetListener = new DatePickerDialog.OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				textFrom.setText(dayOfMonth + "." + (monthOfYear + 1) + "."
						+ year);
			}
		};
		final DatePickerDialog.OnDateSetListener tDateSetListener = new DatePickerDialog.OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				textTo.setText(dayOfMonth + "." + (monthOfYear + 1) + "."
						+ year);
			}
		};

		final DatePickerDialog dpdf = new DatePickerDialog(Menu.this,
				fDateSetListener, fYear, fMonth - 1, fDay);
		final DatePickerDialog dpdt = new DatePickerDialog(Menu.this,
				tDateSetListener, tYear, tMonth - 1, tDay);

		Button btnFrom = (Button) layout.findViewById(R.id.pickDateFrom);
		btnFrom.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dpdf.show();

			}
		});
		Button btnTo = (Button) layout.findViewById(R.id.pickDateTo);
		btnTo.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dpdt.show();

			}
		});
		Spinner spin1 = (Spinner) layout.findViewById(R.id.calendarSpinner);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(Menu.this,
				android.R.layout.simple_dropdown_item_1line,
				ca.getCalendarNames());
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin1.setAdapter(adapter1);
		spin1.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				ca.setSelectedid(arg2);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		builder = new AlertDialog.Builder(Menu.this);
		builder.setView(layout);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				SimpleDateFormat sdfToDate = new SimpleDateFormat("dd.MM.yyyy");

				Date from;
				Date to;
				try {
					from = sdfToDate.parse((String) textFrom.getText());
					to = sdfToDate.parse((String) textTo.getText());
					to.setHours(23);
					to.setMinutes(59);

					int count = 0;
					for (Veranstaltung ver : MainApplicationManager
							.getStundenplan().getVeranstaltungen()) {
						ca.addRecEventToCalendar(ver, from, to);
						count++;
					}
					Toast t = Toast
							.makeText(
									getApplicationContext(),
									getString(R.string.addveranstaltung_toast_text_1a)
											+ " "
											+ count
											+ " "
											+ getString(R.string.calendar_transfer_toast_text_1b),
									Toast.LENGTH_SHORT);
					t.show();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		builder.setNegativeButton("Cancel", null);
		alertDialog = builder.create();
		builder.show();
	}

	private SeparatedListAdapter build_normal(
			ArrayList<Veranstaltung> veranstaltungen2) {
		SeparatedListAdapter separatedListAdapter = new SeparatedListAdapter(
				this);
		NormalListAdapter[] els = new NormalListAdapter[7];
		for (int i = 0; i < els.length; i++) {
			els[i] = new NormalListAdapter(this, new ArrayList<Veranstaltung>());
		}
		for (Veranstaltung ver : veranstaltungen) {
			els[ver.getWochentag()].getItems().add(ver);
		}
		for (int i = 0; i < els.length; i++) {
			if (els[i].getItems().size() > 0) {
				String header = getString(Tools.getWeekday(els[i].getItems()
						.get(0).getWochentag()));
				separatedListAdapter.addSection(header, els[i]);
			}
		}
		return separatedListAdapter;
	}

	@Override
	public void onBackPressed() {
		Log.e("Menu", "Back");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(this.getClass().toString(), "Create");

		addVorlesung = new Intent(Menu.this, AddVorlesung.class);
		adaptstundenplan = new Intent(Menu.this, AdaptStundenplan.class);
		settings = new Intent(Menu.this, Settings.class);
		wizard = new Intent(Menu.this, Wizard.class);

		if (SettingsManager.isWizardDone(getApplicationContext())) {
			setContentView(R.layout.menu);
			MainApplicationManager.setStundenplan(IOManager
					.loadStundenplan(getApplicationContext()));

			header = (TextView) findViewById(R.id.menu_header);
			header.setText(getString(R.string.normal_view));
			veranstaltungen = MainApplicationManager.getVeranstaltungen();
			BreadthFirstSearchTest bfst = new BreadthFirstSearchTest();
			bfst.initGraph();
			MainApplicationManager.setBfst(bfst);

			refresListView(true);

			Button btn1;
			btn1 = (Button) findViewById(R.id.Button01);
			btn1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					final CharSequence[] items = {
							getString(R.string.editmenu_change_view),
							getString(R.string.editmenu_add),
							getString(R.string.editmenu_delete),
							getString(R.string.editmenu_commit) };

					AlertDialog.Builder builder = new AlertDialog.Builder(
							Menu.this);
					builder.setTitle(getString(R.string.timetable_menu));
					builder.setItems(items,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int item) {
									if (item == 0) {
										agenda = !agenda;
										if (agenda) {
											header.setText(getString(R.string.agenda_view));
										} else {
											header.setText(getString(R.string.normal_view));
										}

										refresListView(true);

									}
									if (item == 2) {
										startActivity(adaptstundenplan);
									}
									if (item == 1) {
										startActivity(addVorlesung);
									}
									if (item == 3) {

										try {
											build_calendar_dialog();
										} catch (Exception e) {
											AlertDialog.Builder adb = new AlertDialog.Builder(
													Menu.this);
											adb.setTitle(R.string.error);
											adb.setMessage(R.string.error_calendar);
											adb.setPositiveButton("  OK  ",
													null);
											adb.show();
										}
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
					startActivity(new Intent(Menu.this, ShowExtras.class));

				}
			});

			Button btn3;
			btn3 = (Button) findViewById(R.id.Button03);
			btn3.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

					startActivity(settings);

				}
			});

			Button btn4;
			btn4 = (Button) findViewById(R.id.exit_application);
			btn4.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					MainApplicationManager.setFinish(true);
					finish();
					// System.exit(0);
				}
			});

			Button application_info;
			application_info = (Button) findViewById(R.id.info_application);
			application_info.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					AlertDialog.Builder adb = new AlertDialog.Builder(Menu.this);
					adb.setTitle("About");
					adb.setMessage("FH Nav Dortmund \nMade by Moritz Wiechers\nmoritz.wiechers@googlemail.com");
					adb.setPositiveButton("Close",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog2,
										int which) {

								}
							});
					adb.show();
				}
			});

		} else {
			startActivity(wizard);
		}

	}

	public void onResume() {
		super.onResume();
		Log.e(this.getClass().toString(), "Resume");
		refresListView(true);
	}

	public void onStart() {
		super.onStart();
		Log.e(this.getClass().toString(), "Start");
		if (MainApplicationManager.isFinish()) {
			finish();
		} else {
			refresListView(true);
			MainApplicationManager.setDensity(getResources()
					.getDisplayMetrics().density);
		}
		// FlurryAgent.onStartSession(this, "I7RRJ22MKL64Q9JLNZW8");
	}

	public void onStop() {
		super.onStop();
		// FlurryAgent.onEndSession(this);
		Log.e(this.getClass().toString(), "Stop");
	}

	public void refresListView(boolean startAtBeginning) {
		if (veranstaltungen != null) {
			Collections.sort(veranstaltungen);
			if (agenda) {
				listAdapter = build_agenda(veranstaltungen);
			} else {
				listAdapter = build_normal(veranstaltungen);
			}

			lv1 = (ListView) findViewById(R.id.listView1);
			lv1.setAdapter(listAdapter);
			lv1.setEmptyView(findViewById(R.id.empty));

			lv1.setOnItemClickListener(new OnItemClickListener() {

				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							Menu.this);
					final SeparatedListAdapter sla = (SeparatedListAdapter) lv1
							.getAdapter();
					if (sla.getItem(arg2) instanceof Veranstaltung) {
						final Veranstaltung ver = (Veranstaltung) sla
								.getItem(arg2);
						builder.setTitle(ver.getName());
						final BreadthFirstSearchTest bfst = MainApplicationManager
								.getBfst();

						final CharSequence[] items;
						items = new CharSequence[2];
						items[0] = getString(R.string.editmenu_delete);
						items[1] = getString(R.string.extras_button2);

						builder.setItems(items,
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										if (which == 0) {
											MainApplicationManager
													.getStundenplan()
													.removeVeranstaltung(ver);
											IOManager.saveStundenplan(
													MainApplicationManager
															.getStundenplan(),
													getApplicationContext());
											visibleFirst = lv1
													.getFirstVisiblePosition();
											View v = lv1.getChildAt(0);
											topy = (v == null) ? 0 : v.getTop();

											refresListView(false);
										} else {
											final Node n = bfst
													.getNodeFromListByString(ver
															.getRaum());
											if (n != null) {
												bfst.setFrom((Node) bfst
														.getNodes().get(0));
												bfst.setTo(n);
												startActivity(new Intent(
														Menu.this,
														Navigation.class));
											} else {
												AlertDialog.Builder adb = new AlertDialog.Builder(
														Menu.this);
												adb.setTitle("Navigation");
												adb.setMessage("Comming soon...");
												adb.setPositiveButton(
														"  OK  ",
														new DialogInterface.OnClickListener() {

															public void onClick(
																	DialogInterface dialog2,
																	int which) {
															}
														});
												adb.show();
											}
										}
									}
								});
						AlertDialog alert = builder.create();
						alert.show();
					}
				}
			});
			if (!startAtBeginning) {

				if (visibleFirst >= listAdapter.getCount()) {
					visibleFirst = listAdapter.getCount() - 1;
					topy = 0;
				}
				if (visibleFirst < 0) {
					visibleFirst = 0;
					topy = 0;
				}

				lv1.setSelectionFromTop(visibleFirst, topy);
			}
		}
	}

}
