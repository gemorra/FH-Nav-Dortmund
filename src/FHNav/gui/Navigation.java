package FHNav.gui;

import FHNav.controller.BreadthFirstSearchTest;
import FHNav.controller.BreadthFirstSearchTest.Node;
import FHNav.controller.MainApplicationManager;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class Navigation extends Activity {

	BreadthFirstSearchTest bfst;
	CView cv;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation);

		bfst = MainApplicationManager.getBfst();
		bfst.search(bfst.getFrom(), bfst.getTo());

		LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout1);

		cv = new CView(getApplicationContext());
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		ll.addView(cv, params);
		cv.init();

		Spinner sp_from = (Spinner) findViewById(R.id.spinnerFromRoom);
		Spinner sp_to = (Spinner) findViewById(R.id.spinnerToRoom);
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line);
		int fr = 0;
		int to2 = 0;
		int count = 0;
		for (Object o : bfst.getNodes()) {
			Node n = (Node) o;
			aa.add(n.getName());
			if (n.equals(bfst.getFrom())) {
				fr = count;
			}
			if (n.equals(bfst.getTo())) {
				to2 = count;
			}
			count++;
		}
		sp_from.setAdapter(aa);
		sp_from.setSelection(fr);
		sp_from.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				Node n = (Node) bfst.getNodes().get(arg2);
				bfst.setFrom(n);
				bfst.setPath(bfst.search(bfst.getFrom(), bfst.getTo()));
				cv.invalidate();

			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		sp_to.setAdapter(aa);
		sp_to.setSelection(to2);
		sp_to.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				Node n = (Node) bfst.getNodes().get(arg2);
				bfst.setTo(n);
				bfst.setPath(bfst.search(bfst.getFrom(), bfst.getTo()));
				cv.invalidate();

			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		cv.invalidate();
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

}
