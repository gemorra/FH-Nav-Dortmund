package FHNav.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class Main extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		
		
		if (getIntent().getBooleanExtra("EXIT", false)) {
			finish();
			}
		else{
		AlertDialog.Builder adb = new AlertDialog.Builder(
				Main.this);
		adb.setTitle("Herzlich Willkommen");
		adb.setMessage("Dieser Wizard wird sie durch die erste Einrichtung führen.");
		adb.setPositiveButton("Los geht's!", null);
		adb.show();}
		
		
		
		setContentView(R.layout.wizard);
		getWindow().setBackgroundDrawableResource(R.drawable.b2obenunten);
		
		Spinner spinner1 = (Spinner)this.findViewById(R.id.Spinner01);  
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,  
		         android.R.layout.simple_dropdown_item_1line,  
		         new String[] { "Ba. Praktische Informatik", "Ma. Praktische Informatik", "Ba. Wirtschaftsinformatik", "Ma. Wirtschaftsinformatik"});  
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		spinner1.setAdapter(adapter1); 
		
		
		
		
		Spinner spinner2 = (Spinner)this.findViewById(R.id.Spinner02);  
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,  
		         android.R.layout.simple_spinner_item,  
		         new String[] { "1", "3", "5"});  
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
		spinner2.setAdapter(adapter2); 
		
		Button btn = (Button)this.findViewById(R.id.WizardOK);
		btn.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				startActivity(new Intent(Main.this,MainMenu.class));
				
			}
		});
		
		
		
		}
}
