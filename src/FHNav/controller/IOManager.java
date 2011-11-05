package FHNav.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import FHNav.model.Stundenplan;

public class IOManager {

	private static String stundenplanDatei = "plan.ser";
	private static String save_dir = "private";
	
	public static String getStundenplanDatei() {
		return stundenplanDatei;
	}

	public static void setStundenplanDatei(String stundenplanDatei) {
		IOManager.stundenplanDatei = stundenplanDatei;
	}

	public static void saveStundenplan(Stundenplan stundenplan)
	{
		ObjectOutputStream objOut;
		File file = new File(getLocalFileDir() + File.separator + stundenplanDatei);
		try {
			objOut = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			objOut.writeObject(stundenplan);
			objOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}
	
	public static Stundenplan loadStundenplan()
	{
		Stundenplan stundenplan = null;
		ObjectInputStream objIn;
		File file = new File(getLocalFileDir() + File.separator + stundenplanDatei);
		try {
			objIn = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
			stundenplan = (Stundenplan) objIn.readObject();
			objIn.close();
		} catch (Exception e) {
			stundenplan = new Stundenplan();
		} 
		
		return stundenplan;
	
	}
	
	public static File getLocalFileDir()
	{
		return MainApplicationManager.getCtx().getDir(save_dir, 0);
		
		
	}
	
	
}
