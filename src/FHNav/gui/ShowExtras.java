package FHNav.gui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import FHNav.controller.CanteenBeanTest;
import FHNav.controller.MainApplicationManager;
import FHNav.controller.Tools;
import FHNav.gui.helper.NormalListAdapterForMenu;
import FHNav.gui.helper.SeparatedListAdapter;
import FHNav.model.CanteenMenu;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

public class ShowExtras extends Activity implements Runnable {

	Button btn1;
	Button btn2;
	Button btn3;
	int choose2;
	String chooseMensa;
	String choosePage;
	String dataAktuellesW;
	

	ProgressDialog dialog;

	BaseAdapter listAdapter;

	BaseAdapter listAdapterMensa;

	ListView lv1;

	boolean mensa = false;

	WebView mWebView;

	Spinner sp;
	Thread t1;

	boolean load = false;
	ArrayList<CanteenMenu> lastMenuMensa;
	ArrayList<CanteenMenu> lastMenuKostbar;

	private BaseAdapter build_normal() {
		SeparatedListAdapter separatedListAdapter = new SeparatedListAdapter(this);
		ArrayList<CanteenMenu> menus;
		if (chooseMensa.equals(getString(R.string.page_name_mensa))) {
			if(load)
			{
				MainApplicationManager.setDataMensa(CanteenBeanTest.getMenuMensa());
				load = false;
			}
			menus = MainApplicationManager.getDataMensa();
		} else {
			if(load)
			{
				MainApplicationManager.setDataKostBar(CanteenBeanTest.getMenuKostbar());
				load = false;
			}
			
			menus = MainApplicationManager.getDataKostBar();
			
			System.out.println(menus);
		}
		if(menus==null)
		{
			menus = new ArrayList<CanteenMenu>();
		}
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
					header = getString(Tools.getWeekday(els[i].getItems().get(0).getDate().getDay())) + ", " + getString(R.string.the) + " "
							+ sdf.format(els[i].getItems().get(0).getDate());
				else
					header = getString(R.string.canteenSpezialHeader);
				separatedListAdapter.addSection(header, els[i]);
			}
		}
		return separatedListAdapter;
	}

	public void initWebViewContent() {
		if (choosePage.equals(getString(R.string.page_name_news_W))) {
			if (dataAktuellesW == null)
				dataAktuellesW = parseNewsW();
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		chooseMensa = getString(R.string.page_name_mensa);
		choosePage = getString(R.string.page_name_news);
		mensa = false;
		refresh();

	}

	private void refresh() {
		if (mensa) {
			setContentView(R.layout.extras);
			refreshButtons();
			lv1 = (ListView) findViewById(R.id.listView1);
			lv1.setEmptyView(findViewById(R.id.empty));

			btn1.setText(R.string.extras_button1a);
			sp = (Spinner) findViewById(R.id.spinner1);
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new String[] {
					getString(R.string.page_name_mensa), getString(R.string.page_name_mensa_kostbar) });
			sp.setPromptId(R.string.page_select_header);
			sp.setAdapter(adapter2);
			sp.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					chooseMensa = (String) arg0.getItemAtPosition(arg2);

					if (dialog != null)
						dialog.dismiss();
					dialog = ProgressDialog.show(ShowExtras.this, "", "Download...", true);
					Thread t1 = new Thread(ShowExtras.this);
					t1.start();

				}

				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});

			ImageButton btn2 = (ImageButton) findViewById(R.id.mensa_refresh);
			btn2.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					load = true;
					if (dialog != null)
						dialog.dismiss();
					dialog = ProgressDialog.show(ShowExtras.this, "", "Download...", true);
					Thread t1 = new Thread(ShowExtras.this);
					t1.start();
					
				}
			});
		} else {
			setContentView(R.layout.extras2);
			refreshButtons();
			btn1.setText(R.string.extras_button1b);
			mWebView = (WebView) findViewById(R.id.webView1);
			// mWebView.setLayoutParams(params);

			mWebView.getSettings().setJavaScriptEnabled(true);
			mWebView.setWebViewClient(new HelloWebViewClient());
			mWebView.setBackgroundColor(0);

			sp = (Spinner) findViewById(R.id.spinner1);
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new String[] {
					getString(R.string.page_name_news), getString(R.string.page_name_news_W), getString(R.string.page_name_pplan),
					getString(R.string.page_name_lplan),getString(R.string.page_name_lplan2), getString(R.string.page_name_splan), getString(R.string.page_name_bplan) });
			sp.setPromptId(R.string.page_select_header);
			sp.setAdapter(adapter2);
			sp.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					choosePage = (String) arg0.getItemAtPosition(arg2);
					Thread t1 = new Thread(ShowExtras.this);
					t1.start();
				}

				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
		}

	}

	private void refreshButtons() {
		btn1 = (Button) findViewById(R.id.Button01);
		btn1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (dialog != null)
					dialog.dismiss();
				mensa = !mensa;
				refresh();
			}

		});

		btn2 = (Button) findViewById(R.id.Button02);
		btn2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (dialog != null)
					dialog.dismiss();
				startActivity(new Intent(ShowExtras.this, Navigation.class));
			}
		});

		Button btn3;
		btn3 = (Button) findViewById(R.id.Button03);
		btn3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (dialog != null)
					dialog.dismiss();
				onBackPressed();
			}
		});
	}

	public void run() {
		if (mensa) {
			try {
				listAdapterMensa = build_normal();
				Message msg = handler.obtainMessage();
				handler.sendMessage(msg);
			} catch (Exception e) {
			} finally {
				dialog.dismiss();
			}
		} else {
			try {
				initWebViewContent();
				Message msg = handler.obtainMessage();
				handler.sendMessage(msg);
			} catch (Exception e) {
			} finally {
				
			}
		}
	}

	protected void setWebViewContent() {
		if (choosePage.equals(getString(R.string.page_name_news_W))) {
			// mWebView.loadData(dataAktuellesW, "text/html", "utf-8");
			mWebView.loadDataWithBaseURL("http://www.fh-dortmund.de/de/studi/fb/9/studieng/400/aktuelles_stud.php", dataAktuellesW, "text/html", "utf-8", null);
		} else if (choosePage.equals(getString(R.string.page_name_news))) {
			mWebView.loadUrl("http://www.fh-dortmund.de/de/fb/4/isc/aktuelles/index.php");
		} else if (choosePage.equals(getString(R.string.page_name_pplan))) {
			mWebView.loadUrl("http://docs.google.com/gview?embedded=true&url=http://www.inf.fh-dortmund.de/~pa_data/pplan.pdf");
		} else if (choosePage.equals(getString(R.string.page_name_lplan))) {
			mWebView.loadUrl("http://docs.google.com/gview?embedded=true&url=http://www.gemorra.de/lplan.pdf");
		} else if (choosePage.equals(getString(R.string.page_name_lplan2))) {
			mWebView.loadUrl("http://maps.google.de/maps?hl=de&ll=51.4926,7.415965&spn=0.005965,0.016512&sll=51.491311,7.409399&sspn=0.011931,0.033023&t=h&z=17");
		}else if (choosePage.equals(getString(R.string.page_name_splan))) {
			mWebView.loadUrl("http://docs.google.com/gview?embedded=true&url=http://www.gemorra.de/s1.pdf");
		} else if (choosePage.equals(getString(R.string.page_name_bplan))) {
			mWebView.loadUrl("http://docs.google.com/gview?embedded=false&url=http://www.gemorra.de/bus.pdf");
			System.out.println("bus.pdf");
		}
	}

	private class HelloWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
		// @Override
		// public void onPageFinished(WebView view, String url)
		// {
		// System.out.println("testtest");
		// view.loadUrl("javascript:window.HTMLOUT.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
		// }
	}

	public static String parseNewsW() {
		Document doc;
		String ret = "";
		try {
			doc = Jsoup.connect("http://www.fh-dortmund.de/de/studi/fb/9/studieng/400/aktuelles_stud.php").get();
			Elements tds = doc.select("td.g10");
			// Elements tds2 = doc.select("b.notranslate");
			ret = "<html><head></head><body margin=\"0\" padding=\"0\">";

			String test = " <style type=\"text/css\">hr{border-top:1px solid #F60;  height:1px;} .g10 { font-size:0.8em; padding:5px 5px 3px 0px; } html,body{margin:0px; padding:0px;}</style>";
			ret += test;
			for (int i = 0; i < tds.size(); i++) {
				ret += "<div class=\"g10\">";
				ret += tds.get(i).html();
				ret += "</div>";
				ret += "<hr size=\"1\" noshade>";
			}
			ret += "</body></html>";
			System.out.println(ret);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (mensa) {

				lv1.setAdapter(listAdapterMensa);
				dialog.dismiss();
			} else {
				setWebViewContent();
			}
		}
	};
}
