package de.tu_darmstadt.gdi1.gorillas.mapobjects;



/**
 * Class FigureWithWeapon
 */
public class FigureWithWeapon {

		//
		// Fields
		//

		protected Weapon weapon;
		private Weapon selectedWeapon;
		private double velocity;
		private double angle;
		
		//
		// Constructors
		//
		public FigureWithWeapon () { };
		
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
		protected Weapon getWeapon () {
				return weapon;
		}

		/**
		 * Set the value of selectedWeapon
		 * @param newVar the new value of selectedWeapon
		 */
		private void setSelectedWeapon (Weapon newVar) {
				selectedWeapon = newVar;
		}

		/**
		 * Get the value of selectedWeapon
		 * @return the value of selectedWeapon
		 */
		private Weapon getSelectedWeapon () {
				return selectedWeapon;
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
