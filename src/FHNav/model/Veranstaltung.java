package FHNav.model;

import java.io.Serializable;
import java.util.Date;

public class Veranstaltung implements Serializable{
	
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 2879848547456847640L;
	private String dozent;
	private String name;
	private Date startzeit;
	private Date endzeit;
	private int wochentag;

	private Raum raum;
	private String studiengang;
	private int semester;
	
	public Veranstaltung()
	{		
	}
	
	public Veranstaltung(String dozent, String name, int wochentag, Date startzeit,
		Date endzeit, Raum raum, String studiengang, int semester) {
		setDozent(dozent);
		setName(name);
		setWochentag(wochentag);
		setStartzeit(startzeit);
		setEndzeit(endzeit);
		setRaum(raum);
		setStudiengang(studiengang);
		setSemester(semester);
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
					&& startzeit.equals(oVeranstaltung.startzeit) && endzeit.equals(oVeranstaltung.endzeit) )
			{
				return true;
			}
			else
				return false;
		}
		else 
			return false;
		
	}
	
	public int getWochentag() {
		return wochentag;
	}

	public void setWochentag(int wochentag) {
		this.wochentag = wochentag;
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
	public Date getStartzeit() {
		return startzeit;
	}
	public void setStartzeit(Date startzeit) {
		this.startzeit = startzeit;
	}
	public Date getEndzeit() {
		return endzeit;
	}
	public void setEndzeit(Date endzeit) {
		this.endzeit = endzeit;
	}
	public Raum getRaum() {
		return raum;
	}
	public void setRaum(Raum raum) {
		this.raum = raum;
	}
	
	public String getStudiengang() {
		return studiengang;
	}

	public void setStudiengang(String studiengang) {
		this.studiengang = studiengang;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}
}
