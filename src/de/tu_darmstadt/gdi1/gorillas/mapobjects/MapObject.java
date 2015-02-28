package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Owner;



/**
 * Class MapObject
 */
public class MapObject {

		//
		// Fields
		//

		protected Owner owner;
		
		//
		// Constructors
		//
		public MapObject () { };
		
		//
		// Methods
		//


		//
		// Accessor methods
		//

		/**
		 * Set the value of owner
		 * @param newVar the new value of owner
		 */
		protected void setOwner (Owner newVar) {
				owner = newVar;
		}

		/**
		 * Get the value of owner
		 * @return the value of owner
		 */
		protected Owner getOwner () {
				return owner;
		}

		//
		// Other methods
		//

}
