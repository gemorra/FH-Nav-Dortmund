package FHNav.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import FHNav.model.CanteenMenu;

public class CanteenBeanTest {

	/**
	 * Parst den Kalender der Kostbar (jsoup)
	 * 
	 * @return
	 */
	private static SimpleDateFormat DATE_FORMAT_SIMPLE = new SimpleDateFormat(
			"dd.MM.yyyy");

	/**
	 * Gets the date from a String that contains a date somewhere in between.
	 * Looks for dd.MM.yyyy and similar.
	 * 
	 * @param s
	 *            input string
	 * @return the found Date. null if no date can be found within the input
	 *         string.
	 * @throws ParseException
	 *             if the string cannot be parsed
	 */
	private static Date dateFromString(String s) throws ParseException {
		Date date = null;
		System.out.println("table text: " + s);
		Pattern pattern = Pattern.compile("(\\d{1,2}\\.\\d{1,2}\\.\\d{2,4})");
		Matcher matcher = pattern.matcher(s);
		if (matcher.find()) {
			date = DATE_FORMAT_SIMPLE.parse(matcher.group(1));
			System.out.println("found match: " + matcher.group(1));
		}

		return date;

	}

	// TODO rewrite this
	public static ArrayList<CanteenMenu> getMenuKostbar() {
		Document doc;
		ArrayList<CanteenMenu> menus = new ArrayList<CanteenMenu>();

		try {

			doc = Jsoup.connect("http://www.stwdo.de/index.php?id=248").get();

			Elements tds = doc.select("table.SpeiseplanWoche");

			for (Element e : tds) {

				String dat = e.select("caption").html();
				Date dt = dateFromString(dat);
				if (dt == null) {
					// no date found in this cell
					continue;
				}
				Elements m = e.select("td.Tabellen-spalte-2");
				for (Element e2 : m) {

					String desc = e2.select("p").html();

					desc = StringEscapeUtils.unescapeHtml(desc);

					if (!(desc.startsWith("(") && desc.endsWith(")"))) {
						CanteenMenu cm = new CanteenMenu("", desc, dt);
						menus.add(cm);
					}
				}

			}
			System.out.println("Kostbar parsed");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return menus;
	}

	/**
	 * Parst den Kalender der Mensa (jsoup)
	 * 
	 * @return
	 */
	public static ArrayList<CanteenMenu> getMenuMensa() {
		Document doc;
		ArrayList<CanteenMenu> menus = new ArrayList<CanteenMenu>();
		try {

			doc = Jsoup.connect("http://www.stwdo.de/index.php?id=127").get();

			Elements tds = doc.select("table.SpeiseplanWoche");

			for (Element e : tds) {
				String dat = e.select("caption").html();
				Date dt = dateFromString(dat);
				if (dt == null) {
					// no date found in this cell
					continue;
				}
				Elements m = e.select("td.Tabellen-spalte-2");
				// Elements m2 = e.select("td.Tabellen-spalte-1");
				for (int i = 0; i < m.size(); i++) {
					String desc = m.get(i).select("p").html();
					desc = StringEscapeUtils.unescapeHtml(desc);
					// String title = m2.get(i).select("p").html();
					// title= StringEscapeUtils.unescapeHtml(title);

					CanteenMenu cm = new CanteenMenu("", desc, dt);
					menus.add(cm);
				}

			}
			Elements tdss = doc.select("table.WochenSpecials");

			for (Element e2 : tdss) {

				// String dat = e.select("caption").html();
				// Date dt = sdfToDate.parse(dat);
				Elements ms = e2.select("td.Tabellen-spalte-2");

				for (int i = 0; i < ms.size(); i++) {
					String desc = ms.get(i).select("p").html();
					desc = StringEscapeUtils.unescapeHtml(desc);
					// String title = m2.get(i).select("p").html();
					// title= StringEscapeUtils.unescapeHtml(title);
					CanteenMenu cm = new CanteenMenu("", desc, null);
					menus.add(cm);
				}

			}
			System.out.println("Mensa parsed");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return menus;
	}

}
