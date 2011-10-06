package FHNav.controller;

import java.util.ArrayList;

import FHNav.model.CanteenMenu;
import FHNav.model.Stundenplan;
import FHNav.model.Veranstaltung;
import android.app.Activity;
import android.content.Context;

public class MainApplicationManager {

	public static void refreshData() {
		dataKostBar = CanteenBeanTest.getMenuKostbar();
		dataMensa = CanteenBeanTest.getMenuMensa();
	}

	private static ArrayList<CanteenMenu> dataKostBar;
	private static ArrayList<CanteenMenu> dataMensa;

	private static Context ctx;
	private static Activity currentAcctivity;
	private static Stundenplan stundenplan;

	private static BreadthFirstSearchTest bfst;

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
		if (stundenplan == null) {
			return new Stundenplan();
		} else
			return stundenplan;
	}

	public static void setStundenplan(Stundenplan stundenplan) {
		MainApplicationManager.stundenplan = stundenplan;
	}

	public static ArrayList<Veranstaltung> getVeranstaltungen() {
		ArrayList<Veranstaltung> m_orders = new ArrayList<Veranstaltung>();

		if (MainApplicationManager.getStundenplan().getVeranstaltungen().size() > 990) {
			for (int i = 0; i < 990; i++) {
				m_orders.add(MainApplicationManager.getStundenplan().getVeranstaltungen().get(i));
			}
			return m_orders;
		} else
			return MainApplicationManager.getStundenplan().getVeranstaltungen();

	}
}
