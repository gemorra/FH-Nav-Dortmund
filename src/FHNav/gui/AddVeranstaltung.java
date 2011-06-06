package FHNav.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class AddVeranstaltung extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.addveranstaltung);
		getWindow().setBackgroundDrawableResource(R.drawable.bgunten);
		
		Spinner spinner1 = (Spinner)this.findViewById(R.id.Spinner01);  
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,  
		         android.R.layout.simple_dropdown_item_1line,  
		         new String[] { "Ba. Praktische Informatik", "Ma. Praktische Informatik", "Ba. Wirtschaftsinformatik", "Ma. Wirtschaftsinformatik"});  
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		spinner1.setAdapter(adapter1); 
		
		
		//Hallo
		
		Spinner spinner2 = (Spinner)this.findViewById(R.id.Spinner02);  
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,  
		         android.R.layout.simple_spinner_item,  
		         new String[] { "1", "3", "5"});  
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		spinner2.setAdapter(adapter2); 
		
		Button btn = (Button)this.findViewById(R.id.WizardOK);
		btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
							
			}
		});
		
		
		
		}
}
