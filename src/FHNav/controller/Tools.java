package FHNav.controller;

import FHNav.gui.R;

public class Tools {

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
