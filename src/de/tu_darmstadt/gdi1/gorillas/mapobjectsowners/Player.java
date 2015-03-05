package de.tu_darmstadt.gdi1.gorillas.mapobjectsowners;

import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Alignment;
import de.matthiasmann.twl.BoxLayout;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Container;
import de.matthiasmann.twl.DialogLayout;
import de.matthiasmann.twl.DialogLayout.Group;
import de.matthiasmann.twl.HAlignment;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.TextArea;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.AnimationState;
import de.matthiasmann.twl.renderer.Font;
import de.matthiasmann.twl.renderer.FontCache;
import de.matthiasmann.twl.textarea.HTMLTextAreaModel;
import de.matthiasmann.twl.textarea.SimpleTextAreaModel;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.ui.states.GameSetupState;

/**
 * Class Player
 */
public class Player implements java.io.Serializable{

	//
	// Fields
	//

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

	//
	// Other methods
	//


}
