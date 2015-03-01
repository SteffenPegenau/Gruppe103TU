/**
 * 
 */
package de.tu_darmstadt.gdi1.gorillas.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Steffen Pegenau (steffen.pegenau@gmail.com)
 *
 * Die statischen Methoden dieser Klasse koennen genutzt werden,
 * um einfach Objekte zu speichern und wieder herzustellen.
 * 
 * Die Objekte muessen dazu das Interface 'java.io.Serializable' implementieren!
 */
public class Serializer {
	/**
	 * Erzeugt das Speicherverzeichnis '/save', wenn es noch nicht existiert
	 */
	private static void createSaveDirIfNotExisting() {
		File dir = new File("save");
		if (!dir.exists()) {
			// directory does not exist => create!
			dir.mkdir();
		}
	}
	
	/**
	 * Creates the file if it does not exist yet
	 * @param filename 
	 */
	private static void prepareSaveFile(String filename) {
		File f = new File(filename);
		if(!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Speichert das Objekt unter save/<<Klassenname>>.ser
	 * 
	 * @param Object o, muss die Schnittstelle 'java.io.Serializable' implementieren
	 */
	public static void save(Object o) {
		try
	      {
			String classname = o.getClass().getSimpleName();
			String filename = "save" + File.separator + classname + ".ser";
			createSaveDirIfNotExisting();
			prepareSaveFile(filename);
	        FileOutputStream fileOut = new FileOutputStream(filename);
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(o);
	        out.close();
	        fileOut.close();
	        System.out.println("Serialized object is saved in /save/" + classname + ".ser");
	      }catch(NotSerializableException e) {
	    	  System.out.println("Object to be saved does not implement java.io.Serializable!");
	    	  e.printStackTrace();
	      }
		catch(IOException i)
	      {
	          i.printStackTrace();
	      } 
	}
	
	/**
	 * Leitet aus dem Objekt den Klassennamen und den Pfad zur Datei
	 * save/<<Klassenname>>.ser
	 * ab, stellt aus der Datei das Objekt wieder her und gibt es zurueck
	 * 
	 * @param Object o, muss die Schnittstelle 'java.io.Serializable' implementieren
	 */
	public static Object restore(Object o) {
		Object restored = null;
		String classname = o.getClass().getSimpleName();
		Class<?> clazz = o.getClass();
	      try
	      {
	         FileInputStream fileIn = new FileInputStream("save" + File.separator + classname + ".ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         restored = clazz.cast(in.readObject());
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("class " + classname + " not found");
	         c.printStackTrace();
	         return null;
	      }
	      return clazz.cast(restored);
	}

}
