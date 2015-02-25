package de.tu_darmstadt.gdi1.gorillas.main;


/**
 * Class Player
 */
public class Player {
		private String username;
		private String fullname;
		private String password;
		private int highscore;
		private int money;
		private int roundsPlayed;
		private int wonRounds;
		private double percentageWon;
		private int numberOfThrownObjects;
		private int numberOfHits;
		private double accuracy;
		
		public Player (String username, String fullname) {
			this.username = username;
			this.fullname = fullname;
		};
}
