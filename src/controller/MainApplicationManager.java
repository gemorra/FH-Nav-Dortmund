package controller;

import FHNav.model.Stundenplan;
import android.app.Activity;
import android.content.Context;

public class MainApplicationManager {

	private static Context ctx;
	private static Activity currentAcctivity;
	private static Stundenplan stundenplan;
	
	public static Context getCtx() {
		return ctx;
	}
	public static void setCtx(Context ctx) {
		MainApplicationManager.ctx = ctx;
	}
	public static Activity getCurrentAcctivity() {
		return currentAcctivity;
	}
	public static void setCurrentAcctivity(Activity currentAcctivity) {
		MainApplicationManager.currentAcctivity = currentAcctivity;
	}
	public static Stundenplan getStundenplan() {
		return stundenplan;
	}
	public static void setStundenplan(Stundenplan stundenplan) {
		MainApplicationManager.stundenplan = stundenplan;
	}
	

}
