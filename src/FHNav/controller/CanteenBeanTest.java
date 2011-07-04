package FHNav.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import FHNav.model.CanteenMenu;
import android.util.Log;

public class CanteenBeanTest {

	/** Called when the activity is first created. */
	private static final String SOAP_ACTION = "http://ws.inf.fh-dortmund.de/CanteenBeanService/CanteenBean/";
	private static final String METHOD_NAME = "getTastyMenusForWeek";
	private static final String METHOD_NAME2 = "getTastySpecialsForWeek";
	private static final String NAMESPACE = "http://canteen.fhapp.fhdo.de/";
	private static final String URL = "http://ws.inf.fh-dortmund.de/CanteenBeanService/CanteenBean?xsd=1";

	public static JSONArray getMensaplan2JSON() {
		JSONObject jObject = null;
		try {

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME2);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.debug = true;
			androidHttpTransport.call(SOAP_ACTION, envelope);

			Object result = (Object) envelope.getResponse();

			jObject = new JSONObject(result.toString());

			JSONArray jArray = jObject.getJSONArray("MENUS");
			Log.e("result", result.toString());
			return jArray;

		} catch (Exception e) {
			Log.e("a", e.toString());
		}

		return null;
	}

	public static JSONArray getMensaplanJSON() {
		JSONObject jObject = null;
		JSONArray jArray = null;
		try {

			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);

			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
			androidHttpTransport.debug = true;
			androidHttpTransport.call(SOAP_ACTION, envelope);

			Object result = (Object) envelope.getResponse();

			jObject = new JSONObject(result.toString());

			System.out.println("Parsing!!!");
			System.out.println(jObject.toString());
			// JSONObject arr = jObject.getJSONObject("MENUS");

			jArray = jObject.getJSONArray("MENUS");

			return null;

		} catch (Exception e) {
			Log.e("a", e.toString());
		}

		return jArray;
	}

	public static ArrayList<CanteenMenu> getMenusMensa() {
		JSONArray jArray = CanteenBeanTest.getMensaplanJSON();
		JSONArray jArray2 = CanteenBeanTest.getMensaplan2JSON();
		SimpleDateFormat sdfToDate = new SimpleDateFormat("dd.MM.yyyy");
		ArrayList<CanteenMenu> menus = new ArrayList<CanteenMenu>();
		try {
			for (int i = 0; i < jArray.length(); i++) {

				JSONObject json_data = jArray.getJSONObject(i);

				String title = json_data.getString("MENUTITLE");
				String desc = json_data.getString("MENUDESC");
				System.out.println(json_data);

				Date dt = sdfToDate.parse(json_data.getString("DATE"));
				CanteenMenu cm = new CanteenMenu(title, desc, dt);
				Log.e("Test", json_data.toString());
				menus.add(cm);

			}
			for (int i = 0; i < jArray2.length(); i++) {

				JSONObject json_data = jArray2.getJSONObject(i);

				String title = json_data.getString("MENUTITLE");
				String desc = json_data.getString("MENUDESC");
				CanteenMenu cm = new CanteenMenu(title, desc, null);
				Log.e("Test", json_data.toString());
				menus.add(cm);
			}

		} catch (Exception e) {
			Log.e("error", "Error while Parsing JSON");
		}

		return menus;
	}

	public static ArrayList<CanteenMenu> getMenuKostbar() {
		Document doc;
		ArrayList<CanteenMenu> menus = new ArrayList<CanteenMenu>();
		SimpleDateFormat sdfToDate = new SimpleDateFormat("dd.MM.yyyy");
		try {

			doc = Jsoup.connect("http://www.stwdo.de/index.php?id=248").get();

			Elements tds = doc.select("table.SpeiseplanWoche");

			for (Element e : tds) {
				String dat = e.select("caption").html();
				Date dt = sdfToDate.parse(dat);
				Elements m = e.select("td.Tabellen-spalte-2");
				for (Element e2 : m) {

					String desc = e2.select("p").html();

					desc = StringEscapeUtils.unescapeHtml(desc);
					CanteenMenu cm = new CanteenMenu("", desc, dt);
					menus.add(cm);
				}
				// Elements m = e.select("td.Tabellen-spalte-1");
				// for(Element e2:m)
				// {
				// String desc = e2.select("p").html();
				// CanteenMenu cm = new CanteenMenu(desc, "", dt);
				// menus.add(cm);
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menus;
	}

	public static ArrayList<CanteenMenu> getMenuMensa() {
		Document doc;
		ArrayList<CanteenMenu> menus = new ArrayList<CanteenMenu>();
		SimpleDateFormat sdfToDate = new SimpleDateFormat("dd.MM.yyyy");
		try {

			doc = Jsoup.connect("http://www.stwdo.de/index.php?id=127").get();

			Elements tds = doc.select("table.SpeiseplanWoche");

			for (Element e : tds) {
				String dat = e.select("caption").html();
				Date dt = sdfToDate.parse(dat);
				Elements m = e.select("td.Tabellen-spalte-2");
//				Elements m2 = e.select("td.Tabellen-spalte-1");
				for (int i = 0; i < m.size(); i++) {
					String desc = m.get(i).select("p").html();
					desc = StringEscapeUtils.unescapeHtml(desc);
					// String title = m2.get(i).select("p").html();
					// title= StringEscapeUtils.unescapeHtml(title);
					CanteenMenu cm = new CanteenMenu("", desc, dt);
					menus.add(cm);
				}
				// Elements m = e.select("td.Tabellen-spalte-1");
				// for(Element e2:m)
				// {
				// String desc = e2.select("p").html();
				// CanteenMenu cm = new CanteenMenu(desc, "", dt);
				// menus.add(cm);
				// }

				
			}
			Elements tdss = doc.select("table.WochenSpecials");
			System.out.println(tdss.size());
			for (Element e2 : tdss) {
				System.out.println("josen");
				// String dat = e.select("caption").html();
				// Date dt = sdfToDate.parse(dat);
				Elements ms = e2.select("td.Tabellen-spalte-2");
				System.out.println("MS:" + ms.size());
				for (int i = 0; i < ms.size(); i++) {
					String desc = ms.get(i).select("p").html();
					desc = StringEscapeUtils.unescapeHtml(desc);
					// String title = m2.get(i).select("p").html();
					// title= StringEscapeUtils.unescapeHtml(title);
					CanteenMenu cm = new CanteenMenu("", desc, null);
					menus.add(cm);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return menus;
	}

}
