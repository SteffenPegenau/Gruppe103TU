package de.tu_darmstadt.gdi1.gorillas.mapobjectsowners;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
	//
	// Fields
	//

	public HashMap<String, Player> players;
	public List<Player> playersAL;
	public ArrayList<String> hsc = new ArrayList<String>();

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

	// Sortiermethoden
	// TODO Priorität beachten, erst Gewonnene Runden, dann Wurfgenauigkeit!
	// Namen beim sortieren berücksichtigen

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

	// Speichern der Spielerinformationen

	private void readDataFromPlayer(Player p) throws IOException {
		StringBuilder sb = new StringBuilder(100000);
		String fullname = p.getFullname();
		String username = p.getUsername();
		int playedrounds = p.getRoundsPlayed();
		double percentagewins = p.getPercentageWon();
		double accuracy = p.getAccuracy();

		String line = null;
		String[] s = null;

		try {
			FileInputStream fis = new FileInputStream(new File(DEFAULT_FILE));
			BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
			while ((line = bf.readLine()) != null) {
				s = line.split(", ");
				fullname = s[0];
				username = s[1];
				playedrounds = Integer.parseInt(s[1]);
				percentagewins = Double.parseDouble(s[3]);
				accuracy = Double.parseDouble(s[4]);

				hsc.add(line);
			}
			bf.close();
			fis.close();
		} catch (IOException ioex) {
			System.out
					.println(ioex.getMessage() + "Error, could not read file");
			ioex.printStackTrace();
		}
	}
	
	private void writeDataFromPlayer(ArrayList<String> entry) {
		
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(DEFAULT_FILE));
			
			for (int i = 0; i < entry.size(); i++) {
				bw.write(entry.get(i));
				bw.newLine();	
			}
			bw.close();
		} catch (IOException ioex) {
			System.out.println(ioex.getMessage() + "Fehler " + ioex.toString());
			
		}
	}
	
}
