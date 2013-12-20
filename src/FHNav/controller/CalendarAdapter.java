package FHNav.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import FHNav.model.Veranstaltung;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class CalendarAdapter {

	String calendarNames[];
	int calendarId[];
	int selectedId = 0;
	Context ctx;

	public CalendarAdapter(Context ctx) {
		this.ctx = ctx;
		Cursor cursor;
		if (android.os.Build.VERSION.SDK_INT > 14) {
			cursor = this.ctx.getContentResolver().query(
					Uri.parse("content://com.android.calendar/calendars"),
					new String[] { "_id", "calendar_displayName" }, null, null,
					null);
		} else {
			cursor = this.ctx.getContentResolver().query(
					Uri.parse("content://com.android.calendar/calendars"),
					new String[] { "_id", "displayName" }, null, null, null);
		}

		cursor.moveToFirst();
		// Get calendars name
		calendarNames = new String[cursor.getCount()];
		// Get calendars id
		calendarId = new int[cursor.getCount()];
		for (int i = 0; i < calendarNames.length; i++) {
			calendarId[i] = cursor.getInt(0);
			calendarNames[i] = cursor.getString(1);
			cursor.moveToNext();
		}
	}

	/**
	 * Fügt ein wiederkehrendes Ereignis in den Android Kalender ein
	 * 
	 * @param ver
	 *            Die Veranstaltung, die eingefügt werden soll
	 * @param start
	 *            Startdatum für die Veranstaltungsfolge
	 * @param end
	 *            Enddatum für die Veranstaltungsfolge
	 */
	public void addRecEventToCalendar(Veranstaltung ver, Date start, Date end) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		ContentValues contentEvent = new ContentValues();
		contentEvent.put("calendar_id", calendarId[selectedId]);
		contentEvent.put("title", ver.getName());
		contentEvent.put("description", "bei " + ver.getDozent());
		contentEvent.put("eventLocation", ver.getRaum());
		contentEvent.put("eventTimezone", "Europe/Berlin");

		Calendar c1 = Calendar.getInstance();
		c1.set(start.getYear() + 1900, start.getMonth(), start.getDate(), ver
				.getStartTime().getHours(), ver.getStartTime().getMinutes());
		c1.setFirstDayOfWeek(Calendar.MONDAY);

		while (c1.getTime().getDay() != ver.getWochentag()) {
			c1.add(Calendar.DATE, 1);
		}

		contentEvent.put("dtstart", c1.getTime().getTime());
		// Duration in Sekunden
		long duration = (ver.getEndTime().getHours() - ver.getStartTime()
				.getHours()) * 3600;
		duration += (ver.getEndTime().getMinutes() - ver.getStartTime()
				.getMinutes()) * 60;
		contentEvent.put("duration", "P" + duration + "S");
		String rrule = "FREQ=WEEKLY;BYDAY="
				+ Tools.getShortWeekday(c1.getTime().getDay()) + ";UNTIL="
				+ sdf.format(end) + "T130000Z";
		contentEvent.put("rrule", rrule);

		contentEvent.put("allDay", 0);
		contentEvent.put("hasAlarm", 0);

		Uri eventsUri = Uri.parse("content://com.android.calendar/events");
		this.ctx.getContentResolver().insert(eventsUri, contentEvent);
	}

	public int[] getCalendarId() {
		return calendarId;
	}

	public String[] getCalendarNames() {
		return calendarNames;
	}

	public int getSelectedId() {
		return selectedId;
	}

	public void setCalendarId(int[] calendarId) {
		this.calendarId = calendarId;
	}

	public void setCalendarNames(String[] calendarNames) {
		this.calendarNames = calendarNames;
	}

	public void setSelectedid(int selectedId) {
		this.selectedId = selectedId;
	}
	// public void getEvent(Context ctx)
	// {
	// Uri.Builder builder =
	// Uri.parse("content://com.android.calendar/instances/when").buildUpon();
	// long now = new Date().getTime();
	// ContentUris.appendId(builder, now - DateUtils.WEEK_IN_MILLIS);
	// ContentUris.appendId(builder, now + DateUtils.WEEK_IN_MILLIS);
	//
	// Cursor eventCursor = ctx.getContentResolver().query(builder.build(),
	// new String[] { "title", "begin", "end", "allDay",
	// "rrule","duration","timezone"}, "Calendars._id=" +
	// calendarId[selectedId],
	// null, "startDay ASC, startMinute ASC" );
	//
	// while (eventCursor.moveToNext()) {
	// final String title = eventCursor.getString(0);
	// final Date begin = new Date(eventCursor.getLong(1));
	// final Date end = new Date(eventCursor.getLong(2));
	// final Boolean allDay = !eventCursor.getString(3).equals("0");
	// String rrule = eventCursor.getString(4);
	// String rdate = eventCursor.getString(5);
	// String tzone = eventCursor.getString(6);
	//
	// Log.e("asd: ","Title: " + title + " Zone: " + tzone );
	// }
	//
	// }
	/*
	 * erste Tests mit Kalender public void addEventToCalendar() {
	 * 
	 * ContentValues contentEvent = new ContentValues(); // Particular Calendar
	 * in which we need to add Event contentEvent.put("calendar_id",
	 * calendarId[selectedId]); // Title/Caption of the Event
	 * contentEvent.put("title", "Wedding"); // Description of the Event
	 * contentEvent.put("description", "Wedding Party"); // Venue/Location of
	 * the Event contentEvent.put("eventLocation", "New York"); // Start Date of
	 * the Event with Time
	 * 
	 * Date startDate = new Date(); Date startDate2 = new
	 * Date(startDate.getTime()+1000*3600*24); contentEvent.put("dtstart",
	 * startDate2.getTime()); // End Date of the Event with Time
	 * 
	 * 
	 * contentEvent.put("duration", "P3600S");
	 * 
	 * Log.e("Tag", startDate2.toGMTString()); String rrule =
	 * "FREQ=WEEKLY;BYDAY=FR;UNTIL=20111009T130000Z"; // All Day Event
	 * contentEvent.put("allDay", 0); // Set alarm for this Event
	 * contentEvent.put("hasAlarm",0); contentEvent.put("rrule",rrule);
	 * 
	 * Uri eventsUri = Uri.parse("content://com.android.calendar/events"); //
	 * event is added successfully
	 * this.ctx.getContentResolver().insert(eventsUri, contentEvent);
	 * 
	 * }
	 * 
	 * public void getEvent() { Uri.Builder builder =
	 * Uri.parse("content://com.android.calendar/instances/when").buildUpon();
	 * long now = new Date().getTime(); ContentUris.appendId(builder, now -
	 * DateUtils.WEEK_IN_MILLIS); ContentUris.appendId(builder, now +
	 * DateUtils.WEEK_IN_MILLIS);
	 * 
	 * Cursor eventCursor =
	 * MainApplicationManager.getCtx().getContentResolver().
	 * query(builder.build(), new String[] { "title", "begin", "end", "allDay",
	 * "rrule","duration"}, "Calendars._id=" + calendarId[selectedId], null,
	 * "startDay ASC, startMinute ASC" );
	 * 
	 * while (eventCursor.moveToNext()) { final String title =
	 * eventCursor.getString(0); final Date begin = new
	 * Date(eventCursor.getLong(1)); final Date end = new
	 * Date(eventCursor.getLong(2)); final Boolean allDay =
	 * !eventCursor.getString(3).equals("0"); String rrule =
	 * eventCursor.getString(4); String rdate = eventCursor.getString(5);
	 * 
	 * Log.e("asd: ","Title: " + title + " RRule:" + rrule + " Dur:" + rdate); }
	 * 
	 * }
	 */

}
