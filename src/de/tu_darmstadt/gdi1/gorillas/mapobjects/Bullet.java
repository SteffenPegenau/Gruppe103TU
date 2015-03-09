package de.tu_darmstadt.gdi1.gorillas.mapobjects;

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
		velocityY = (float) (Math.sin(angle) * velocity);
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
		float x = posX0
				+ (float) (velocityX * SCALING_FACTOR * existenceTimeInNS);
		float y = posYO
				- (float) (velocityY * SCALING_FACTOR * existenceTimeInNS)
				+ (float) (0.5 * 10 * Math.pow(SCALING_FACTOR
						* existenceTimeInNS, 2));
		Vector2f newPosition = new Vector2f(x, y);
		//System.out.println("New Position: " + newPosition);
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
		//System.out.println("Add " + delta + " = " + getExistenceTimeInNS());
		this.existenceTimeInNS += delta;
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
