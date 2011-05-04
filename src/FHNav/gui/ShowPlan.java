package FHNav.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ShowPlan extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showplan);
//		getWindow().setBackgroundDrawableResource(R.drawable.test);
		
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.new_game:
	    	startActivity(new Intent(ShowPlan.this,AddVeranstaltung.class));
	    	break;
	    case R.id.quit:
	    	startActivity(new Intent(ShowPlan.this,ShowAgenda.class));
	    }
	    
	    return true;
	}
}
