package FHNav.controller;

import FHNav.gui.R;

/**
 * Klasse für kleine Transformationen (bis jetzt nur Wochentagsnamen und kürzel)
 * 
 * @author Moritz Wiechers
 * 
 */
public class Tools {

	public static String getShortWeekday(int day) {
		String ret = "";
		switch (day) {
		case 1:
			ret = "MO";
			break;
		case 2:
			ret = "TU";
			break;
		case 3:
			ret = "WE";
			break;
		case 4:
			ret = "TH";
			break;
		case 5:
			ret = "FR";
			break;
		case 6:
			ret = "SA";
			break;
		case 7:
			ret = "SU";
			break;

		}
		return ret;
	}

	public static int getWeekday(int day) {
		int ret = 0;
		switch (day) {
		case 1:
			ret = R.string.monday;
			break;
		case 2:
			ret = R.string.tuesday;
			break;
		case 3:
			ret = R.string.wednesday;
			break;
		case 4:
			ret = R.string.thursday;
			break;
		case 5:
			ret = R.string.friday;
			break;
		case 6:
			ret = R.string.saturday;
			break;
		case 7:
			ret = R.string.sunday;
			break;

		}
		return ret;
	}

}
