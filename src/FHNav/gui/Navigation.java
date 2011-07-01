package FHNav.gui;

import FHNav.controller.BreadthFirstSearchTest;
import FHNav.controller.BreadthFirstSearchTest.Node;
import FHNav.controller.MainApplicationManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class Navigation extends Activity {

	BreadthFirstSearchTest bfst = new BreadthFirstSearchTest();
	CView cv;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation);
		
		
		bfst.initGraph();
		
		MainApplicationManager.setBfst(bfst);
		bfst.search(bfst.getFrom(), bfst.getTo());
		
		LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout1);
		
		cv = new CView(MainApplicationManager.getCtx());
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		ll.addView(cv, params);
		cv.init();
		
		Spinner sp_from = (Spinner) findViewById(R.id.spinnerFromRoom);
		Spinner sp_to = (Spinner) findViewById(R.id.spinnerToRoom);
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,  android.R.layout.simple_dropdown_item_1line);
		for (Object o: bfst.getNodes()) {
			Node n = (Node) o;
			aa.add(n.getName());
		}
		sp_from.setAdapter(aa);
		sp_from.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Node n = (Node) bfst.getNodes().get(arg2);
				bfst.setFrom(n);
				bfst.setPath(bfst.search(bfst.getFrom(),bfst.getTo()));
				cv.invalidate();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		sp_to.setAdapter(aa);
		sp_to.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Node n = (Node) bfst.getNodes().get(arg2);
				bfst.setTo(n);
				bfst.setPath(bfst.search(bfst.getFrom(),bfst.getTo()));
				cv.invalidate();
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}
