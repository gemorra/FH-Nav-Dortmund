package FHNav.model;

/**
 * Item from the Aktuelles RSS feed.
 * 
 */
public class AktuellesItem {

	private String title;
	private String description;
	private String link;
	private String pubDate;

	public String getDescription() {
		return description;
	}

	public String getHash() {
		return ""
				+ String.format("%s %s", this.title, this.description)
						.hashCode();
	}

	public String getLink() {
		return link;
	}

	public String getPubDate() {
		return pubDate;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
