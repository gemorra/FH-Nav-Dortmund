package FHNav.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

public class AdaptStundenplan extends Activity{
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		Spinner s1=(Spinner) findViewById(R.id.Spinner1);
		ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
			new String[] {"Agenda Ansicht", "Stundenplan Ansicht"});
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			s1.setAdapter(adapter);
			
		Button im1 = (Button) this.findViewById(R.id.add_button);
		//im1.setImageResource(R.drawable)
		Button im2 = (Button) this.findViewById(R.id.delete_button);
		//im2.setImageResource(R.drawable)
		Button im3 = (Button) this.findViewById(R.id.zurueck_button);
		//im3.setImageResource(R.drawable)
	}
}
