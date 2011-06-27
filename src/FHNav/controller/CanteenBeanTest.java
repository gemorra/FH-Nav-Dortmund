package FHNav.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
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
		JSONObject jObject =null;
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
			System.out.println(jArray);
			return jArray;

			
		} catch (Exception e) {
			Log.e("a",e.toString());
		}
		

		return null;		
	}
	
	
	
	
	public static JSONArray getMensaplanJSON() {
		JSONObject jObject =null;
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
			
			JSONArray jArray = jObject.getJSONArray("MENUS");
			System.out.println(jArray);
			return jArray;

			
		} catch (Exception e) {
			Log.e("a",e.toString());
		}
		

		return null;		
	}
	
	public static ArrayList<CanteenMenu> getMenus()
	{
		JSONArray jArray = CanteenBeanTest.getMensaplanJSON();
		JSONArray jArray2 = CanteenBeanTest.getMensaplan2JSON();
		SimpleDateFormat sdfToDate = new SimpleDateFormat("dd.MM.yyyy");
		ArrayList<CanteenMenu> menus = new ArrayList<CanteenMenu>();
		try {
			for (int i = 0; i < jArray.length(); i++) {

				JSONObject json_data = jArray.getJSONObject(i);
				
				String title = json_data.getString("MENUTITLE");
				String desc = json_data.getString("MENUDESC");
				Date dt = sdfToDate.parse(json_data.getString("DATE"));
				CanteenMenu cm = new CanteenMenu(title,desc,dt);
				Log.e("Test",json_data.toString());
				menus.add(cm);
			}
			for (int i = 0; i < jArray2.length(); i++) {

				JSONObject json_data = jArray2.getJSONObject(i);
				
				String title = json_data.getString("MENUTITLE");
				String desc = json_data.getString("MENUDESC");
				CanteenMenu cm = new CanteenMenu(title,desc,null);
				Log.e("Test",json_data.toString());
				menus.add(cm);
			}
			
		} catch (Exception e) {
			Log.e("error", "Error while Parsing JSON");
		}
		
		
		return menus;
	}
	
	
	
	

}
