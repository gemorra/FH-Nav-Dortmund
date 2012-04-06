package FHNav.controller;

import java.util.ArrayList;

import FHNav.model.CanteenMenu;
import FHNav.model.Stundenplan;
import FHNav.model.Veranstaltung;
import android.app.Activity;

/**
 * Diese Klasse dient als "Sammelcontainer" Der Stundenplan und Speiseplan u.A.
 * werden so "appweit" verwaltet.
 * 
 * @author Moritz Wiechers
 * 
 */
public class MainApplicationManager {

	/**
	 * aktualisiert den Speiseplan
	 */

	public static void refreshData() {
		downloading = true;
		dataKostBar = CanteenBeanTest.getMenuKostbar();
		dataMensa = CanteenBeanTest.getMenuMensa();
		downloading = false;
		fireDownloader();
	}

	private static float density;
	
	public static float getDensity() {
		return density;
	}

	public static void setDensity(float density) {
		MainApplicationManager.density = density;
	}

	private static ArrayList<I_Mensa_Downloader> listener = new ArrayList<I_Mensa_Downloader>();

	private static void fireDownloader() {
		for (I_Mensa_Downloader i : listener) {
			i.downloadDone();
		}
	}

	public static void addListener(I_Mensa_Downloader i) {
		listener.add(i);
	}

	public static void removeListener(I_Mensa_Downloader i) {
		listener.remove(i);
	}

	private static boolean finish = false;

	public static boolean isFinish() {
		return finish;
	}

	public static void setFinish(boolean finish) {
		MainApplicationManager.finish = finish;
	}

	private static String selectedBranch = "";

	public static String getSelectedBranch() {
		return selectedBranch;
	}

	public static void setSelectedBranch(String selectedBranch) {
		MainApplicationManager.selectedBranch = selectedBranch;
	}

	private static boolean downloading = false;

	private static ArrayList<CanteenMenu> dataKostBar;
	private static ArrayList<CanteenMenu> dataMensa;

	private static Activity currentAcctivity;
	private static Stundenplan stundenplan;

	private static BreadthFirstSearchTest bfst;

	public static boolean isDownloading() {
		return downloading;
	}

	public static void setDownloading(boolean downloading) {
		MainApplicationManager.downloading = downloading;
	}

	public static ArrayList<CanteenMenu> getDataKostBar() {
		return dataKostBar;
	}

	public static void setDataKostBar(ArrayList<CanteenMenu> dataKostBar) {
		MainApplicationManager.dataKostBar = dataKostBar;
	}

	public static ArrayList<CanteenMenu> getDataMensa() {
		return dataMensa;
	}

	public static void setDataMensa(ArrayList<CanteenMenu> dataMensa) {
		MainApplicationManager.dataMensa = dataMensa;
	}

	public static BreadthFirstSearchTest getBfst() {
		return bfst;
	}

	public static void setBfst(BreadthFirstSearchTest bfst) {
		MainApplicationManager.bfst = bfst;
	}

	public static Activity getCurrentAcctivity() {
		return currentAcctivity;
	}

	public static void setCurrentAcctivity(Activity currentAcctivity) {
		MainApplicationManager.currentAcctivity = currentAcctivity;
	}

	public static Stundenplan getStundenplan() {
		if (stundenplan == null) {
			return new Stundenplan();
		} else
			return stundenplan;
	}

	public static void setStundenplan(Stundenplan stundenplan) {
		MainApplicationManager.stundenplan = stundenplan;
	}

	public static ArrayList<Veranstaltung> getVeranstaltungen() {
		return MainApplicationManager.getStundenplan().getVeranstaltungen();

	}
}
