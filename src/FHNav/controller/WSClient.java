package FHNav.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.fhdo.fhapp.timetable.TimeTableBean;
import de.fhdo.fhapp.timetable.TimeTableBeanService;


public class WSClient 
{
	// Service und Bean
	private TimeTableBeanService service;
	private TimeTableBean myTimeTableBean;
	
	
	public WSClient()
	{
		// Service und Bean zuweisen
		service = new TimeTableBeanService();
//		myTimeTableBean = service.getTimeTableBeanPort();
	}

	// wrapper method
	public String getCurrentBranches()
	{
		// Aufruf einer Bean-Methode
		return this.myTimeTableBean.getCurrentBranches();
	}

	// wrapper method
	public String getActivitiesForWeek(int branchId)
	{
		return this.myTimeTableBean.getActivitiesForWeek(branchId);
	}
	
	// alle Branches abrufen und in eine wunderbare ArrayList werfen
	public ArrayList<String> getParsedBranches()
	{	
		ArrayList<String> myResult = new ArrayList<String>();
		
		try
		{
			// den Json-String in ein Objekt werfen.
			JSONArray allBranches = new JSONArray(new JSONObject(this.myTimeTableBean.getCurrentBranches()).get("BRANCHES").toString());
			JSONObject thisObj = null;
			
			// alle Studiengänge durchwandern und jedem eine ID zuweisen (die Datenbank hat nämlich keine... :) )
			for(int i = 0; i < allBranches.length(); i++)
			{
				thisObj = new JSONObject(allBranches.get(i).toString());
				myResult.add(thisObj.getInt("ID"), thisObj.getString("NAME"));
			}
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return myResult;
	}
	

	// bestimme den String-Array mit den Spaltennamen
	public String[] getColumns(int branchId)
	{
		Vector<String> columns = new Vector<String>();
		try
		{
			JSONObject allActivities = new JSONObject(this.myTimeTableBean.getActivitiesForWeek(branchId));
			
			JSONArray myDayObj = null;
			JSONObject myActivities = null;
			String[] myFields = getNames(allActivities);
					
			HashMap<String, Integer> columnOffset = new HashMap<String, Integer>();
	
			ArrayList<Integer> myStartHours = new ArrayList<Integer>();
			
			int sumColumn = 0;
			
			// alle Felder des Json-Arrays durchwandern
			// und die "Start"-Stunden-Offsets in eine ArrayList speichern
			// haargenau der selbe code (c&p) wie unten.  
			for(String name : myFields)
			{
				myDayObj = allActivities.getJSONArray(name);
				for(int i = 0; i < myDayObj.length(); i++)
				{
					myActivities = myDayObj.getJSONObject(i);	
					myStartHours.add(myActivities.getInt("START"));
				}				
				columnOffset.put(name, sumColumn);
				
				sumColumn += count(myStartHours);
		
				for(int i = 0; i < count(myStartHours); i++)
				{
					columns.add(columnOffset.get(name), name);
				}

				myStartHours.clear();
			}
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getColumnArray(columns);
	}
	
	
	// alle gefundenen Spalten durchwandern, nach Wochentag ordnen,
	// erforderliche Spalten hinzufügen und einen Array zurückgeben
	private String[] getColumnArray(Vector<String> columns)
	{
		String[] colArray = new String[columns.size()];
		String[] compare = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};
		
		int actualIndex = 0;
		for(int c_index = 0; c_index < compare.length; c_index++)
		{	
			for(int i = 0; i < columns.size(); i++)
			{
				if(compare[c_index].equalsIgnoreCase(columns.get(i)))
				{
					colArray[actualIndex] = columns.get(i);
					actualIndex++;
				}
			}
		}	
		
		return colArray;	
	}
	
	// Tabellendaten erzeugen
	public String[][] getTableData(int branchId)
	{
		String[][] result = null;
		try
		{
			JSONObject allActivities = new JSONObject(this.myTimeTableBean.getActivitiesForWeek(branchId));
			
			JSONArray myDayObj = null;
			JSONObject myActivities = null;
			// alle verfügbaren Namen des Objekts abrufen
			String[] myFields = getNames(allActivities);
			
			String[][] tableData; 
			
			Vector<String> columns = new Vector<String>();
			HashMap<String, Integer> columnOffset = new HashMap<String, Integer>();
			HashMap<String, Integer> columnIndex = new HashMap<String, Integer>();
				
			ArrayList<Integer> myStartHours = new ArrayList<Integer>();
			int sumColumn = 0;
			
			for(String name : myFields)
			{
				// Nur die Aktivitäten für einen Tag auswählen
				myDayObj = allActivities.getJSONArray(name);
				for(int i = 0; i < myDayObj.length(); i++)
				{
					//nur eine Aktivität auswählen
					myActivities = myDayObj.getJSONObject(i);
					//nur die Start-Stunde auswählen
					myStartHours.add(myActivities.getInt("START"));
				}
				
				columnOffset.put(name, count(myStartHours));	
				sumColumn += count(myStartHours);
						
				for(int i = 0; i < count(myStartHours); i++)
				{
					columns.add(name);
				}
				
				myStartHours.clear();
			}
			
		
			columnIndex = convertIndex(columnOffset);
			
		
			
			tableData = new String[12][sumColumn];
			
			int colTracker = 0;
	
			for(String name : myFields)
			{	
				colTracker = columnIndex.get(name);
								
				myDayObj = allActivities.getJSONArray(name);
				for(int i = 0; i < myDayObj.length(); i++)
				{
					myActivities = myDayObj.getJSONObject(i);
					
					int helpColumn = colTracker;
					while(tableData[myActivities.getInt("START")][helpColumn] != null)
					{
						helpColumn++;
					}
					
					tableData[myActivities.getInt("START")][helpColumn] = myActivities.getString("NAME");
				}
			}

			result = tableData;
		
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	
	// Spalten-Offset in Spalten-Index verwandeln
	private HashMap<String, Integer> convertIndex(HashMap<String, Integer> columnOffset)
	{
		HashMap<String, Integer> columnIndex = new HashMap<String, Integer>();
		
		for(String key : columnOffset.keySet())
		{
			if(key.equalsIgnoreCase("MONDAY"))
			{
				columnIndex.put(key, 0);
			}
			if(key.equalsIgnoreCase("TUESDAY"))
			{
				columnIndex.put(key, columnOffset.get("MONDAY"));
			}
			if(key.equalsIgnoreCase("WEDNESDAY"))
			{
				columnIndex.put(key, columnOffset.get("MONDAY") + columnOffset.get("TUESDAY"));
			}
			if(key.equalsIgnoreCase("THURSDAY"))
			{
				columnIndex.put(key, columnOffset.get("MONDAY") + columnOffset.get("TUESDAY") + columnOffset.get("WEDNESDAY"));
			}
			if(key.equalsIgnoreCase("FRIDAY"))
			{
				columnIndex.put(key, columnOffset.get("MONDAY") + columnOffset.get("TUESDAY") + columnOffset.get("WEDNESDAY") + columnOffset.get("THURSDAY"));
			}
			
		}
		
		return columnIndex;
	}
	
	// Elemente der ArrayList durchzählen
	// Maximum zurückgeben
	private int count(ArrayList<Integer> list)
	{
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		int currMax = 0;
		int max = 0;
		
		for(int i=0; i < list.size(); i++)
		{
			if(!map.containsKey(list.get(i)))
			{
				map.put(list.get(i), 1);
			}
			else
			{
				map.put(list.get(i), map.get(list.get(i))+1);
			}
		}
	
		for(Integer k : map.keySet())
		{
			max = map.get(k);
			if(max > currMax)
			{
				currMax = max;
			}		
		}
		
		return currMax;	
	}
	
	private static String[] getNames(JSONObject json) {
	    int length = json.length();
	    if (length == 0) {
	      return null;
	    }
	    String[] names = new String[length];
	    Iterator<?> i = json.keys();
	    int j = 0;
	    while (i.hasNext()) {
	      names[j++] = (String) i.next();
	    }
	    return names;
	  }
			
}
