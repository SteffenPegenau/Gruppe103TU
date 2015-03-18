package de.tu_darmstadt.gdi1.gorillas.mapobjectsowners;

/**
 * Class Player
 */
public class Player extends Owner implements java.io.Serializable {
	/**
	 * 
	 */
	public static final String ERROR_MSG_EMPTY_USERNAME = "USERNAME MUST NOT BE EMPTY!";

	private static final long serialVersionUID = -6462859935769741215L;
	private String username = "";
	private int roundsPlayed = 0;
	private int roundsWon = 0;
	private double percentageWon = 0;
	private int numberOfThrows = 0;
	private int numberOfHits = 0;
	private double accuracy = 0;
	public boolean isInitialised = false;
	
	protected int lifesLeft = 1;

	public String enteredAngle = "0";
	public String enteredVelocity = "0";

	private int arrayIndex;

	 public Player(String username) {
	 this.username = username;
	 isInitialised = true;
	 };
	
	 public Player() {
	
	 }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getRoundsPlayed() {
		return roundsPlayed;
	}

	public void setRoundsPlayed(int roundsPlayed) {
		this.roundsPlayed = roundsPlayed;
	}

	public int getWonRounds() {
		return roundsWon;
	}

	public void setWonRounds(int wonRounds) {
		this.roundsWon += wonRounds;
	}

	public double getPercentageWon() {
		return percentageWon;
	}

	public void setPercentageWon(int wonRounds, int playedRounds) {
		this.percentageWon = (wonRounds / playedRounds);
	}

	public int getNumberOfThrownObjects() {
		return numberOfThrows;
	}

	public void setNumberOfThrownObjects(int numberOfThrownObjects) {
		this.numberOfThrows = numberOfThrownObjects;
	}

	public int getNumberOfHits() {
		return numberOfHits;
	}

	public void setNumberOfHits(int numberOfHits) {
		this.numberOfHits = numberOfHits;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public boolean isInitialised() {
		return isInitialised;
	}

	public void setInitialised(boolean isInitialised) {
		this.isInitialised = isInitialised;
	}

	public String toString() {
		return this.username;
	}

	public boolean isUsernameEmpty() {
		if (username.isEmpty() || username.contentEquals("")) {
			return true;
		} else {
			return false;
		}
	}

	public String checkUsername() {
		if (username.isEmpty() || username.contentEquals("")) {
			return ERROR_MSG_EMPTY_USERNAME;
		} else {
			return "";
		}
	}

	public int getArrayIndex() {
		return arrayIndex;
	}

	public void setArrayIndex(int arrayIndex) {
		this.arrayIndex = arrayIndex;
	}

	/**
	 * Wird aufgerufen, wenn Spieler die Figur des anderen Spielers getroffen
	 * hat
	 */
	public void hitEnemyFigure() {
		System.out.println("Spieler " + getUsername() + " <" + getArrayIndex()
				+ "> hat gegnerische Figur getroffen!");
	}

	/**
	 * Wird aufgerufen, wenn die Figur des Spielers getroffen wird
	 */
	public void figureWasHit() {
		reduceLifesLeft();
		System.out.println("Figur von Spieler " + getUsername() + " <"
				+ getArrayIndex() + "> wurde getroffen!" + "Leben übrig: "
				+ getLifesLeft());
	}

	protected void reduceLifesLeft() {
		lifesLeft--;
	}

	public void setLifesLeft(int lifesLeft) {
		this.lifesLeft = lifesLeft;
	}

	public int getLifesLeft() {
		return lifesLeft;
	}

	/**
	 * Gibt den Array Index des jeweils anderen Spielers zurück
	 * 
	 * Beispiel: aus 0 wird 1 und umgekehrt
	 * 
	 * @param p
	 *            Spieler, dessen Gegenspieler gesucht wird
	 * @return Array Index des anderen Spielers
	 */
	public static int getOtherPlayersArrayIndex(Player p) {
		return (p.getArrayIndex() == 0) ? 1 : 0;
	}

	//
	// Other methods
	//

}
