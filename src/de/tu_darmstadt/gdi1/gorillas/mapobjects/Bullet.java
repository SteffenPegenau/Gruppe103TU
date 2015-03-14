package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.Event;
import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.event.basicevents.LoopEvent;
import eea.engine.interfaces.IDestructible;

/**
 * Class Bullet
 */
public class Bullet extends MapObject {
	public final static double SCALING_FACTOR = (double) 1 / 100;
	public final static double GRAVITY = 10.0;
	
	// Radiant, nicht in Grad!
	protected double angle;

	protected float accelerationX;
	protected float accelerationY;

	// Time of existence in micro seconds
	protected long existenceTimeInms;

	protected double velocity;
	protected double velocityX;
	protected double velocityY;

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

	public double getVelocity() {
		return velocity;
	}
	
	/**
	 * Funktion zum Runden von Doubles auf eine best. Zahl von Nachkommastellen.
	 * 
	 * Geklaut bei http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
	 * 
	 * @param value Zu rundender Wert
	 * @param places Zahl der Nachkommastellen
	 * @return
	 */
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	private double cos() {
		double cos = Math.cos(Math.toRadians(angle));
		cos = round(cos, 12);
		//return StrictMath.round(StrictMath.cos(StrictMath.toRadians(angle)));
		return cos;
	}
	
	private double sin() {
		double sin = Math.sin(Math.toRadians(angle));
		sin = round(sin, 12);
		//return StrictMath.round(StrictMath.cos(StrictMath.toRadians(angle)));
		//return StrictMath.round(StrictMath.sin(StrictMath.toRadians(angle)));
		return sin;
	}
	
	
	/**
	 * 
	 * @param velocity
	 */
	public void setVelocity(double angleInDegree, float velocity) {
		this.velocity = velocity;
		//System.out.println("ArrayIndex=" + player.getArrayIndex());
		//System.out.print("Winkel=" + angleInDegree + " wird zu ");
		// Unterscheidung linker - rechter - Spieler
		if(player.getArrayIndex() == 1) {
			// rechter Spieler
			this.angle = 180 - angleInDegree;
		} else {
			this.angle = angleInDegree;
		}
		//System.out.println(angle);
		velocityX = cos() * velocity;
		velocityY = sin() * velocity;
		System.out.println("Winkel (Grad): " + angle + "\t Winkle(rad): " + Math.toRadians(angle) + "\tcos=" + cos() + "\tsin=" + sin());
		System.out.println("v=" + velocity + "\tvX=" + velocityX + "\tvY=" + velocityY);
	}

	public float getAccelerationX() {
		return accelerationX;
	}

	public float getAccelerationY() {
		return accelerationY;
	}

	public double getVelocityX() {
		return velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public Vector2f calculateNewPosition() {
		double scaledTimeOfExistence = SCALING_FACTOR * existenceTimeInms;
		double x = posX0
				+ velocityX * scaledTimeOfExistence;
		double y = posYO
				- velocityY * scaledTimeOfExistence
				+ 0.5 * GRAVITY * Math.pow(scaledTimeOfExistence, 2);
		Vector2f newPosition = new Vector2f((float) x, (float) y);
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
		return existenceTimeInms;
	}

	public void addExistenceTime(long delta) {
		//System.out.println("AddExistenceTime: " + getExistenceTimeInNS() + " + " + delta + " = " + (getExistenceTimeInNS() + delta));
		existenceTimeInms += delta;
		
	}
	
	protected Event leftScreenLeftRightBottom() {
		Event leftScreen = new LoopEvent();
		leftScreen.addAction(new Action() {
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				Entity entity = event.getOwnerEntity();
				Vector2f position = entity.getPosition();
				float x = position.x;
				float y = position.y;
				if(x < 0 || x > gc.getWidth() || y > gc.getHeight()) {
					StateBasedEntityManager.getInstance().removeEntity(sb.getCurrentStateID(),
					        entity);
					System.out.println("Removed " + entity.getID() + " at Position " + x + " | " + y);
				}
				
			}
		});
		return leftScreen;
	}

	protected Event getCollisionEvent() {
		Event collisionEvent = new CollisionEvent();
		collisionEvent.addAction(new Action() {
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {

				// hole die Entity, mit der kollidiert wurde
				CollisionEvent collider = (CollisionEvent) event;
				Entity entity = collider.getCollidedEntity();
				if (!entity.getID().contentEquals("background")) {
					// wenn diese durch ein Pattern zerst�rt werden kann, dann
					// caste
					// zu IDestructible
					// ansonsten passiert bei der Kollision nichts
					IDestructible destructible = null;
					if (entity instanceof IDestructible) {
						destructible = (IDestructible) entity;
					} else {
						return;
					}

					// zerst�re die Entit�t (dabei wird das der Entit�t
					// zugewiese Zerst�rungs-Pattern benutzt)
					destructible.impactAt(event.getOwnerEntity().getPosition());
					
					StateBasedEntityManager.getInstance().removeEntity(sb.getCurrentStateID(),
					        event.getOwnerEntity());
				}
			}
		});
		//collisionEvent.addAction(new DestroyEntityAction());
		return collisionEvent;
	}

	/**
	 * Fügt zu der aktuellen Kugel alle Events hinzu
	 */
	public void addEvents() {
		this.addComponent(leftScreenLeftRightBottom());
		this.addComponent(getCollisionEvent());
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * 
	 * @param time
	 *            die Zeit
	 * @return die momentane x-Koordinate
	 * 
	 *         public int actualXPos(int time) { return player.getX() +
	 *         this.xMod(player.getAngle(), player.getVelocity()) * time; }
	 * 
	 *         /**
	 * 
	 * @param time
	 *            die Zeit
	 * @param gravity
	 *            die Erdbeschleunigung
	 * @return die momentane y-Koordinate
	 * 
	 *         public int actualYPos(int time, double gravity) { return (int)
	 *         (player.getY() + this.yMod(player.getAngle(),
	 *         player.getVelocity()) * time + gravity time / 2); }
	 */
	//
	// Accessor methods
	//

	//
	// Other methods
	//

}
