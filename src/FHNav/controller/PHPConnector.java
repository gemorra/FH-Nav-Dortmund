package FHNav.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import FHNav.model.Stundenplan;
import FHNav.model.Veranstaltung;
import android.util.Log;

public class PHPConnector {

	private static String pathToFile ="http://gemorra.de/test.php";
	
	public static String getPathToFile() {
		return pathToFile;
	}

	public static void setPathToFile(String pathToFile) {
		PHPConnector.pathToFile = pathToFile;
	}

	public static JSONArray getJSONArray(ArrayList<NameValuePair> nvp) {
		String result = "";

		// http post

		try {

			HttpClient httpclient = new DefaultHttpClient();

			HttpPost httppost = new HttpPost(pathToFile);

			httppost.setEntity(new UrlEncodedFormEntity(nvp));

			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();

			InputStream is = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "UTF-8"), 8);

			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "n");

			}

			is.close();

			result = sb.toString();

		} catch (Exception e) {

			Log.e("log_tag", "Error converting result " + e.toString());

		}

		// parse json data
		try {

			JSONArray jArray = new JSONArray(result);

			return jArray;

		} catch (JSONException e) {

			Log.e("log_tag", "Error parsing data " + e.toString());
		}

		return null;

	}

	public static ArrayList<String> getAllBranches() {

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("type", "getAllBranches"));

		// http post
		ArrayList<String> ret = new ArrayList<String>();

		JSONArray jArray = getJSONArray(nameValuePairs);
		try {
			for (int i = 0; i < jArray.length(); i++) {

				JSONObject json_data = jArray.getJSONObject(i);
				ret.add(json_data.getString("pos"));
			}
		} catch (Exception e) {
			Log.e("error", "Error while Parsing JSON");
		}

		return ret;
	}

	public static Stundenplan getStundenplanFromMysql(String s) {


		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("type", "getDetailOfBranch"));
		nameValuePairs.add(new BasicNameValuePair("pos", s));

		Stundenplan stundenplan = new Stundenplan();

		Log.e("", "Josen");
		JSONArray jArray = getJSONArray(nameValuePairs);
		Log.e("", "Josen2");
		
		try {
			for (int i = 0; i < jArray.length(); i++) {
				
				JSONObject json_data = jArray.getJSONObject(i);
				Log.e("asd",json_data.getString("name") ) ;
				String dozent = json_data.getString("staff");
				String name = json_data.getString("name");
				String raum = json_data.getString("location");
				String studiengang = json_data.getString("pos");
				String semester = json_data.getString("semester");
				int wochentag = json_data.getInt("dayOfWeek");
				int start = json_data.getInt("start");
				int dauer = json_data.getInt("duration");
				String type = json_data.getString("type");
				Veranstaltung veranstaltung = new Veranstaltung(dozent, name, wochentag, start, dauer, raum, studiengang, semester,type);
				stundenplan.addVeranstaltung(veranstaltung);
			}
		} catch (Exception e) {
		}
		return stundenplan;

	}
}
