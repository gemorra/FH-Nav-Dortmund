package FHNav.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		getWindow().setBackgroundDrawableResource(R.drawable.logokloppo);

		Button btn1;
		btn1 = (Button) findViewById(R.id.Button01);
		 btn1.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
            	 startActivity(new Intent(MainMenu.this, ShowAgenda.class));

             }
         });
		 
		 Button btn4;
			btn4 = (Button) findViewById(R.id.Button04);
			 btn4.setOnClickListener(new View.OnClickListener() {
	             public void onClick(View v) {
	            	 Intent exit_intent=new Intent(MainMenu.this,Main.class);
	            	 exit_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	            	 exit_intent.putExtra("EXIT", true);    // just value to indicate i want to  exit the application
	            	 startActivity(exit_intent);
	             }
	         }); 
		 
		 
		 
	}
	
}
