package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.weapons.Weapon;
import eea.engine.entity.DestructibleImageEntity;



/**
 * Class FigureWithWeapon
 */
public class FigureWithWeapon extends MapObject {
		//
		// Fields
		//
		protected Weapon weapon;
		private double velocity;
		private double angle;
		
		public final static String DESTRUCTION_PATH = "gorillas/destruction.png";
		public final static boolean DEBUG = Gorillas.debug;
		BufferedImage image;
		Graphics2D building;
		
		public FigureWithWeapon(String entityID) {
			super(entityID);
			
		}
				
		private Vector2f positionOnBuilding(Building building) {
			Vector2f positionBuilding = building.getPosition();
			float posX = positionBuilding.getX();
			float posY = positionBuilding.getY();
			posY -= building.getHeight() / 2 + image.getHeight() / 2;
			System.out.println("Set position: " + posX + " | " + posY);
			return new Vector2f(posX, posY);
			
		}
		
		public void setPosition(Building building) {
			super.setPosition(positionOnBuilding(building));
		}
		//
		// Methods
		//
		
		public DestructibleImageEntity asDestructibleImageEntity() {
			int width = 42;
			int height = 35;
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			DestructibleImageEntity entity = new DestructibleImageEntity(
					this.getID(), image, DESTRUCTION_PATH, DEBUG);
			entity.setPosition(this.getPosition());
			return entity;
		}

		//
		// Accessor methods
		//

		/**
		 * Set the value of weapon
		 * @param newVar the new value of weapon
		 */
		protected void setWeapon (Weapon newVar) {
				weapon = newVar;
		}

		/**
		 * Get the value of weapon
		 * @return the value of weapon
		 */
		public Weapon getWeapon () {
				return weapon;
		}


		/**
		 * Set the value of velocity
		 * @param newVar the new value of velocity
		 */
		private void setVelocity (double newVar) {
				velocity = newVar;
		}

		/**
		 * Get the value of velocity
		 * @return the value of velocity
		 */
		private double getVelocity () {
				return velocity;
		}

		/**
		 * Set the value of angle
		 * @param newVar the new value of angle
		 */
		private void setAngle (double newVar) {
				angle = newVar;
		}

		/**
		 * Get the value of angle
		 * @return the value of angle
		 */
		private double getAngle () {
				return angle;
		}
		

		//
		// Other methods
		//

}
