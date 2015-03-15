package de.tu_darmstadt.gdi1.gorillas.mapobjectsowners;


/**
 * Class Player
 */
public class Player extends Owner implements java.io.Serializable{
	/**
	 * 
	 */
	public static final String ERROR_MSG_EMPTY_USERNAME = "USERNAME MUST NOT BE EMPTY!";
	
	private static final long serialVersionUID = -6462859935769741215L;
	private String username = "";
	private String fullname = "";
	private String password;
	private int highscore;
	private int money;
	private int roundsPlayed;
	private int wonRounds;
	private double percentageWon;
	private int numberOfThrownObjects;
	private int numberOfHits;
	private double accuracy;
	public boolean isInitialised = false;
	private int xPos;
	private int yPos;
	private double angle;
	private double velocity;
	
	protected int lifesLeft = 1;
	
	public String enteredAngle = "0";
	public String enteredVelocity = "0";
	
	private int arrayIndex;

	//
	// Constructors
	//
	public Player(String username) {
		this.username = username;
		isInitialised = true;
	};
	
	public Player() {
		
	}
	/**
	 * @param username
	 * @param fullname
	 */
	public Player(String username, String fullname) {
		this.username = username;
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getHighscore() {
		return highscore;
	}

	public void setHighscore(int highscore) {
		this.highscore = highscore;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getRoundsPlayed() {
		return roundsPlayed;
	}

	public void setRoundsPlayed(int roundsPlayed) {
		this.roundsPlayed = roundsPlayed;
	}

	public int getWonRounds() {
		return wonRounds;
	}

	public void setWonRounds(int wonRounds) {
		this.wonRounds = wonRounds;
	}

	public double getPercentageWon() {
		return percentageWon;
	}

	public void setPercentageWon(double percentageWon) {
		this.percentageWon = percentageWon;
	}

	public int getNumberOfThrownObjects() {
		return numberOfThrownObjects;
	}

	public void setNumberOfThrownObjects(int numberOfThrownObjects) {
		this.numberOfThrownObjects = numberOfThrownObjects;
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
	
	public int getX() {
		return xPos;
	}
	
	public int getY(){
		return yPos;
	}
	
	public double getAngle(){
		return angle;
	}
	
	public double getVelocity(){
		return velocity;
	}
	
	public boolean isUsernameEmpty() {
		if(username.isEmpty() || username.contentEquals("")) {
			return true;
		} else {
			return false;
		}
	}
	
	public String checkUsername() {
		if(username.isEmpty() || username.contentEquals("")) {
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
	 * Wird aufgerufen, wenn Spieler die Figur des anderen Spielers getroffen hat
	 */
	public void hitEnemyFigure() {
		System.out.println("Spieler " + getUsername() + " <" + getArrayIndex() + "> hat gegnerische Figur getroffen!");
	}
	
	/**
	 * Wird aufgerufen, wenn die Figur des Spielers getroffen wird
	 */
	public void figureWasHit() {
		reduceLifesLeft();
		System.out.println("Figur von Spieler " + getUsername() + " <" + getArrayIndex() + "> wurde getroffen!" + "Leben übrig: " + getLifesLeft());
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
	 * @param p Spieler, dessen Gegenspieler gesucht wird
	 * @return Array Index des anderen Spielers
	 */
	public static int getOtherPlayersArrayIndex(Player p) {
		return (p.getArrayIndex() == 0) ? 1 : 0;
	}
	
	//
	// Other methods
	//


}
