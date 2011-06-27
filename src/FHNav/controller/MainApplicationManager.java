package FHNav.controller;

import java.util.ArrayList;

import FHNav.model.Stundenplan;
import FHNav.model.Veranstaltung;
import android.app.Activity;
import android.content.Context;
import android.widget.BaseAdapter;

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
	
	public static ArrayList<Veranstaltung> getVeranstaltungen() {
		ArrayList<Veranstaltung> m_orders = new ArrayList<Veranstaltung>();
		
		if(MainApplicationManager.getStundenplan().getVeranstaltungen().size()>990){		
			for(int i=0; i<990; i++)
			{
				m_orders.add(MainApplicationManager.getStundenplan().getVeranstaltungen().get(i));
			}
			return m_orders;
		}
		else
		return MainApplicationManager.getStundenplan().getVeranstaltungen();
			
		
	}
}
