package de.tu_darmstadt.gdi1.gorillas.mapobjectsowners;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.sun.xml.internal.txw2.output.StreamSerializer;

import de.matthiasmann.twl.model.SimpleChangableListModel;
import de.tu_darmstadt.gdi1.gorillas.main.Serializer;

/**
 * Class PlayerList
 */
public class PlayerList extends Player implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6554140052844216005L;

	public static final String ERROR_MSG_EQUAL_USERNAMES = "USERNAMES MUST NOT BE EQUAL!";

	//
	// Fields
	//

	public HashMap<String, Player> players;
	public List<Player> playersAL;

	public PlayerList() {
		players = new HashMap<String, Player>();
	}

	public void AddPlayer(Player p) {
		if (!players.containsKey(p.getUsername())) {
			players.put(p.getUsername(), p);
		}
	}

	public static PlayerList restorePlayerList() {
		PlayerList list = (PlayerList) Serializer.restore(new PlayerList());
		if (list == null) {
			return new PlayerList();
		} else {
			return list;
		}
	}
	

	/**
	 * Gibt die Playliste als HashMap<String, Player> zurück
	 * 
	 * @return HashMap
	 */
	public HashMap<String, Player> getPlayersAsHashMap() {
		return players;
	}
	
	
	/**
	 * Gibt den Spieler mit dem Username zurück
	 * 
	 * @param username Der Username des gesuchten Spielers
	 * @return Spieler-Objekt
	 */
	public Player getPlayer(String username) {
		return players.get(username);
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

	public static boolean equalUsernames(Player[] players) {
		Player p1 = players[0];
		Player p2 = players[1];
		
		String usernameP1 = p1.getUsername();
		String usernameP2 = p2.getUsername();
		if (usernameP1.contentEquals(usernameP2)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean usernamesOkay(Player[] players) {
			if(players[0].isUsernameEmpty()) {
				return false;
			} else if (players[1].isUsernameEmpty()) {
				return false;
			} else if (equalUsernames(players)) {
				return false;
			}
			return true;
		}

	public int size() {
		return players.size();
	}
	
//Priorität beacten, erst Gewonnene Runden, dann Wurfgenauigkeit!
	
	public void hashMapToList(HashMap<String, Player> playersList) {
		playersAL = new ArrayList<Player>(playersList.values());
		
	}
	public void playedRoundsFilter() {
		playersAL.stream().map(p -> p.getRoundsPlayed()).sorted();
	}
	public void wonRoundsFilter() {
		playersAL.stream().map(p -> p.getWonRounds()).sorted();
	}
	public void percentageWonRoundsFilter() {
		playersAL.stream().map(p -> p.getPercentageWon()).sorted();
	}
	public void throwAccuracyFilter() {
		playersAL.stream().map(p -> p.getAccuracy()).sorted();
	}

}
