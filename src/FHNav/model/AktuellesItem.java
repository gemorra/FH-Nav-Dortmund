package FHNav.model;

/**
 * @author Admin
 *
 */
public class AktuellesItem {

	private String title;
	private String description;
	private String link;
	private String pubDate;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	
	public String getHash() {
		return ""+String.format("%s %s", this.title, this.description).hashCode();
	}
	
	
	
}
