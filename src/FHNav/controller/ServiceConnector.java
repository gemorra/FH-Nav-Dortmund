package FHNav.controller;

import java.util.ArrayList;

public class ServiceConnector {

	
	
	public static ArrayList<String> getAllBranches()
	{
//		TimeTableBeanService ttbs = new TimeTableBeanService();
//		return null;
		WSClient wsc = new WSClient();
		
		return wsc.getParsedBranches();
	}
	
	
}
