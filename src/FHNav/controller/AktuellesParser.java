package FHNav.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;

import FHNav.model.AktuellesItem;
import FHNav.model.CanteenMenu;
import FHNav.sqlite.AktuellesDAO;

public class AktuellesParser {

	//private static final String RSS_URL = "http://www.zielkeb.de/rss.txt";
	private static final String RSS_URL = "http://www.inf.fh-dortmund.de/rss.php";
	
	
	private AktuellesDAO dao;
	
	public AktuellesParser(Context ctx) {
		dao = new AktuellesDAO(ctx);
	}
	
	public static String replaceCDATA(String s) {
		return s.replace("<![CDATA[", "").replace("]]>", "").trim();
	}

	public List<AktuellesItem> getNewAktuellesEntries() {
		return dao.getNewItemsFromList(getAktuelles());
	}
	
	public List<AktuellesItem> getAktuelles() {
		Document doc;
		List<AktuellesItem> items = new ArrayList<AktuellesItem>();
		//SimpleDateFormat sdfToDate = new SimpleDateFormat("dd.MM.yyyy");
		try {

			doc = Jsoup.parse(new URL(RSS_URL).openStream(), "ISO-8859-1", RSS_URL);
			
			Elements entries = doc.select("rss channel item");
			//Order ascending by date so we notify in the right order
			Collections.reverse(entries);
			
			for (Element i : entries) {
				AktuellesItem aktuellesItem = new AktuellesItem();
				aktuellesItem.setTitle(replaceCDATA(i.select("title").text()));
				aktuellesItem.setDescription(replaceCDATA(i.select("description").text()));
				aktuellesItem.setLink(replaceCDATA(i.select("link").text()));
				aktuellesItem.setPubDate(replaceCDATA(i.select("pubDate").text()));
				items.add(aktuellesItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return items;
	}


}
