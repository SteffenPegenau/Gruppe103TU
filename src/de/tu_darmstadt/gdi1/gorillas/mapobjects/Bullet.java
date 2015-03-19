package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.gorillas.comments.EnumToString;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import de.tu_darmstadt.gdi1.gorillas.ui.states.GameplayState;
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
	public double gravity = 10.0;

	protected GameplayState gameplayState;

	// Radiant, nicht in Grad!
	protected double angle;

	public static float windS;
	public static boolean windSetting = false;

	protected float accelerationX;
	protected float accelerationY;

	// Time of existence in micro seconds
	protected long existenceTimeInms;

	protected double velocity;
	protected double velocityX;
	protected double velocityY;

	protected float posX0;
	protected float posY0;

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
	 * Geklaut bei
	 * http://stackoverflow.com/questions/2808535/round-a-double-to-2
	 * -decimal-places
	 * 
	 * @param value
	 *            Zu rundender Wert
	 * @param places
	 *            Zahl der Nachkommastellen
	 * @return
	 */
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	private double cos() {
		double cos = Math.cos(Math.toRadians(angle));
		cos = round(cos, 12);
		// return StrictMath.round(StrictMath.cos(StrictMath.toRadians(angle)));
		return cos;
	}

	private double sin() {
		double sin = Math.sin(Math.toRadians(angle));
		sin = round(sin, 12);
		// return StrictMath.round(StrictMath.cos(StrictMath.toRadians(angle)));
		// return StrictMath.round(StrictMath.sin(StrictMath.toRadians(angle)));
		return sin;
	}

	/**
	 * 
	 * @param velocity
	 */
	public void setVelocity(double angleInDegree, float velocity) {
		this.velocity = velocity;
		// System.out.println("ArrayIndex=" + player.getArrayIndex());
		// System.out.print("Winkel=" + angleInDegree + " wird zu ");
		// Unterscheidung linker - rechter - Spieler
		if (player.getArrayIndex() == 1) {
			// rechter Spieler
			this.angle = 180 - angleInDegree;
		} else {
			this.angle = angleInDegree;
		}
		// System.out.println(angle);
		velocityX = cos() * velocity;
		velocityY = sin() * velocity;
		// System.out.println("Winkel (Grad): " + angle + "\t Winkle(rad): " +
		// Math.toRadians(angle) + "\tcos=" + cos() + "\tsin=" + sin());
		// System.out.println("v=" + velocity + "\tvX=" + velocityX + "\tvY=" +
		// velocityY);
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

	/**
	 * Berechnet den Abstand zwischen Ziel und eigentlichem Treffer
	 * 
	 * @param enemy
	 *            der zu treffende Spieler
	 * @return den Abstand vom Aufprallort der Banane zum Spieler
	 */
	private int getDist(Player enemy) {
		if (player.getArrayIndex() == 0) {
			// Wenn der aktive Spieler gerade Spieler0 ist
			enemy = gameplayState.getPlayer(1);
			// ist der Gegner Spieler1
			return (int) Math.sqrt(Math.pow(enemy.getPlayersFigure()
					.getPosition().x - getPosition().x, 2)
					+ Math.pow(enemy.getPlayersFigure().getPosition().y
							- getPosition().y, 2));
			// das hier ist die Abstandsformel nach dem Satz des Pythagoras
		} else {
			enemy = gameplayState.getPlayer(0);
			return (int) Math.sqrt(Math.pow(getPosition().x
					- enemy.getPlayersFigure().getPosition().x, 2)
					+ Math.pow(getPosition().y, 2 - enemy.getPlayersFigure()
							.getPosition().y));
		}
	}

	/**
	 * Hier wird entsprechend der berechneten Entfernung ein passender Spruch
	 * ausgegeben
	 * 
	 * @return
	 */
	public String fittingComment() {
		EnumToString enumToString = new EnumToString();
		// System.out.println(getDist(player));
		if (getDist(player) <= 150 && getDist(player) >= -150) {
			return enumToString.printClose();
		}
		if (getDist(player) >= 150) {
			return enumToString.printToShort();
		} else {
			return enumToString.printFarOff();
		}
	}

	public Vector2f calculateNewPosition() {

		double scaledTimeOfExistence = SCALING_FACTOR * existenceTimeInms;
		// TODO wind an aus
		double x = posX0
				+ velocityX
				* scaledTimeOfExistence
				+ (0.5f *  GameplayState.wind
						* (scaledTimeOfExistence * scaledTimeOfExistence) * getWindS());
		System.out.println("Windstärke: " + GameplayState.wind);
		double y = posY0 - velocityY * scaledTimeOfExistence + 0.5
				* getGravity() * Math.pow(scaledTimeOfExistence, 2);
		System.out.println("Gravitation: " + getGravity());
		Vector2f newPosition = new Vector2f((float) x, (float) y);
		// System.out.println("New Position: " + newPosition);
		// TODO: Umsetzen des Dotzen:
		/*
		 * Beispielcode: if (y == 600 && bullet.spped < 25) { alles auf null
		 * setzen return newPosition;
		 */

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

	public float getPosY0() {
		return posY0;
	}

	public void setPosY0(float posY0) {
		this.posY0 = posY0;
	}

	public long getExistenceTimeInNS() {
		return existenceTimeInms;
	}

	public void addExistenceTime(long delta) {
		// System.out.println("AddExistenceTime: " + getExistenceTimeInNS() +
		// " + " + delta + " = " + (getExistenceTimeInNS() + delta));
		existenceTimeInms += delta;

	}

	protected void removeEntityFromState(StateBasedGame sb,
			GameplayState gameplayState, Entity entity) {
		gameplayState.removeBullet((Bullet) entity);
		StateBasedEntityManager.getInstance().removeEntity(
				sb.getCurrentStateID(), entity);

	}

	protected Event leftScreenLeftRightBottom() {
		Event leftScreen = new LoopEvent();
		leftScreen.addAction(new Action() {
			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				Entity entity = event.getOwnerEntity();
				Vector2f position = entity.getPosition();
				EnumToString enumToString = new EnumToString();
				float x = position.x;
				float y = position.y;
				if (x < 0 || x > gc.getWidth() || y > gc.getHeight()) {
					removeEntityFromState(sb, gameplayState, entity);
					System.out.println("Removed " + entity.getID()
							+ " at Position " + x + " | " + y);
					System.out.println(enumToString.printFarOff());
				}

			}
		});
		return leftScreen;
	}

	protected Action collisionAction() {
		class collisionAction implements Action {
			private GameplayState gameplayState;
			private Player player;
			private Player enemyPlayer;

			public collisionAction(Bullet bullet, GameplayState gameplayState,
					Player player) {
				this.gameplayState = gameplayState;
				this.player = player;
				int arrayIndexEnemyPlayer = (player.getArrayIndex() == 0) ? 1
						: 0;
				this.enemyPlayer = gameplayState
						.getPlayer(arrayIndexEnemyPlayer);
			}

			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {

				// hole die Entity, mit der kollidiert wurde
				CollisionEvent collider = (CollisionEvent) event;
				Entity entity = collider.getCollidedEntity();
				EnumToString enumToString = new EnumToString();
				if (!entity.getID().contentEquals("background")) {
					System.out.println("COLLIDED WITH " + entity.getID());
					gameplayState.getSkyline().sun.changeBackImage();
					// wenn diese durch ein Pattern zerst�rt werden kann, dann
					// caste
					// zu IDestructible
					// ansonsten passiert bei der Kollision nichts

					IDestructible destructible = null;
					System.out.println(fittingComment());
					if (entity.getID().contentEquals(
							enemyPlayer.getPlayersFigure().getID())) {
						// Gegner getroffen!
						// System.out.println("Gegner getroffen");
						enemyPlayer.figureWasHit();
						player.hitEnemyFigure();
						if (enemyPlayer.getLifesLeft() > 0) {
							gameplayState.getSkyline().createSkyline();
							gameplayState.positionPlayer();
						}
						System.out.println(enumToString.printHit());
					} else if (entity.getID().contentEquals("sun")) {
						gameplayState.getSkyline().sun.changeImage();
						return;
					} else if (entity instanceof IDestructible) {
						// Etwas anderes getroffen, zB Gebäude
						destructible = (IDestructible) entity;
						destructible.impactAt(event.getOwnerEntity()
								.getPosition());
					} else {
						return;
					}
					removeEntityFromState(sb, gameplayState,
							event.getOwnerEntity());
					// zerst�re die Entit�t (dabei wird das der Entit�t
					// zugewiese Zerst�rungs-Pattern benutzt)

				}
			}
		}

		Action a = new collisionAction(this, gameplayState, player);
		return a;
	}

	protected Event getCollisionEvent() {
		Event collisionEvent = new CollisionEvent();
		collisionEvent.addAction(collisionAction());
		// collisionEvent.addAction(new DestroyEntityAction());
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

	public void setGameplayState(GameplayState gameplayState) {
		this.gameplayState = gameplayState;
	}

	public static void perfectDegreeShot(double degree, Player player,
			Player victim, double gravity) {
		System.out
				.println(">>>>>>>>>>>>>>>>> Berechnung der perfekten Geschwindigkeit für den Winkel "
						+ degree + " Grad");
		FigureWithWeapon playersFigure = player.getPlayersFigure();
		FigureWithWeapon victimsFigure = victim.getPlayersFigure();

		double x = victimsFigure.getPosition().x;
		double y = victimsFigure.getPosition().y;

		double x0 = playersFigure.getPosition().x;
		double y0 = playersFigure.getPosition().y;

		double deltaX = 0;
		double deltaY = 0;

		if (player.getArrayIndex() == 0) {
			deltaX = x - x0;
			deltaY = y - y0;
		} else {
			deltaX = x0 - x;
			deltaY = y0 - y;
		}

		// System.out.println("x=" + x + "\ty=" + y + "\tx0=" + x0 + "\ty0="+y0
		// );
		// System.out.println("Delta X: " + deltaX + "\tDelta Y: " + deltaY);
		double tan = Math.tan(Math.toRadians(degree));
		// System.out.println("Tan(89Grad)=" + tan);
		double toBeSquareRooted = 2 * (deltaY + tan * deltaX) / gravity;
		// System.out.println("Wurzel:" + toBeSquareRooted);
		double t = Math.sqrt(toBeSquareRooted);

		// System.out.println("Flugzeit: " + t);
		double cos = Math.cos(Math.toRadians(degree));
		double v = deltaX / (cos * t);

		System.out.println("Perfect velocity for a " + degree
				+ " Degree shot: " + v);

		System.out.println("<<<<<<<<<<<<<<<<<");
	}

	public void setGravity(double g) {
		gravity = g;
	}

	public double getGravity() {
		return gravity;
	}

	public static void setWindSOn() {
		Bullet.windS = 1;
	}

	public static void setWindSOff() {
		Bullet.windS = 0;
	}
	
	public static float getWindS() {
		return windS;
	}
	public static boolean getWindSetting() {
		if (getWindS() == 1) {
			windSetting = true;
		} else {
			windSetting = false;
		}
		return windSetting;
	}

}

// package de.tu_darmstadt.gdi1.gorillas.mapobjects;
//
// import java.math.BigDecimal;
// import java.math.RoundingMode;
//
// import org.newdawn.slick.GameContainer;
// import org.newdawn.slick.geom.Vector2f;
// import org.newdawn.slick.state.StateBasedGame;
//
// import de.tu_darmstadt.gdi1.gorillas.comments.EnumToString;
// import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
// import de.tu_darmstadt.gdi1.gorillas.ui.states.GameplayState;
// import eea.engine.action.Action;
// import eea.engine.component.Component;
// import eea.engine.entity.Entity;
// import eea.engine.entity.StateBasedEntityManager;
// import eea.engine.event.Event;
// import eea.engine.event.basicevents.CollisionEvent;
// import eea.engine.event.basicevents.LoopEvent;
// import eea.engine.interfaces.IDestructible;
//
// /**
// * Class Bullet
// */
// public class Bullet extends MapObject {
// public final static double SCALING_FACTOR = (double) 1 / 100;
// public final static double GRAVITY = 10.0;
//
// protected GameplayState gameplayState;
//
// // Radiant, nicht in Grad!
// protected double angle;
//
// protected float accelerationX;
// protected float accelerationY;
//
// // Time of existence in micro seconds
// protected long existenceTimeInms;
//
// protected double velocity;
// protected double velocityX;
// protected double velocityY;
//
// protected float posX0;
// protected float posY0;
//
// protected Player player;
// protected Boolean sunhit = false;
//
// public Bullet(String entityID) {
// super(entityID);
//
// }
//
// public void setThrowSettings(double angle, float velocity) {
// setAngle(angle);
// setVelocity(angle, velocity);
// }
//
// public double getAngle() {
// return angle;
// }
//
// private void setAngle(double angle) {
// this.angle = angle;
// }
//
// public double getVelocity() {
// return velocity;
// }
//
// /**
// * Funktion zum Runden von Doubles auf eine best. Zahl von Nachkommastellen.
// *
// * Geklaut bei
// * http://stackoverflow.com/questions/2808535/round-a-double-to-2
// * -decimal-places
// *
// * @param value
// * Zu rundender Wert
// * @param places
// * Zahl der Nachkommastellen
// * @return
// */
// public static double round(double value, int places) {
// if (places < 0)
// throw new IllegalArgumentException();
//
// BigDecimal bd = new BigDecimal(value);
// bd = bd.setScale(places, RoundingMode.HALF_UP);
// return bd.doubleValue();
// }
//
// private double cos() {
// double cos = Math.cos(Math.toRadians(angle));
// cos = round(cos, 12);
// // return StrictMath.round(StrictMath.cos(StrictMath.toRadians(angle)));
// return cos;
// }
//
// private double sin() {
// double sin = Math.sin(Math.toRadians(angle));
// sin = round(sin, 12);
// // return StrictMath.round(StrictMath.cos(StrictMath.toRadians(angle)));
// // return StrictMath.round(StrictMath.sin(StrictMath.toRadians(angle)));
// return sin;
// }
//
// /**
// *
// * @param velocity
// */
// public void setVelocity(double angleInDegree, float velocity) {
// this.velocity = velocity;
// // System.out.println("ArrayIndex=" + player.getArrayIndex());
// // System.out.print("Winkel=" + angleInDegree + " wird zu ");
// // Unterscheidung linker - rechter - Spieler
// if (player.getArrayIndex() == 1) {
// // rechter Spieler
// this.angle = 180 - angleInDegree;
// } else {
// this.angle = angleInDegree;
// }
// // System.out.println(angle);
// velocityX = cos() * velocity;
// velocityY = sin() * velocity;
// // System.out.println("Winkel (Grad): " + angle + "\t Winkle(rad): " +
// // Math.toRadians(angle) + "\tcos=" + cos() + "\tsin=" + sin());
// // System.out.println("v=" + velocity + "\tvX=" + velocityX + "\tvY=" +
// // velocityY);
// }
//
// public float getAccelerationX() {
// return accelerationX;
// }
//
// public float getAccelerationY() {
// return accelerationY;
// }
//
// public double getVelocityX() {
// return velocityX;
// }
//
// public double getVelocityY() {
// return velocityY;
// }
//
// /**
// * Berechnet den Abstand zwischen Ziel und eigentlichem Treffer
// *
// * @param enemy
// * der zu treffende Spieler
// * @return den Abstand vom Aufprallort der Banane zum Spieler
// */
// private int getDist(Player enemy) {
// if (player.getArrayIndex() == 0) {
// // Wenn der aktive Spieler gerade Spieler0 ist
// enemy = gameplayState.getPlayer(1);
// // ist der Gegner Spieler1
// return (int) Math.sqrt(Math.pow(enemy.getPlayersFigure()
// .getPosition().x - getPosition().x, 2)
// + Math.pow(enemy.getPlayersFigure().getPosition().y
// - getPosition().y, 2));
// // das hier ist die Abstandsformel nach dem Satz des Pythagoras
// } else {
// enemy = gameplayState.getPlayer(0);
// return (int) Math.sqrt(Math.pow(getPosition().x
// - enemy.getPlayersFigure().getPosition().x, 2)
// + Math.pow(getPosition().y, 2 - enemy.getPlayersFigure()
// .getPosition().y));
// }
// }
//
// /**
// * Hier wird entsprechend der berechneten Entfernung ein passender Spruch
// * ausgegeben
// *
// * @return
// */
// public String fittingComment() {
// EnumToString enumToString = new EnumToString();
// // System.out.println(getDist(player));
// if (getDist(player) <= 150 && getDist(player) >= -150) {
// return enumToString.printClose();
// }
// if (getDist(player) >= 150) {
// return enumToString.printToShort();
// } else {
// return enumToString.printFarOff();
// }
// }
//
// public Vector2f calculateNewPosition() {
// double scaledTimeOfExistence = SCALING_FACTOR * existenceTimeInms;
//
// double x = posX0 + velocityX * scaledTimeOfExistence;
// double y = posY0 - velocityY * scaledTimeOfExistence + 0.5 * GRAVITY
// * Math.pow(scaledTimeOfExistence, 2);
//
// Vector2f newPosition = new Vector2f((float) x, (float) y);
// // System.out.println("New Position: " + newPosition);
// return newPosition;
// }
//
// @Override
// public void update(GameContainer gc, StateBasedGame sb, int delta) {
// super.update(gc, sb, delta);
// addExistenceTime(delta);
// this.setPosition(calculateNewPosition());
// }
//
// public float getPosX0() {
// return posX0;
// }
//
// public void setPosX0(float posX0) {
// this.posX0 = posX0;
// }
//
// public float getPosY0() {
// return posY0;
// }
//
// public void setPosY0(float posY0) {
// this.posY0 = posY0;
// }
//
// public long getExistenceTimeInNS() {
// return existenceTimeInms;
// }
//
// public void addExistenceTime(long delta) {
// // System.out.println("AddExistenceTime: " + getExistenceTimeInNS() +
// // " + " + delta + " = " + (getExistenceTimeInNS() + delta));
// existenceTimeInms += delta;
//
// }
//
// protected void removeEntityFromState(StateBasedGame sb,
// GameplayState gameplayState, Entity entity) {
// gameplayState.removeBullet((Bullet) entity);
// StateBasedEntityManager.getInstance().removeEntity(
// sb.getCurrentStateID(), entity);
//
// }
//
// protected Event leftScreenLeftRightBottom() {
// Event leftScreen = new LoopEvent();
// leftScreen.addAction(new Action() {
// @Override
// public void update(GameContainer gc, StateBasedGame sb, int delta,
// Component event) {
// Entity entity = event.getOwnerEntity();
// Vector2f position = entity.getPosition();
// EnumToString enumToString = new EnumToString();
// float x = position.x;
// float y = position.y;
// if (x < 0 || x > gc.getWidth() || y > gc.getHeight()) {
// removeEntityFromState(sb, gameplayState, entity);
// System.out.println("Removed " + entity.getID()
// + " at Position " + x + " | " + y);
// System.out.println(enumToString.printFarOff());
// }
//
// }
// });
// return leftScreen;
// }
//
// protected Action collisionAction() {
// class collisionAction implements Action {
// private GameplayState gameplayState;
// private Player player;
// private Player enemyPlayer;
//
// public collisionAction(Bullet bullet, GameplayState gameplayState,
// Player player) {
// this.gameplayState = gameplayState;
// this.player = player;
// int arrayIndexEnemyPlayer = (player.getArrayIndex() == 0) ? 1
// : 0;
// this.enemyPlayer = gameplayState
// .getPlayer(arrayIndexEnemyPlayer);
// }
//
// @Override
// public void update(GameContainer gc, StateBasedGame sb, int delta,
// Component event) {
//
// // hole die Entity, mit der kollidiert wurde
// CollisionEvent collider = (CollisionEvent) event;
// Entity entity = collider.getCollidedEntity();
// EnumToString enumToString = new EnumToString();
// gameplayState.skyline.sun.changeBackImage();
// if (!entity.getID().contentEquals("background")) {
// System.out.println("COLLIDED WITH " + entity.getID());
// // wenn diese durch ein Pattern zerst�rt werden kann, dann
// // caste
// // zu IDestructible
// // ansonsten passiert bei der Kollision nichts
// IDestructible destructible = null;
// //System.out.println(fittingComment());
// if (entity.getID().contentEquals(
// enemyPlayer.getPlayersFigure().getID())) {
// // Gegner getroffen!
// // System.out.println("Gegner getroffen");
// enemyPlayer.figureWasHit();
// player.hitEnemyFigure();
// if (enemyPlayer.getLifesLeft() > 0) {
// gameplayState.skyline.rebuildSkyline();
// }
// System.out.println(enumToString.printHit());
// } else if (entity.getID().contentEquals("sun")) {
// gameplayState.skyline.sun.changeImage();
// return;
// } else if (entity instanceof IDestructible) {
// // Etwas anderes getroffen, zB Gebäude
// destructible = (IDestructible) entity;
// destructible.impactAt(event.getOwnerEntity()
// .getPosition());
// } else {
// return;
// }
// removeEntityFromState(sb, gameplayState,
// event.getOwnerEntity());
// // zerst�re die Entit�t (dabei wird das der Entit�t
// // zugewiese Zerst�rungs-Pattern benutzt)
//
// }
// }
// }
//
// Action a = new collisionAction(this, gameplayState, player);
// return a;
// }
//
// protected Event getCollisionEvent() {
// Event collisionEvent = new CollisionEvent();
// collisionEvent.addAction(collisionAction());
// // collisionEvent.addAction(new DestroyEntityAction());
// return collisionEvent;
// }
//
// /**
// * Fügt zu der aktuellen Kugel alle Events hinzu
// */
// public void addEvents() {
// this.addComponent(leftScreenLeftRightBottom());
// this.addComponent(getCollisionEvent());
// }
//
// public Player getPlayer() {
// return player;
// }
//
// public void setPlayer(Player player) {
// this.player = player;
// }
//
// public void setGameplayState(GameplayState gameplayState) {
// this.gameplayState = gameplayState;
// }
//
// public static void perfectDegreeShot(double degree, Player player,
// Player victim) {
// System.out
// .println(">>>>>>>>>>>>>>>>> Berechnung der perfekten Geschwindigkeit für den Winkel "
// + degree + " Grad");
// FigureWithWeapon playersFigure = player.getPlayersFigure();
// FigureWithWeapon victimsFigure = victim.getPlayersFigure();
//
// double x = victimsFigure.getPosition().x;
// double y = victimsFigure.getPosition().y;
//
// double x0 = playersFigure.getPosition().x;
// double y0 = playersFigure.getPosition().y;
//
// double deltaX = 0;
// double deltaY = 0;
//
// if (player.getArrayIndex() == 0) {
// deltaX = x - x0;
// deltaY = y - y0;
// } else {
// deltaX = x0 - x;
// deltaY = y0 - y;
// }
//
// // System.out.println("x=" + x + "\ty=" + y + "\tx0=" + x0 + "\ty0="+y0
// // );
// // System.out.println("Delta X: " + deltaX + "\tDelta Y: " + deltaY);
// double tan = Math.tan(Math.toRadians(degree));
// // System.out.println("Tan(89Grad)=" + tan);
// double toBeSquareRooted = 2 * (deltaY + tan * deltaX) / GRAVITY;
// // System.out.println("Wurzel:" + toBeSquareRooted);
// double t = Math.sqrt(toBeSquareRooted);
//
// // System.out.println("Flugzeit: " + t);
// double cos = Math.cos(Math.toRadians(degree));
// double v = deltaX / (cos * t);
//
// System.out.println("Perfect velocity for a " + degree
// + " Degree shot: " + v);
//
// System.out.println("<<<<<<<<<<<<<<<<<");
// }
//
// }
