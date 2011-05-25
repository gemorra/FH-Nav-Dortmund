package FHNav.gui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;



public class MainMenu extends Activity {

	Intent show;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		show = new Intent(MainMenu.this, ShowAgenda.class);
		
		if(pref.getBoolean("wizardDone", false)){		
		setContentView(R.layout.mainmenu);
		getWindow().setBackgroundDrawableResource(R.drawable.logokloppo);
		Button btn1;
		btn1 = (Button) findViewById(R.id.Button01);
		 btn1.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
            	 startActivity(show);

             }
         });
		 
		 Button btn4;
			btn4 = (Button) findViewById(R.id.Button04);
			 btn4.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 stopService(show);
	            	 finish();
	             }
	         }); 
		}
		else
		{
			startActivity(new Intent(MainMenu.this, Wizard.class));
		}
		 
		 
	}
	
}
