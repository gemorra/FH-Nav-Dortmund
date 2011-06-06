package FHNav.model;

import java.io.Serializable;
import java.util.ArrayList;


public class Stundenplan implements Serializable
{	
	public interface Listener {
    void onModelStateUpdated(Stundenplan stundenplan);
	}
	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 3022166862004615344L;

	private ArrayList<Veranstaltung> veranstaltungen = new ArrayList<Veranstaltung>();
	
//	private final List<Listener> listeners = new ArrayList();
	
	public Stundenplan()
	{
		
	}

	public void refresh()
	{
		for(Veranstaltung ver:veranstaltungen)
		{
			ver.refresh();
		}
	}
	
	public void addVeranstaltung(Veranstaltung veranstaltung)
	{
		if(! veranstaltungen.contains(veranstaltung))
			veranstaltungen.add(veranstaltung);
	
	}
	
	public void removeVeranstaltung(Veranstaltung veranstaltung)
	{	
		if(veranstaltungen.contains(veranstaltung))
			veranstaltungen.remove(veranstaltung);		
	}

	public ArrayList<Veranstaltung> getVeranstaltungen() {
		return veranstaltungen;
	}


	public void setVeranstaltungen(ArrayList<Veranstaltung> veranstaltungen) {
		this.veranstaltungen = veranstaltungen;
	}
	
}
