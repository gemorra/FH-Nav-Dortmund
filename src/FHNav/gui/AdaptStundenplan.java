package FHNav.gui;

import java.util.ArrayList;

import FHNav.controller.MainApplicationManager;
import FHNav.gui.helper.ExtendedListAdapter;
import FHNav.model.Veranstaltung;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class AdaptStundenplan extends Activity {

	private ArrayList<Veranstaltung> veranstaltungen;
	public ExtendedListAdapter extendedListAdapter;
	ListView lv1;
	Button btn_select_all;
	boolean select = true;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.adaptstundenplan);

		veranstaltungen = MainApplicationManager.getVeranstaltungen();

		extendedListAdapter = new ExtendedListAdapter(this, veranstaltungen);
		lv1 = (ListView) findViewById(R.id.adaptstundenplan_list);
		lv1.setAdapter(extendedListAdapter);

		btn_select_all = (Button) findViewById(R.id.adaptstundenplan_select_all);

		btn_select_all.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ExtendedListAdapter ex = (ExtendedListAdapter) (lv1
						.getAdapter());
				if (select) {
					ex.selectAll();
					select = false;
					btn_select_all.setText(R.string.button_deselect_all_label);
				} else {
					ex.deselectAll();
					select = true;
					btn_select_all.setText(R.string.button_select_all_label);
				}
			}
		});
	}
}
