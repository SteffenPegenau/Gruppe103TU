package de.tu_darmstadt.gdi1.gorillas.weapons;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import de.tu_darmstadt.gdi1.gorillas.mapobjects.Bullet;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.FigureWithWeapon;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import eea.engine.entity.Entity;



/**
 * Class Weapon
 */
public abstract class Weapon {

		//
		// Fields
		//
	
		public Class<? extends Bullet> bullet;
		
		// Used to build the name for the bullet (it's an entity)
		protected int projectileCounter;

		protected int ammunition;
		
		//
		// Constructors
		//
		public Weapon () {
			projectileCounter = 0;
		};
		
		//
		// Methods
		//


		//
		// Accessor methods
		//

		/**
		 * Set the value of ammunition
		 * @param newVar the new value of ammunition
		 */
		protected void setAmmunition (int newVar) {
				ammunition = newVar;
		}

		/**
		 * Get the value of ammunition
		 * @return the value of ammunition
		 */
		protected int getAmmunition () {
				return ammunition;
		}

		//
		// Other methods
		//
		
		private Bullet newBulletAsEntity(Player player, FigureWithWeapon fig) {
			Constructor<? extends Bullet> con = null;
			Bullet projectile = null;
			try {
				con = bullet.getConstructor(String.class);
			} catch (NoSuchMethodException | SecurityException e1) {
				e1.printStackTrace();
			}
			try {
				projectile = con.newInstance(player.getUsername() + "_SHOT_" + projectileCounter);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
			
			projectile.setPlayer(player);
			return projectile;
		}

		public Bullet shot(Player player, FigureWithWeapon fig, double angle, float velocity) {
			Bullet projectile = newBulletAsEntity(player, fig);
			//projectile.setPosition(fig.getPosition());
			projectile.addEvents();
			projectile.setPosX0(fig.getPosition().x);
			projectile.setPosYO(fig.getPosition().y);
			System.out.println("Shot with Angle " + angle + " and velocity " + velocity);
			projectile.setVelocity(angle, velocity);
			
			//LoopEvent loop = new LoopEvent();
			//Action a = new MoveRightAction(0.25f);
			//loop.addAction(a);
			//projectile.addComponent(loop);
			projectileCounter++;
			return projectile;
		}
}
