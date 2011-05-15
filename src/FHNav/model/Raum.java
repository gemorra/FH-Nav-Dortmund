package FHNav.model;

import java.io.Serializable;

public class Raum implements Serializable{

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = 7623281716077044373L;
	private char etage;
	private int nummer;
	private char trakt;
	private String ipAdresse;
	
	public Raum()
	{
		
	}
	
	public Raum(char trakt, char etage, int nummer )
	{
		setEtage(etage);
		setNummer(nummer);
		setTrakt(trakt);
	}
	
	
	public char getEtage() {
		return etage;
	}
	public void setEtage(char etage) {
		this.etage = etage;
	}
	public String getIpAdresse() {
		return ipAdresse;
	}
	public void setIpAdresse(String ipAdresse) {
		this.ipAdresse = ipAdresse;
	}
	public int getNummer() {
		return nummer;
	}
	public void setNummer(int nummer) {
		this.nummer = nummer;
	}
	public char getTrakt() {
		return trakt;
	}
	public void setTrakt(char trakt) {
		this.trakt = trakt;
	}
	
	@Override 
	public boolean equals(Object o)
	{
		if(o != null && o instanceof Raum)
		{
			Raum oRaum = (Raum) o;
			if(etage == oRaum.etage && nummer == oRaum.nummer && trakt == oRaum.trakt)
			{
				return true;
			}
			else
				return false;
		}
		else 
			return false;
		
	}
	
	
}
