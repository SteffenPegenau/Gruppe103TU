package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;

/**
 * Class Bullet
 */
public class Bullet {

	//
	// Fields
	//

	private Player player;

	//
	// Constructors
	//
	public Bullet() {

	}

	//
	// Methods
	//

	/**
	 * 
	 * @param angle der Abwurfwinkel
	 * @param velocity die Abwurfgeschwindigkeit
	 * @return die Änderung der Geschwindigkeit
	 */
	private int xMod(double angle, double velocity) {
		return (int) (Math.cos(angle) * velocity);
	}

	/**
	 * 
	 * @param angle der Abwurfwinkel
	 * @param velocity die Abwurfgeschwindigkeit
	 * @return die Änderung der Geschwindigkeit
	 */
	private int yMod(double angle, double velocity) {
		return (int) (Math.sin(angle) * velocity);
	}

	/**
	 * 
	 * @param time die Zeit
	 * @return die momentane x-Koordinate
	 */
	public int actualXPos(int time) {
		return player.getX()
				+ this.xMod(player.getAngle(), player.getVelocity()) * time;
	}

	/**
	 * 
	 * @param time die Zeit
	 * @param gravity die Erdbeschleunigung
	 * @return die momentane y-Koordinate
	 */
	public int actualYPos(int time, double gravity) {
		return (int) (player.getY()
				+ this.yMod(player.getAngle(), player.getVelocity()) * time + gravity
				* time / 2);
	}

	//
	// Accessor methods
	//

	//
	// Other methods
	//

}
