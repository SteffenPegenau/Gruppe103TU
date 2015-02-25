/**
 * 
 */
package de.tu_darmstadt.gdi1.gorillas.main;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * @author Steffen Pegenau (steffen.pegenau@gmail.com)
 *
 */
public class PlayerList implements java.io.Serializable {
	ArrayList<Player> players;

	public PlayerList() {
		players = new ArrayList<Player>();
	}
	
	public static PlayerList getPlayerListByFile() {
		return null;
	}
	
	public static boolean save() throws IOException {
		// Write to disk with FileOutputStream
		FileOutputStream f_out = new FileOutputStream("myobject.data");

		// Write object with ObjectOutputStream
		ObjectOutputStream obj_out = new ObjectOutputStream (f_out);

		// Write object out to disk
		System.out.println("OUT!");
		Player player = new Player("Steffen", "Steffen Pegenau");
		
		obj_out.writeObject (player);
		
		obj_out.close();
		return true;
	}
	
	
	
}
