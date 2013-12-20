package FHNav.gui;

import java.util.ArrayList;
import java.util.Collections;

import FHNav.controller.IOManager;
import FHNav.controller.MainApplicationManager;
import FHNav.controller.PHPConnector;
import FHNav.controller.Tools;
import FHNav.gui.helper.ExtendedListAdapter;
import FHNav.gui.helper.SeparatedListAdapter;
import FHNav.model.Stundenplan;
import FHNav.model.Veranstaltung;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class AddVorlesung extends Activity implements Runnable {
	ProgressDialog dialog;
	private ArrayList<Veranstaltung> veranstaltungen;
	public ExtendedListAdapter extendedListAdapter;
	public SeparatedListAdapter separatedListAdapter;
	ListView lv1;
	boolean loadSpinner = true;
	Spinner spinner1;
	ArrayList<String> spinnerContent;
	Button btn_select_all;
	Button btn_add;
	Button btn_back;
	boolean select = false;
	boolean click = false;

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			if (!loadSpinner) {
				build_list();

			} else {
				ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
						AddVorlesung.this,
						android.R.layout.simple_dropdown_item_1line,
						spinnerContent);
				adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner1.setAdapter(adapter1);
				dialog.dismiss();
				if (spinnerContent.size() < 1) {
					// Verbindungsfehler Dialog:
					final Dialog error_dialog = new Dialog(AddVorlesung.this);

					error_dialog
							.setContentView(R.layout.alert_dialog_connection_problem);
					error_dialog
							.setTitle(R.string.alert_dialog_connection_problem_title);
					// final EditText et = (EditText)
					// error_dialog.findViewById(R.id.alert_dialog_connection_problem_editText);
					Button btn = (Button) error_dialog
							.findViewById(R.id.alert_dialog_connection_problem_button);
					// et.setText(SettingsManager.getPathToFile());
					btn.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							// SettingsManager.setPathToFile(et.getText().toString());
							error_dialog.dismiss();
							loadSpinner = true;
							dialog = ProgressDialog.show(AddVorlesung.this, "",
									"Download...", true);
							Thread t1 = new Thread(AddVorlesung.this);
							t1.start();
						}
					});
					Button btn2 = (Button) error_dialog
							.findViewById(R.id.alert_dialog_connection_problem_buttonNext);
					btn2.setText(getString(R.string.alert_dialog_connection_problem_next_button2));
					btn2.setOnClickListener(new OnClickListener() {

						public void onClick(View v) {
							error_dialog.dismiss();
							onBackPressed();
						}
					});

					error_dialog.show();
				}
			}
		}
	};

	private void build_list() {

		separatedListAdapter = new SeparatedListAdapter(this);

		ExtendedListAdapter[] els = new ExtendedListAdapter[7];
		for (int i = 0; i < els.length; i++) {
			els[i] = new ExtendedListAdapter(this,
					new ArrayList<Veranstaltung>());
		}

		for (Veranstaltung ver : veranstaltungen) {
			els[ver.getWochentag() - 1].getItems().add(ver);
		}

		for (int i = 0; i < els.length; i++) {
			if (els[i].getItems().size() > 0) {
				els[i].refresh();
				separatedListAdapter.addSection(
						getString(Tools.getWeekday(i + 1)), els[i]);
			}
		}

		lv1 = (ListView) findViewById(R.id.addvorlesung_list);
		lv1.setAdapter(separatedListAdapter);

	}

	public void deselect_all() {
		for (int i = 0; i < separatedListAdapter.sections.size(); i++) {
			ExtendedListAdapter el = (ExtendedListAdapter) (separatedListAdapter.sections
					.values().toArray()[i]);
			el.deselectAll();
		}
		separatedListAdapter.notifyDataSetChanged();
		select = true;
		btn_select_all.setText(R.string.button_select_all_label);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addvorlesung);
		separatedListAdapter = new SeparatedListAdapter(this);
		dialog = ProgressDialog
				.show(AddVorlesung.this, "", "Download...", true);
		loadSpinner = true;
		Thread t1 = new Thread(AddVorlesung.this);
		t1.start();

		btn_select_all = (Button) findViewById(R.id.addveranstaltung_select_all);
		btn_select_all.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (select) {
					select_all();
				} else {
					deselect_all();
				}
			}
		});

		btn_back = (Button) findViewById(R.id.addveranstaltung_back);
		btn_back.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				onBackPressed();
			}
		});

		btn_add = (Button) findViewById(R.id.addveranstaltung_add);
		btn_add.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ArrayList<Veranstaltung> tmpArr = new ArrayList<Veranstaltung>();
				Stundenplan s = MainApplicationManager.getStundenplan();
				int sizebefore = s.getVeranstaltungen().size();
				for (int i = 0; i < separatedListAdapter.sections.size(); i++) {
					ExtendedListAdapter el = (ExtendedListAdapter) (separatedListAdapter.sections
							.values().toArray()[i]);
					for (int j = 0; j < el.getChecked().size(); j++) {
						if (el.getChecked().get(j) == true) {
							tmpArr.add(el.getItems().get(j));
						}
					}
				}
				if (tmpArr.size() == 0) {
					Toast t = Toast.makeText(getApplicationContext(),
							getString(R.string.addveranstaltung_toast_text_0b),
							Toast.LENGTH_SHORT);
					t.show();
				} else {
					for (Veranstaltung ver : tmpArr) {
						s.addVeranstaltung(ver);
					}
					deselect_all();

					int count = s.getVeranstaltungen().size() - sizebefore;

					if (count > 0) {
						MainApplicationManager.setStundenplan(s);
						IOManager.saveStundenplan(
								MainApplicationManager.getStundenplan(),
								getApplicationContext());
						Toast t = Toast
								.makeText(
										getApplicationContext(),
										getString(R.string.addveranstaltung_toast_text_1a)
												+ " "
												+ count
												+ " "
												+ getString(R.string.addveranstaltung_toast_text_1b),
										Toast.LENGTH_SHORT);
						t.show();
					} else {
						Toast t = Toast
								.makeText(
										getApplicationContext(),
										getString(R.string.addveranstaltung_toast_text_0),
										Toast.LENGTH_SHORT);
						t.show();
					}
				}
			}

		});

		click = false;
		spinner1 = (Spinner) AddVorlesung.this.findViewById(R.id.Spinner01);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				System.out.println("ITEM SELECTED");
				String selectedBranch = (String) (spinner1.getSelectedItem());
				if ((spinnerContent.size() >= 1)
						&& !selectedBranch
								.equals(getString(R.string.addvorlesung_spinner_text))) {
					if (spinnerContent.get(0).equals(
							getString(R.string.addvorlesung_spinner_text))) {
						spinnerContent.remove(0);
						spinner1.setSelection(spinner1
								.getSelectedItemPosition() - 1);
						// spinner1.refreshDrawableState();
					} else {
						// if (spinnerContent.size() >= 1 &&
						// !selectedBranch.equals(getString(R.string.addvorlesung_spinner_text)))
						// {

						Log.e("inhalt", selectedBranch);

						if (selectedBranch != getString(R.string.addvorlesung_spinner_text)) {
							loadSpinner = false;
							dialog = ProgressDialog.show(AddVorlesung.this, "",
									"Download...", true);
							Thread t = new Thread(AddVorlesung.this);
							t.start();
						}

						// }
					}
				}

			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				System.out.println("NOTHING SELECTED");
			}
		});

		// veranstaltungen = MainApplicationManager.getVeranstaltungen();

	}

	public void onStart() {
		super.onStart();
		Log.e(this.getClass().toString(), "Start");
		if (MainApplicationManager.isFinish()) {
			finish();
			// FlurryAgent.onStartSession(this, "I7RRJ22MKL64Q9JLNZW8");
		}

	}

	public void onStop() {
		super.onStop();
		// FlurryAgent.onEndSession(this);
		Log.e(this.getClass().toString(), "Stop");
	}

	public void run() {
		try {
			if (!loadSpinner) {

				Stundenplan stundenplan = PHPConnector.getStundenplanFromMysql(
						(String) (spinner1.getSelectedItem()),
						getApplicationContext());

				if (stundenplan.getVeranstaltungen().size() > 0) {

					veranstaltungen = stundenplan.getVeranstaltungen();
					Collections.sort(veranstaltungen);
					Message msg = handler.obtainMessage();
					handler.sendMessage(msg);
				}
				dialog.dismiss();
			} else {
				spinnerContent = new ArrayList<String>();
				spinnerContent.add("Studiengang wählen");

				spinnerContent.addAll(PHPConnector
						.getAllBranches(getApplicationContext()));

				Message msg = handler.obtainMessage();
				handler.sendMessage(msg);
			}
		} catch (Exception e) {
		} finally {
			dialog.dismiss();
		}
	}

	public void select_all() {
		for (int i = 0; i < separatedListAdapter.sections.size(); i++) {
			ExtendedListAdapter el = (ExtendedListAdapter) (separatedListAdapter.sections
					.values().toArray()[i]);
			el.selectAll();
			separatedListAdapter.notifyDataSetChanged();
			select = false;
			btn_select_all.setText(R.string.button_deselect_all_label);
		}
	}
}
