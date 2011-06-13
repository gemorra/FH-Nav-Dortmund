package FHNav.gui;

import java.util.ArrayList;
import java.util.Collections;

import FHNav.controller.MainApplicationManager;
import FHNav.controller.Tools;
import FHNav.gui.helper.ExtendedListAdapter;
import FHNav.gui.helper.SeparatedListAdapter;
import FHNav.model.Veranstaltung;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AdaptStundenplan extends Activity {

	private ArrayList<Veranstaltung> veranstaltungen;
	public SeparatedListAdapter separatedListAdapter;
	ListView lv1;
	Button btn_select_all;
	boolean select = true;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.adaptstundenplan);

		veranstaltungen = MainApplicationManager.getVeranstaltungen();
		Collections.sort(veranstaltungen);
		build_list();

		btn_select_all = (Button) findViewById(R.id.adaptstundenplan_select_all);
		btn_select_all.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (select) {
					select_all();
				} else {
					deselect_all();
				}
			}
		});

		Button btn_delete = (Button) findViewById(R.id.adaptstundenplan_delete);
		btn_delete.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ArrayList<Veranstaltung> tmpArr = new ArrayList<Veranstaltung>();
				int sizebefore = veranstaltungen.size();
				for (int i = 0; i < separatedListAdapter.sections.size(); i++) {
					ExtendedListAdapter el = (ExtendedListAdapter) (separatedListAdapter.sections.values().toArray()[i]);
					for (int j = 0; j < el.getChecked().size(); j++) {
						if (el.getChecked().get(j) == true) {
							tmpArr.add(el.getItems().get(j));
						}
					}

				}
				for (Veranstaltung ver : tmpArr)
					veranstaltungen.remove(ver);
				deselect_all();
				build_list();
				int count = sizebefore - veranstaltungen.size();
				Toast t = Toast.makeText(getApplicationContext(), getString(R.string.adaptstundenplan_toast_text_1a) + " " + count + " "
						+ getString(R.string.adaptstundenplan_toast_text_1b), Toast.LENGTH_SHORT);
				t.show();
			}
		});

	}

	public void select_all() {
		for (int i = 0; i < separatedListAdapter.sections.size(); i++) {
			ExtendedListAdapter el = (ExtendedListAdapter) (separatedListAdapter.sections.values().toArray()[i]);
			el.selectAll();
			separatedListAdapter.notifyDataSetChanged();
			select = false;
			btn_select_all.setText(R.string.button_deselect_all_label);
		}
	}

	public void deselect_all() {
		for (int i = 0; i < separatedListAdapter.sections.size(); i++) {
			ExtendedListAdapter el = (ExtendedListAdapter) (separatedListAdapter.sections.values().toArray()[i]);
			el.deselectAll();
		}
		separatedListAdapter.notifyDataSetChanged();
		select = true;
		btn_select_all.setText(R.string.button_select_all_label);
	}

	private void build_list() {

		separatedListAdapter = new SeparatedListAdapter(this);

		ExtendedListAdapter[] els = new ExtendedListAdapter[7];
		for (int i = 0; i < els.length; i++) {
			els[i] = new ExtendedListAdapter(this, new ArrayList<Veranstaltung>());
		}

		for (Veranstaltung ver : veranstaltungen) {
			els[ver.getWochentag() - 1].getItems().add(ver);
		}

		for (int i = 0; i < els.length; i++) {
			if (els[i].getItems().size() > 0) {
				els[i].refresh();
				separatedListAdapter.addSection(getString(Tools.getWeekday(i + 1)), els[i]);
			}
		}

		lv1 = (ListView) findViewById(R.id.adaptstundenplan_list);
		lv1.setAdapter(separatedListAdapter);

	}

	
}
