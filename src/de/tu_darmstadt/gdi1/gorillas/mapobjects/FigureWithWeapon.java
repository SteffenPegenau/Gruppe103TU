package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.component.render.ImageRenderComponent;



/**
 * Class FigureWithWeapon
 */
public class FigureWithWeapon extends MapObject {
		

		protected final static String FIGURE_IMAGE = null;
		protected Image image = null;
		//
		// Fields
		//

		protected Weapon weapon;
		private Weapon selectedWeapon;
		private double velocity;
		private double angle;
		
		public FigureWithWeapon(String entityID) {
			super(entityID);
			
		}
		
		public void setFigureImage(String pathToImage) {
			try {
				//System.out.println("Figure Image: " + pathToImage);
				image = new Image(pathToImage);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			this.addComponent(new ImageRenderComponent(image));
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
