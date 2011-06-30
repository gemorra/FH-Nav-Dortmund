package FHNav.gui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import FHNav.controller.CanteenBeanTest;
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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class ShowExtras extends Activity implements Runnable, OnItemSelectedListener {

	BaseAdapter listAdapter;
	BaseAdapter listAdapterMensa;
	boolean mensa = false;
	ListView lv1;
	Button btn1;
	ProgressDialog dialog;
	Button btn2;
	Button btn3;
	Spinner sp;
	WebView mWebView;

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
			mWebView = (WebView) findViewById(R.id.webView1);
//			mWebView.setLayoutParams(params);
			
			mWebView.getSettings().setJavaScriptEnabled(false);
			mWebView.setWebViewClient(new HelloWebViewClient());
			mWebView.setBackgroundColor(0);

			sp = (Spinner) findViewById(R.id.spinner1);
			ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new String[] {
					getString(R.string.page_name_news), getString(R.string.page_name_news_W), getString(R.string.page_name_mensa),
					getString(R.string.page_name_mensa_kostbar) });
			sp.setPromptId(R.string.page_select_header);
			sp.setAdapter(adapter2);
			sp.setOnItemSelectedListener(this);
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
				startActivity(new Intent(ShowExtras.this, Navigation.class));
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
		} else {
			initWebViewContent();
			Message msg = handler.obtainMessage();
			handler.sendMessage(msg);
		}
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

	// class MyJavaScriptInterface
	// {
	// @SuppressWarnings("unused")
	// public void showHTML(String html)
	// {
	// new AlertDialog.Builder(ShowExtras.this)
	// .setTitle("HTML")
	// .setMessage(html)
	// .setPositiveButton(android.R.string.ok, null)
	// .setCancelable(false)
	// .create()
	// .show();
	// }
	// }
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
				// ret += "<br />" + tds2.get(i).html();
				ret += "</div>";
				ret += "<hr size=\"1\" noshade>";
			}
			ret += "</body></html>";
			System.out.println(ret);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String test
		// =" <style type=\"text/css\">hr{color:#f00; background-color:#f00; border:0; height:5px;} .spann {font-size:20em; color:#444444; background-color:#FFFFFF; } .g10 { font-size:10em; }</style>";
		// ret+=test;
		// ret+="<span class=\"spann\">Test</span> <br /> test <br/> <hr class=\"hrr\"> <h1>ads</h1>";
		return ret;
	}

	protected void setWebViewContent() {
		if (choose == 1) {
			// mWebView.loadData(dataAktuellesW, "text/html", "utf-8");
			mWebView.loadDataWithBaseURL("http://www.fh-dortmund.de/de/studi/fb/9/studieng/400/aktuelles_stud.php", dataAktuellesW, "text/html", "utf-8", null);
		}
		if (choose == 0) {
			mWebView.setPadding(5, 0, 0, 0);
			mWebView.loadUrl("http://www.fh-dortmund.de/de/fb/4/isc/aktuelles/index.php");
		}
		if (choose == 3) {

			mWebView.loadData(dataKostBar, "text/html", "utf-8");

		}

	}

	public static String parseMensaKostBar() {
		Document doc;
		String ret = "";
		try {
			doc = Jsoup.connect("http://www.stwdo.de/index.php?id=248").get();
			Elements tds = doc.select("table.SpeiseplanWoche");
			// ret = "<table>";
			for (Element e : tds) {
				ret += e.toString();
				// ret += "<hr>";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	int choose;
	String dataAktuellesW;
	String dataMensa;
	String dataKostBar;

	public void initWebViewContent() {
		int arg2 = choose;

		if (arg2 == 1) {
			dataAktuellesW = parseNewsW();

		}
		if (arg2 == 3) {
			dataKostBar = parseMensaKostBar();

		}
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		choose = arg2;
		Thread t1 = new Thread(ShowExtras.this);
		t1.start();
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
