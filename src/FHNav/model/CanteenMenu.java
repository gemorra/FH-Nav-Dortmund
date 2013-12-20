package FHNav.model;

import java.util.Date;

/**
 * Klasse für die Speiseplandaten
 * 
 * @author Moritz Wiechers
 * 
 */
public class CanteenMenu {

	private String title;
	private String desc;
	private Date date;

	public CanteenMenu() {
		super();
	}

	public CanteenMenu(String title, String desc, Date date) {
		super();
		this.title = title;
		this.desc = desc;
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public String getDesc() {
		return desc;
	}

	public String getTitle() {
		return title;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
