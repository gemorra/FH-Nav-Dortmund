package FHNav.model;

import java.util.Date;

public class CanteenMenu {

	private String title;
	private String desc;
	private Date date;
	
	
	public CanteenMenu()
	{
		super();
	}
	
	public CanteenMenu(String title, String desc, Date date) {
		super();
		this.title = title;
		this.desc = desc;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
