/**
 * 
 */
package de.tu_darmstadt.gdi1.gorillas.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.WriteAbortedException;

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
			String classname = o.getClass().getSimpleName();
			save(o, classname);
	}
	
	
	/**
	 * Speichert das Objekt unter save/filename.ser
	 * 
	 * @param Object o, muss die Schnittstelle 'java.io.Serializable' implementieren
	 * @param String filename, der gewuenschte Dateiname
	 */
	public static void save(Object o, String filename) {
		try
	      {
			filename = "save" + File.separator + filename + ".hsc";
			createSaveDirIfNotExisting();
			prepareSaveFile(filename);
	        FileOutputStream fileOut = new FileOutputStream(filename);
	        ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(o.getClass().cast(o));
	        out.close();
	        fileOut.close();
	        //System.out.println("Serialized object is saved in " + filename);
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
	 * Siehe auch Doku zu restore Funktion
	 * 
	 * @param Object o, muss die Schnittstelle 'java.io.Serializable' implementieren
	 */
	public static Object restore(Object o) {
		String classname = o.getClass().getSimpleName();
		return restore(o, classname);
	}
	
	/**
	 * Stellt ein gespeichertes Objekt aus der Datei
	 * save/FILENAME.ser
	 * wieder her und gibt es zurueck
	 * 
	 * Beispiel: PlayerList list = Serializer.restore(new PlayerList(), "Dateiname");
	 * 
	 * @param Object o, muss die Schnittstelle 'java.io.Serializable' implementieren
	 */
	public static Object restore(Object o, String filename) {
		Object restored = null;
		Class<?> clazz = o.getClass();
	      try
	      {
	         FileInputStream fileIn = new FileInputStream("save" + File.separator + filename + ".ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         restored = clazz.cast(in.readObject());
	         in.close();
	         fileIn.close();
	      } catch (WriteAbortedException e) {
	    	  return null;
	      }catch(FileNotFoundException e) {
	    	  return null;
	      } catch(IOException i)
	      {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("class " + clazz.getSimpleName() + " not found");
	         c.printStackTrace();
	         return null;
	      }
	      return clazz.cast(restored);
	}

}
