package FHNav.gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import FHNav.controller.CanteenBeanTest;
import FHNav.controller.Tools;
import FHNav.gui.helper.NormalListAdapterForMenu;
import FHNav.gui.helper.SeparatedListAdapter;
import FHNav.model.CanteenMenu;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ShowExtras extends Activity implements Runnable {

	BaseAdapter listAdapter;
	BaseAdapter listAdapterMensa;
	boolean mensa = false;
	ListView lv1;
	Button btn1;
	ProgressDialog dialog;
	Button btn2;
	Button btn3;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mensa = false;
		refresh();

	}

	private void refresh() {
		if (mensa) {
			setContentView(R.layout.extras);
			refreshButtons();
			lv1 = (ListView) findViewById(R.id.listView1);
			lv1.setEmptyView(findViewById(R.id.empty));
			if (listAdapterMensa == null) {
				dialog = ProgressDialog.show(ShowExtras.this, "", "Download...", true);
				Thread t1 = new Thread(ShowExtras.this);
				t1.start();
			} else
				lv1.setAdapter(listAdapterMensa);
			btn1.setText(R.string.extras_button1a);
		} else {
			setContentView(R.layout.extras2);
			refreshButtons();
			btn1.setText(R.string.extras_button1b);
			WebView mWebView = (WebView) findViewById(R.id.webView1);
			mWebView.getSettings().setJavaScriptEnabled(false);
//			mWebView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");  
			mWebView.setWebViewClient(new HelloWebViewClient());	
			mWebView.setBackgroundColor(0);
		    if(mWebView.getUrl()==null)
		    	mWebView.loadUrl("http://www.fh-dortmund.de/de/fb/4/isc/aktuelles/index.php");
//		    mWebView.setBackgroundColor(0);
		}

	}
	

	private void refreshButtons() {
		btn1 = (Button) findViewById(R.id.Button01);
		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				mensa = !mensa;
				refresh();
			}

		});

		btn2 = (Button) findViewById(R.id.Button02);
		btn2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// AlertDialog.Builder adb = new AlertDialog.Builder(Menu.this);
				// adb.setTitle("Navigation");
				// adb.setMessage("Comming soon...");
				// adb.setPositiveButton("  OK  ", new
				// DialogInterface.OnClickListener() {
				//
				// public void onClick(DialogInterface dialog2, int which) {
				// }
				// });
				// adb.show();
			}
		});

		Button btn3;
		btn3 = (Button) findViewById(R.id.Button03);
		btn3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	private BaseAdapter build_normal() {
		SeparatedListAdapter separatedListAdapter = new SeparatedListAdapter(this);
		ArrayList<CanteenMenu> menus = CanteenBeanTest.getMenus();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

		NormalListAdapterForMenu[] els = new NormalListAdapterForMenu[8];
		for (int i = 0; i < els.length; i++) {
			els[i] = new NormalListAdapterForMenu(this, new ArrayList<CanteenMenu>());
		}
		for (CanteenMenu cm : menus) {
			if (cm.getDate() == null)
				els[7].getItems().add(cm);
			else
				els[cm.getDate().getDay()].getItems().add(cm);
		}
		for (int i = 0; i < els.length; i++) {
			if (els[i].getItems().size() > 0) {
				String header;
				if (i != 7)
					header = getString(Tools.getWeekday(els[i].getItems().get(0).getDate().getDay())) + ", der "
							+ sdf.format(els[i].getItems().get(0).getDate());
				else
					header = getString(R.string.canteenSpezialHeader);
				separatedListAdapter.addSection(header, els[i]);
			}
		}
		return separatedListAdapter;
	}

	public void run() {
		if (mensa) {
			listAdapterMensa = build_normal();
			Message msg = handler.obtainMessage();
			handler.sendMessage(msg);
		}
	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (mensa) {

				lv1.setAdapter(listAdapterMensa);
				dialog.dismiss();
			}
		}
	};
//	class MyJavaScriptInterface  
//	{  
//	    @SuppressWarnings("unused")  
//	    public void showHTML(String html)  
//	    {  
//	        new AlertDialog.Builder(ShowExtras.this)  
//	            .setTitle("HTML")  
//	            .setMessage(html)  
//	            .setPositiveButton(android.R.string.ok, null)  
//	        .setCancelable(false)  
//	        .create()  
//	        .show();  
//	    }  
//	}  
	private class HelloWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
//	    @Override  
//	    public void onPageFinished(WebView view, String url)  
//	    {  
//	        System.out.println("testtest"); 
//	        view.loadUrl("javascript:window.HTMLOUT.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");  
//	    }
	}
}
