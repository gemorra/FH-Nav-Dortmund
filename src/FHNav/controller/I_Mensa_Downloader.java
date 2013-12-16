package FHNav.controller;

/**
 * Dient als Interface um der View der Activity Extras (Speiseplan) mitzuteilen,
 * dass der Speiseplan runtergeladen wurde.
 * 
 * @author Moritz Wiechers
 * 
 */
public interface I_Mensa_Downloader {
	/**
	 * wird aufgerufen wenn Speiseplan runtergeladen wurde
	 */
	public abstract void downloadDone();

}
