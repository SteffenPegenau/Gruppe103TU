package de.tu_darmstadt.gdi1.gorillas.mapobjects;



/**
 * Class Weapon
 */
public class Weapon {

		//
		// Fields
		//

		protected int ammunition;
		
		//
		// Constructors
		//
		public Weapon () { };
		
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

}
