package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import eea.engine.event.basicevents.LoopEvent;

/**
 * Class Bullet
 */
public class Bullet extends MapObject{
	LoopEvent movingX;
	LoopEvent movingY;
	
	public final static double SCALING_FACTOR = Math.pow(10, -2.8);
	
	protected double angle;
	
	protected float accelerationX;
	protected float accelerationY;
	
	// Time of existence in nano seconds
	protected long existenceTimeInNS;
	
	protected float velocity;
	protected float velocityX;
	protected float velocityY;
	
	protected float posX0;
	protected float posYO;
	
	protected Player player;


	public Bullet(String entityID) {
		super(entityID);
		
	}
	
	public void setThrowSettings(double angle, float velocity) {
		setAngle(angle);
		setVelocity(angle, velocity);
	}
	
	public double getAngle() {
		return angle;
	}



	private void setAngle(double angle) {
		this.angle = angle;
	}



	public float getVelocity() {
		return velocity;
	}


	/**
	 * 
	 * @param velocity
	 */
	public void setVelocity(double angle, float velocity) {
		this.velocity = velocity;
		velocityX = (float) (Math.cos(angle) * velocity);
		velocityY = (float) (Math.cos(angle) * velocity);
	}

	public float getAccelerationX() {
		return accelerationX;
	}



	public float getAccelerationY() {
		return accelerationY;
	}



	public float getVelocityX() {
		return velocityX;
	}



	public float getVelocityY() {
		return velocityY;
	}

	protected Vector2f calculateNewPosition() {
		float x = posX0 + (float) (velocityX * SCALING_FACTOR * existenceTimeInNS);
		float y = posYO - (float) (velocityY * SCALING_FACTOR * existenceTimeInNS) + (float) (0.5 * 10 * Math.pow(SCALING_FACTOR * existenceTimeInNS, 2));
		Vector2f newPosition = new Vector2f(x, y);
		System.out.println("New Position: " + newPosition);
		return newPosition;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		super.update(gc, sb, delta);
		addExistenceTime(delta);
		this.setPosition(calculateNewPosition());
	}
	

	public float getPosX0() {
		return posX0;
	}

	public void setPosX0(float posX0) {
		this.posX0 = posX0;
	}

	public float getPosYO() {
		return posYO;
	}

	public void setPosYO(float posYO) {
		this.posYO = posYO;
	}

	public long getExistenceTimeInNS() {
		return existenceTimeInNS;
	}

	public void addExistenceTime(int delta) {
		System.out.println("Add " + delta + " = " + getExistenceTimeInNS());
		this.existenceTimeInNS += delta;
	}

	
	


	/**
	 * 
	 * @param time die Zeit
	 * @return die momentane x-Koordinate
	 
	public int actualXPos(int time) {
		return player.getX()
				+ this.xMod(player.getAngle(), player.getVelocity()) * time;
	}

	/**
	 * 
	 * @param time die Zeit
	 * @param gravity die Erdbeschleunigung
	 * @return die momentane y-Koordinate
	 
	public int actualYPos(int time, double gravity) {
		return (int) (player.getY()
				+ this.yMod(player.getAngle(), player.getVelocity()) * time + gravity
				* time / 2);
	}
*/
	//
	// Accessor methods
	//

	//
	// Other methods
	//

}
