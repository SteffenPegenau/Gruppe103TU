package de.tu_darmstadt.gdi1.gorillas.mapobjectsowners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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

	public static final String DEFAULT_FILE = "/assets/gorillas.playerscores/HighscoreList.hsc";
	
	protected static final int HIGHSCORE_SIZE = 10;
	//
	// Fields
	//

	public HashMap<String, Player> players;
	public ArrayList<Player> highscoreList;

	public PlayerList() {
		players = new HashMap<String, Player>();
	}
	
	/**
	 * Ergänzt/Ändert Spieler in der Liste und speichert
	 * 
	 * Statisch => kann ohne jede Initialisierung genutzt werden
	 * 
	 * Beispiel:
	 * PlayerList.AddPlayer(player);
	 * 
	 * @param player Zu speichernder/ändernder Spieler
	 */
	public static void savePlayer(Player player) {
			PlayerList list = (PlayerList) Serializer.restore(new PlayerList());
			list.players.put(player.getUsername(), player);
			list.savePlayerList();
	}

	/**
	 * Lädt die gespeicherte Liste aus Datei und gibt sie zurück
	 * 
	 * Beispiel: PlayerList list = PlayerList.restorePlayerList();
	 * 
	 * @return PlayerList als Objekt
	 */
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
	 * @param username
	 *            Der Username des gesuchten Spielers
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
	
	
	public void addPlayer(Player player) {
		String username = player.getUsername();
		players.put(username, player);
	}
	

	public static void savePlayerList(SimpleChangableListModel<Player> model) {
		PlayerList list = new PlayerList();
		for (int i = 0; i < model.getNumEntries(); i++) {
			list.addPlayer(model.getEntry(i));
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
		if (players[0].isUsernameEmpty()) {
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
	
	public ArrayList<Player> highscoreList (HashMap<String, Player> hmpl) {
		highscoreList = new ArrayList<Player>(hmpl.values());
		highscoreList.stream().map(p -> p.getWonRounds()).sorted().limit(10);
		return highscoreList;
	}
	
	/**
	 * Erzeugt die für den Highscore sortiere PlayerList als Array der Länghe 10
	 * @return Array für Highscore
	 */
	public static Player[] getHighscore() {
		// Initialisiert den Highscore leer, 
		//falls es weniger als 10 gespeicherte Spieler gibt
		Player[] highscore = new Player[HIGHSCORE_SIZE];
		
		for (int i = 0; i < highscore.length; i++) {
			highscore[i] = new Player("leer");
		}
		
		// Alle Spieler holen und sortieren
		PlayerList list = restorePlayerList();
	    List<Player> allPlayers = new ArrayList<Player>(list.getPlayersAsHashMap().values());
	    Collections.sort(allPlayers, highscoreComparator());
	    
	    // die ersten 10 Spieler der sortierten Liste zu Array hinzufügen
	    int counter = 0;
	    for (Player p : allPlayers) {
	    	System.out.println(p);
			if(counter < highscore.length) {
				highscore[counter] = p;
				counter++;
			} else {
				// Highscore voll => for-Schleife abbrechen
				//break;
			}
		}
		
		return highscore;
	}
	
	protected static Comparator<Player> highscoreComparator() {
		return new Comparator<Player>() {
			
	        // negative int: first is less than second
			// 0: equal
			// positive int: first is greater than second
			public int compare(Player p1, Player p2) {
				if(p1.getPercentageWon() > p2.getPercentageWon()) {
					return 1;
				} else if(p1.getPercentageWon() < p2.getPercentageWon()) {
					return -1;
				} else {
					if(p1.getThrowsForAHit() < p2.getThrowsForAHit()) {
						return 1;
					} else if(p1.getThrowsForAHit() > p2.getThrowsForAHit()) {
						return -1;
					} else {
						return 0;
					}
				}
	        }
	    };
	}
	
}

	
