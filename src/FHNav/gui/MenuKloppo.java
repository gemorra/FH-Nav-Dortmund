package FHNav.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MenuKloppo extends Activity {
	Intent show;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menukloppo);
		show = new Intent(MenuKloppo.this, ShowAgenda.class);
		Button btn1;
		btn1 = (Button) findViewById(R.id.Button01);
		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.e("asd", "asd");
			}
		});
	}
}
