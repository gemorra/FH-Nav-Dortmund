package FHNav.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * Diese Klasse kümmert sich um die Verwaltung der Settings, die lokal auf dem
 * Smartphone gespeichert werden
 * 
 * @author Moritz Wiechers
 * 
 */
public class SettingsManager {
	static SharedPreferences preferences;

	private static String pathToFile = "http://www.gemorra.de/fhnav/connect.php";
	private static boolean wizardDone = false;
	private static int text_size = 1;
	private static boolean lecture_details_groupletter = true;
	private static boolean lecture_details_lecturer = true;
	private static boolean lecture_details_type = true;

	public static void loadSettings(Context ctx) {
		preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
		loadPathToFile();
		loadWizardDone();
		loadLecture_details_groupletter();
		loadLecture_details_lecturer();
		loadLecture_details_type();
		loadText_size();
	}

	public static void setPathToFile(String pathToFile) {
		SettingsManager.pathToFile = pathToFile;
		Editor editor = preferences.edit();
		editor.putString("pathToFile", pathToFile);
		editor.commit();
	}

	public static String getPathToFile() {
		return pathToFile;
	}

	public static void loadPathToFile() {
		pathToFile = preferences.getString("pathToFile", "http://www.gemorra.de/fhnav/connect.php");
	}

	public static boolean isWizardDone() {
		return wizardDone;
	}

	public static void setWizardDone(boolean wizardDone) {
		SettingsManager.wizardDone = wizardDone;
		Editor editor = preferences.edit();
		editor.putBoolean("wizardDone", wizardDone);
		editor.commit();
	}

	public static void loadWizardDone() {
		wizardDone = preferences.getBoolean("wizardDone", false);
	}

	public static int getText_size() {
		return text_size;
	}

	public static void setText_size(int text_size) {
		SettingsManager.text_size = text_size;
		Editor editor = preferences.edit();
		editor.putInt("text_size", text_size);
		editor.commit();
	}

	public static void loadText_size() {
		text_size = preferences.getInt("text_size", 1);
	}

	public static boolean isLecture_details_groupletter() {
		return lecture_details_groupletter;
	}

	public static void loadLecture_details_groupletter() {
		lecture_details_groupletter = preferences.getBoolean("lecture_details_groupletter", true);
	}

	public static void setLecture_details_groupletter(boolean lecture_details_groupletter) {
		SettingsManager.lecture_details_groupletter = lecture_details_groupletter;
		Editor editor = preferences.edit();
		editor.putBoolean("lecture_details_groupletter", lecture_details_groupletter);
		editor.commit();
	}

	public static boolean isLecture_details_lecturer() {
		return lecture_details_lecturer;
	}

	public static void setLecture_details_lecturer(boolean lecture_details_lecturer) {
		SettingsManager.lecture_details_lecturer = lecture_details_lecturer;
		Editor editor = preferences.edit();
		editor.putBoolean("lecture_details_lecturer", lecture_details_lecturer);
		editor.commit();
	}

	public static void loadLecture_details_lecturer() {
		lecture_details_lecturer = preferences.getBoolean("lecture_details_lecturer", true);
	}

	public static boolean isLecture_details_type() {
		return lecture_details_type;
	}

	public static void setLecture_details_type(boolean lecture_details_type) {
		SettingsManager.lecture_details_type = lecture_details_type;
		Editor editor = preferences.edit();
		editor.putBoolean("lecture_details_type", lecture_details_type);
		editor.commit();
	}

	public static void loadLecture_details_type() {
		lecture_details_type = preferences.getBoolean("lecture_details_type", true);
	}

	public static void setPreferences(SharedPreferences preferences) {
		SettingsManager.preferences = preferences;
	}

}
