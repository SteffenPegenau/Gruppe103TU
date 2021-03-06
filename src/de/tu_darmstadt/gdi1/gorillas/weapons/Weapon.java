package de.tu_darmstadt.gdi1.gorillas.weapons;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import de.tu_darmstadt.gdi1.gorillas.mapobjects.Bullet;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.FigureWithWeapon;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import de.tu_darmstadt.gdi1.gorillas.ui.states.GameplayState;



/**
 * Class Weapon
 */
public abstract class Weapon {
		public Class<? extends Bullet> bullet;
		
		// Used to build the name for the bullet (it's an entity)
		protected int projectileCounter;
		protected int ammunition;
		protected double gravityInput;
		
		public Weapon () {
			projectileCounter = 0;
		}
		
		
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
		
		public void setProjectileCounter(int projectile) {
			this.projectileCounter = projectile;
		}

		public int getProjectileCounter() {
			return projectileCounter;
		}
		//
		// Other methods
		//
		
		private Bullet newBulletAsEntity(Player player) {
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

		public Bullet shot(Player player, FigureWithWeapon fig, double angle, float velocity, GameplayState state) {
			player.countANewShot();
			Bullet projectile = newBulletAsEntity(player);
			//projectile.setPosition(fig.getPosition());
			projectile.setGravity(state.gravity);
			projectile.setGameplayState(state);
			projectile.addEvents();
			projectile.setPosX0(fig.getPosition().x);
			projectile.setPosY0(fig.getPosition().y);
			System.out.println("Shot with Angle " + angle + " and velocity " + velocity + "from x0=" + fig.getPosition().x + " and y0=" + fig.getPosition().y);
			projectile.setVelocity(angle, velocity);
			
			
			//LoopEvent loop = new LoopEvent();
			//Action a = new MoveRightAction(0.25f);
			//loop.addAction(a);
			//projectile.addComponent(loop);
			projectileCounter++;
			setProjectileCounter(projectileCounter);
			return projectile;
		}


		public double getGravityInput() {
			return gravityInput;
		}


		public void setGravityInput(double gravityInput) {
			this.gravityInput = gravityInput;
		}
}
