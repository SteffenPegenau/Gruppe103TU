package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.gorillas.weapons.Weapon;



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
