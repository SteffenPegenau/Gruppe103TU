package de.tu_darmstadt.gdi1.gorillas.mapobjectsowners;

import de.matthiasmann.twl.Container;
import de.matthiasmann.twl.HAlignment;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.TextArea;
import de.matthiasmann.twl.renderer.AnimationState;
import de.matthiasmann.twl.renderer.Font;
import de.matthiasmann.twl.renderer.FontCache;
import de.matthiasmann.twl.textarea.HTMLTextAreaModel;



/**
 * Class Player
 */
public class Player {

		//
		// Fields
		//

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
		
		//
		// Constructors
		//
		public Player () { };
		
		//
		// Methods
		//


		//
		// Accessor methods
		//

		/**
		 * Set the value of username
		 * @param newVar the new value of username
		 */
		private void setUsername (String newVar) {
				username = newVar;
		}
		

		/**
		 * Get the value of username
		 * @return the value of username
		 */
		private String getUsername () {
				return username;
		}

		/**
		 * Set the value of fullname
		 * @param newVar the new value of fullname
		 */
		private void setFullname (String newVar) {
				fullname = newVar;
		}

		/**
		 * Get the value of fullname
		 * @return the value of fullname
		 */
		private String getFullname () {
				return fullname;
		}

		/**
		 * Set the value of password
		 * @param newVar the new value of password
		 */
		private void setPassword (String newVar) {
				password = newVar;
		}

		/**
		 * Get the value of password
		 * @return the value of password
		 */
		private String getPassword () {
				return password;
		}

		/**
		 * Set the value of highscore
		 * @param newVar the new value of highscore
		 */
		private void setHighscore (int newVar) {
				highscore = newVar;
		}

		/**
		 * Get the value of highscore
		 * @return the value of highscore
		 */
		private int getHighscore () {
				return highscore;
		}

		/**
		 * Set the value of money
		 * @param newVar the new value of money
		 */
		private void setMoney (int newVar) {
				money = newVar;
		}

		/**
		 * Get the value of money
		 * @return the value of money
		 */
		private int getMoney () {
				return money;
		}

		/**
		 * Set the value of roundsPlayed
		 * @param newVar the new value of roundsPlayed
		 */
		private void setRoundsPlayed (int newVar) {
				roundsPlayed = newVar;
		}

		/**
		 * Get the value of roundsPlayed
		 * @return the value of roundsPlayed
		 */
		private int getRoundsPlayed () {
				return roundsPlayed;
		}

		/**
		 * Set the value of wonRounds
		 * @param newVar the new value of wonRounds
		 */
		private void setWonRounds (int newVar) {
				wonRounds = newVar;
		}

		/**
		 * Get the value of wonRounds
		 * @return the value of wonRounds
		 */
		private int getWonRounds () {
				return wonRounds;
		}

		/**
		 * Set the value of percentageWon
		 * @param newVar the new value of percentageWon
		 */
		private void setPercentageWon (double newVar) {
				percentageWon = newVar;
		}

		/**
		 * Get the value of percentageWon
		 * @return the value of percentageWon
		 */
		private double getPercentageWon () {
				return percentageWon;
		}

		/**
		 * Set the value of numberOfThrownObjects
		 * @param newVar the new value of numberOfThrownObjects
		 */
		private void setNumberOfThrownObjects (int newVar) {
				numberOfThrownObjects = newVar;
		}

		/**
		 * Get the value of numberOfThrownObjects
		 * @return the value of numberOfThrownObjects
		 */
		private int getNumberOfThrownObjects () {
				return numberOfThrownObjects;
		}

		/**
		 * Set the value of numberOfHits
		 * @param newVar the new value of numberOfHits
		 */
		private void setNumberOfHits (int newVar) {
				numberOfHits = newVar;
		}

		/**
		 * Get the value of numberOfHits
		 * @return the value of numberOfHits
		 */
		private int getNumberOfHits () {
				return numberOfHits;
		}

		/**
		 * Set the value of accuracy
		 * @param newVar the new value of accuracy
		 */
		private void setAccuracy (double newVar) {
				accuracy = newVar;
		}

		/**
		 * Get the value of accuracy
		 * @return the value of accuracy
		 */
		private double getAccuracy () {
				return accuracy;
		}

		//
		// Other methods
		//
		
		public static Container getPlayerSelectContainer(Player player, int x, int y) {
			Container container = new Container();
			
			String html = "<b><button>BUTTON</button>Hello World! ssssssssssssssssssssss</b>";
			HTMLTextAreaModel content = new HTMLTextAreaModel(html);
			content.domModified();
			if(player == null) {
				// Kein Spieler selektiert
				// => zeige entsprechende Meldung und Button, um Spieler auszuwaehlen
				TextArea structure = new TextArea(content);
				structure.setPosition(x, y);
				structure.adjustSize();
				structure.registerImage("name", null);
				container.add(structure.getGUI());
			} else {
				// Spieler selektiert
				// => zeige Informationen zum Spieler und Button, um anderen Spieler auszuwaehlen
			}
			return container;
		}

}
