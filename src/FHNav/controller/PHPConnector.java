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
import android.content.Context;
import android.util.Log;

public class PHPConnector {

	/**
	 * Generiert eine Liste mit allen Stundenplänen
	 * 
	 * @return Liste mit allen Stundenplänen
	 */
	public static ArrayList<String> getAllBranches(Context ctx) {

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("type", "getAllBranches"));

		// http post
		ArrayList<String> ret = new ArrayList<String>();

		JSONArray jArray = getJSONArray(nameValuePairs, ctx);
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

	/**
	 * Ruft die entsprechende php Datei auf dem Server mit den angegebenen
	 * Parametern auf und parst die Rückgabe in ein JSONArray
	 * 
	 * @param nvp
	 *            Gibt im Endeffekt an welche mysql-db anfrage ausgeführt werden
	 *            soll. (liegen in der php datei) Dies findet über einfach Get
	 *            Variablen in der URL statt
	 * @return
	 */
	public static JSONArray getJSONArray(ArrayList<NameValuePair> nvp,
			Context ctx) {
		String result = "";

		try {

			HttpClient httpclient = new DefaultHttpClient();

			HttpPost httppost = new HttpPost(SettingsManager.getPathToFile(ctx));

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

	/**
	 * Generiert den Stundenplan zum passenden Branch s. Da jede Stunde einzeln
	 * in der DB liegt werden diese zusammengefasst zu einer Veranstaltung
	 * 
	 * @param s
	 *            der Branch aus dem der Stundenplan generiert werden soll. (^=
	 *            Studiengang+Semester)
	 * @return der generierte Stundenplan
	 */
	public static Stundenplan getStundenplanFromMysql(String s, Context ctx) {

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

		nameValuePairs.add(new BasicNameValuePair("type", "getDetailOfBranch"));
		nameValuePairs.add(new BasicNameValuePair("pos", s));

		Stundenplan stundenplan = new Stundenplan();

		JSONArray jArray = getJSONArray(nameValuePairs, ctx);

		try {
			for (int i = 0; i < jArray.length(); i++) {

				JSONObject json_data = jArray.getJSONObject(i);

				String dozent = json_data.getString("staff");
				String name = json_data.getString("name");
				String raum = json_data.getString("location");
				String studiengang = json_data.getString("pos");
				String semester = json_data.getString("semester");
				String studentSet = json_data.getString("studentSet");
				int wochentag = json_data.getInt("dayOfWeek");
				int start = json_data.getInt("start");
				int dauer = json_data.getInt("duration");
				String type = json_data.getString("type");
				Veranstaltung veranstaltung = new Veranstaltung(dozent, name,
						wochentag, start, dauer, raum, studiengang, semester,
						type, studentSet);

				if (stundenplan.getVeranstaltungen().contains(veranstaltung)) {
					int index = stundenplan.getVeranstaltungen().indexOf(
							veranstaltung);
					Veranstaltung tmpVeranstaltung = stundenplan
							.getVeranstaltungen().get(index);
					tmpVeranstaltung.setDauer(stundenplan.getVeranstaltungen()
							.get(index).getDauer() + 1);
					if (tmpVeranstaltung.getStart() > veranstaltung.getStart()) {
						tmpVeranstaltung.setStart(veranstaltung.getStart());
					}
				} else {
					stundenplan.addVeranstaltung(veranstaltung);
				}

			}
		} catch (Exception e) {
		}

		stundenplan.refresh();
		return stundenplan;

	}
}
