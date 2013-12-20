package FHNav.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Stellt die einzelne Veranstaltung dar
 * 
 * @author Moritz Wiechers
 * 
 */
public class Veranstaltung implements Serializable, Comparable<Veranstaltung> {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 2879848547456847640L;

	/**
	 * Transformation für das Ende einer Veranstaltung
	 * 
	 * @param value
	 * @param duration
	 * @return
	 */
	public static Date getEndDateFromInt(int value, int duration) {
		Date dt;
		value += (duration - 1);
		dt = getStartDateFromInt(value);
		dt.setMinutes(dt.getMinutes() + 45);
		return dt;

	}

	/**
	 * Transformation für den Anfang einer Veranstaltung
	 * 
	 * @param value
	 * @return
	 */
	public static Date getStartDateFromInt(int value) {
		value++;
		Date dt = new Date();
		switch (value) {
		case 1:
			dt.setHours(8);
			dt.setMinutes(30);
			break;
		case 2:
			dt.setHours(9);
			dt.setMinutes(20);
			break;
		case 3:
			dt.setHours(10);
			dt.setMinutes(15);
			break;
		case 4:
			dt.setHours(11);
			dt.setMinutes(5);
			break;
		case 5:
			dt.setHours(12);
			dt.setMinutes(0);
			break;
		case 6:
			dt.setHours(12);
			dt.setMinutes(50);
			break;
		case 7:
			dt.setHours(14);
			dt.setMinutes(15);
			break;
		case 8:
			dt.setHours(15);
			dt.setMinutes(5);
			break;
		case 9:
			dt.setHours(16);
			dt.setMinutes(0);
			break;
		case 10:
			dt.setHours(16);
			dt.setMinutes(50);
			break;
		case 11:
			dt.setHours(17);
			dt.setMinutes(45);
			break;
		case 12:
			dt.setHours(18);
			dt.setMinutes(35);
			break;
		}
		return dt;
	}

	private String dozent;
	private String name;
	private int start;
	private int dauer;
	private int wochentag;
	private String type;
	private String raum;
	private String studiengang;

	private String semester;
	private String studentSet;

	private Date startTime;

	private Date endTime;

	public Veranstaltung() {
	}

	public Veranstaltung(String dozent, String name, int wochentag, int start,
			int duration, String raum, String studiengang, String semester,
			String type, String studentSet) {
		setDozent(dozent);
		setName(name);
		setWochentag(wochentag);
		setStart(start);
		setDuration(duration);
		setRaum(raum);
		setStudiengang(studiengang);
		setSemester(semester);
		setType(type);
		setStudentSet(studentSet);

	}

	/**
	 * Dient der Sortierung nach dem Datum
	 */
	public int compareTo(Veranstaltung ver) {
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;

		if (this == ver) {
			return EQUAL;
		} else {
			if (this.wochentag < ver.wochentag) {
				return BEFORE;
			} else if (this.wochentag > ver.wochentag) {
				return AFTER;
			} else if (this.wochentag == ver.wochentag) {
				if (this.start <= ver.start) {
					return BEFORE;
				} else {
					return AFTER;
				}
			}
		}

		return EQUAL;

	}

	/**
	 * Vergleich auf Gleichheit
	 */
	@Override
	public boolean equals(Object o) {
		if ((o != null) && (o instanceof Veranstaltung)) {
			Veranstaltung oVeranstaltung = (Veranstaltung) o;
			if (raum.equals(oVeranstaltung.raum)
					&& dozent.equals(oVeranstaltung.dozent)
					&& name.equals(oVeranstaltung.name)
					&& studiengang.equals(oVeranstaltung.studiengang)
					&& (type.equals(oVeranstaltung.type))
					&& (studentSet.equals(oVeranstaltung.studentSet))
					&& (wochentag == oVeranstaltung.wochentag)
					&& semester.equals(oVeranstaltung.semester)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	public int getDauer() {
		return dauer;
	}

	public String getDozent() {
		return dozent;
	}

	public int getDuration() {
		return dauer;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getName() {
		return name;
	}

	public String getRaum() {
		return raum;
	}

	public String getSemester() {
		return semester;
	}

	public int getStart() {
		return start;
	}

	public Date getStartTime() {
		return startTime;
	}

	public String getStudentSet() {
		return studentSet;
	}

	public String getStudiengang() {
		return studiengang;
	}

	public String getType() {
		return type;
	}

	public int getWochentag() {
		return wochentag;
	}

	public void refresh() {
		setStartTime(getStartDateFromInt(start));
		setEndTime(getEndDateFromInt(start, dauer));
	}

	public void setDauer(int dauer) {
		this.dauer = dauer;
	}

	public void setDozent(String dozent) {
		this.dozent = dozent;
	}

	public void setDuration(int duration) {
		this.dauer = duration;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRaum(String raum) {
		this.raum = raum;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setStudentSet(String studentSet) {
		this.studentSet = studentSet;
	}

	public void setStudiengang(String studiengang) {
		this.studiengang = studiengang;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setWochentag(int wochentag) {
		this.wochentag = wochentag;
	}

}
