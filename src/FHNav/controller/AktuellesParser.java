package FHNav.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import FHNav.model.AktuellesItem;
import FHNav.sqlite.AktuellesDAO;
import android.content.Context;

/**
 * Parser for Aktuelles RSS feed.
 *
 */
public class AktuellesParser {
	private static final String RSS_URL = "http://www.inf.fh-dortmund.de/rss.php";
	public static final String WEB_URL = "http://www.fh-dortmund.de/de/fb/4/isc/aktuelles/index.php";
	
	private AktuellesDAO dao;
	
	public AktuellesParser(Context ctx) {
		dao = new AktuellesDAO(ctx);
	}

	/**
	 * Removes any HTML tags from a list of JSoup text nodes.
	 * 
	 * @param nodes
	 *            list of text nodes
	 * @return text without HTML tags
	 */
	private static String getTextWithoutTags(List<TextNode> nodes) {
		StringBuilder sb = new StringBuilder();

		for (TextNode n : nodes) {
			sb.append(n.getWholeText().replaceAll("<(.*?)>", ""));
		}
		return sb.toString();
	}

	/**
	 * @param s
	 *            text with CDATA tags
	 * @return text without CDATA tags
	 */
	private static String replaceCDATA(String s) {
		return s.replace("<![CDATA[", "").replace("]]>", "").trim();
	}
	

	/**
	 * Loads Aktuelles from the RSS Feed.
	 * 
	 * @return list of items
	 */
	public List<AktuellesItem> getAktuelles() {
		Document doc;
		List<AktuellesItem> items = new ArrayList<AktuellesItem>();
		try {

			doc = Jsoup.parse(new URL(RSS_URL).openStream(), "ISO-8859-1",
					RSS_URL);

			Elements entries = doc.select("rss channel item");
			// Order ascending by date so we notify in the right order
			Collections.reverse(entries);

			for (Element i : entries) {
				AktuellesItem aktuellesItem = new AktuellesItem();
				aktuellesItem.setTitle(replaceCDATA(i.select("title").get(0)
						.text()));
				aktuellesItem.setDescription(getTextWithoutTags(i
						.select("description").get(0).textNodes()));
				aktuellesItem.setLink(replaceCDATA(i.select("link").get(0)
						.text()));
				aktuellesItem.setPubDate(replaceCDATA(i.select("pubDate")
						.get(0).text()));
				items.add(aktuellesItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return items;
	}

	/**
	 * Gets all entries from Aktuelles which have not been seen yet.
	 * 
	 * @return
	 */
	public List<AktuellesItem> getNewAktuellesEntries() {
		return dao.getNewItemsFromList(getAktuelles());
	}

}
