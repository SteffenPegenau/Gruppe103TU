package de.tu_darmstadt.gdi1.gorillas.mapobjectsowners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.sun.javafx.collections.MappingChange.Map;

import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleChangableListModel;
import de.tu_darmstadt.gdi1.gorillas.main.Serializer;



/**
 * Class PlayerList
 */
public class PlayerList implements java.io.Serializable {

		//
		// Fields
		//

		private HashMap<String, Player> players;
		
		public PlayerList() {
			players = new HashMap<String, Player>();
		}
		
		public void AddPlayer(Player p) {
			if(!players.containsKey(p.getUsername())) {
				players.put(p.getUsername(), p);
			}
		}
		
		public static PlayerList restorePlayerList() {
			PlayerList list = (PlayerList) Serializer.restore(new PlayerList());
			if(list == null) {
				return new PlayerList();
			} else {
				return list;
			}
		}
		
		public ArrayList<Player> getPlayers() {
			ArrayList<Player> list = new ArrayList<Player>();
			for (Entry<String, Player> entry : players.entrySet()) {
				list.add((Player) entry.getValue());
			}
			return list;
		}
		
		
		public static void savePlayerList(SimpleChangableListModel<Player> model) {
			PlayerList list = new PlayerList();
			for (int i = 0; i < model.getNumEntries(); i++) {
				list.AddPlayer(model.getEntry(i));
			}
			Serializer.save(list);
		}
		
		public void savePlayerList() {
			Serializer.save(this);
			
		}
		
		public int size() {
			return players.size();
		}

}
