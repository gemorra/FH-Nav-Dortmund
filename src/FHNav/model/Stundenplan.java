package FHNav.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasse des Stundenplans
 * 
 * @author Moritz Wiechers
 * 
 */
public class Stundenplan implements Serializable {
	public interface Listener {
		void onModelStateUpdated(Stundenplan stundenplan);
	}

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 3022166862004615344L;

	private ArrayList<Veranstaltung> veranstaltungen;

	public Stundenplan() {
		veranstaltungen = new ArrayList<Veranstaltung>();
	}

	public void addVeranstaltung(Veranstaltung veranstaltung) {
		if (!veranstaltungen.contains(veranstaltung)) {
			veranstaltungen.add(veranstaltung);
		}

	}

	public ArrayList<Veranstaltung> getVeranstaltungen() {
		return veranstaltungen;
	}

	public void refresh() {
		for (Veranstaltung ver : veranstaltungen) {
			ver.refresh();
		}
	}

	public void removeVeranstaltung(Veranstaltung veranstaltung) {
		if (veranstaltungen.contains(veranstaltung)) {
			veranstaltungen.remove(veranstaltung);
		}
	}

	public void setVeranstaltungen(ArrayList<Veranstaltung> veranstaltungen) {
		this.veranstaltungen = veranstaltungen;
	}

}
