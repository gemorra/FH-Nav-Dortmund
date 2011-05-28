package FHNav.model;

import java.io.Serializable;

public class Veranstaltung implements Serializable{
	
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 2879848547456847640L;
	private String dozent;
	private String name;
	private int start;
	private int dauer;
	private int wochentag;
	private String type;
	private String raum;
	private String studiengang;
	private String semester;
	
	public Veranstaltung()
	{		
	}
	
	public Veranstaltung(String dozent, String name, int wochentag, int start,
		int duration, String raum, String studiengang, String semester, String type) {
		setDozent(dozent);
		setName(name);
		setWochentag(wochentag);
		setStart(start);
		setDuration(duration);
		setRaum(raum);
		setStudiengang(studiengang);
		setSemester(semester);
		setType(type);
	}
	
	
	/**
	 * Erstmal ohne Semester und Studiengang
	 */
	@Override
	public boolean equals(Object o)
	{	
		if(o != null && o instanceof Veranstaltung)
		{
			Veranstaltung oVeranstaltung = (Veranstaltung) o;
			if(raum.equals(oVeranstaltung.raum) && dozent.equals(oVeranstaltung.dozent) && name.equals(oVeranstaltung) 
					&& (start==oVeranstaltung.start) && (dauer==oVeranstaltung.dauer) )
			{
				return true;
			}
			else
				return false;
		}
		else 
			return false;
		
	}
	
	public int getDauer() {
		return dauer;
	}

	public void setDauer(int dauer) {
		this.dauer = dauer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSemester() {
		return semester;
	}

	public int getWochentag() {
		return wochentag;
	}

	public void setWochentag(int wochentag) {
		this.wochentag = wochentag;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getDuration() {
		return dauer;
	}

	public void setDuration(int duration) {
		this.dauer = duration;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getDozent() {
		return dozent;
	}
	public void setDozent(String dozent) {
		this.dozent = dozent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getRaum() {
		return raum;
	}
	public void setRaum(String raum) {
		this.raum = raum;
	}
	
	public String getStudiengang() {
		return studiengang;
	}

	public void setStudiengang(String studiengang) {
		this.studiengang = studiengang;
	}

}
