package FHNav.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import FHNav.model.Stundenplan;
import android.content.Context;

public class IOManager {

	private static String stundenplanDatei = "plan.ser";
	private static String save_dir = "private";

	/**
	 * gibt den lokalen Pfad zurück
	 * 
	 * @param ctx
	 * @return
	 */
	public static File getLocalFileDir(Context ctx) {

		return ctx.getDir(save_dir, 0);

	}

	public static String getStundenplanDatei() {
		return stundenplanDatei;
	}

	/**
	 * Lädt den Stundenplan.
	 * 
	 * @param ctx
	 *            immer der ApplicationContext
	 * @return der geladene Stundenplan
	 */
	public static Stundenplan loadStundenplan(Context ctx) {
		Stundenplan stundenplan = null;
		ObjectInputStream objIn;
		File file = new File(getLocalFileDir(ctx) + File.separator
				+ stundenplanDatei);
		try {
			objIn = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream(file)));
			stundenplan = (Stundenplan) objIn.readObject();
			objIn.close();
			System.out.println("Load Success");
			System.out.println("Path: " + getLocalFileDir(ctx));
		} catch (Exception e) {
			stundenplan = new Stundenplan();
			System.out.println("Load Error");
		}

		return stundenplan;

	}

	/**
	 * Speichert den Stundenplan unter gegebenem Context ab
	 * 
	 * @param stundenplan
	 * @param ctx
	 *            immer der ApplicationContext
	 */
	public static void saveStundenplan(Stundenplan stundenplan, Context ctx) {
		ObjectOutputStream objOut;
		File file = new File(getLocalFileDir(ctx) + File.separator
				+ stundenplanDatei);
		try {
			objOut = new ObjectOutputStream(new BufferedOutputStream(
					new FileOutputStream(file)));
			objOut.writeObject(stundenplan);
			objOut.close();
			System.out.println("Save Success");
			System.out.println("Path: " + getLocalFileDir(ctx));
		} catch (Exception e) {
			System.out.println("Save Error");
		}
	}

	public static void setStundenplanDatei(String stundenplanDatei) {
		IOManager.stundenplanDatei = stundenplanDatei;
	}

}
