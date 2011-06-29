package FHNav.gui;

import FHNav.controller.BreadthFirstSearchTest;
import FHNav.controller.MainApplicationManager;
import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class Navigation extends Activity {

	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.navigation);
		
		BreadthFirstSearchTest bfst = new BreadthFirstSearchTest();
		bfst.initGraph();
		
		MainApplicationManager.setBfst(bfst);
		
		
		LinearLayout ll = (LinearLayout) findViewById(R.id.linearLayout1);
		
		CView cv = new CView(MainApplicationManager.getCtx());
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		ll.addView(cv, params);
		cv.init();
	}
	
}
