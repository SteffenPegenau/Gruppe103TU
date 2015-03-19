package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import java.awt.Graphics2D;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import de.tu_darmstadt.gdi1.gorillas.weapons.BananaThrower;
import de.tu_darmstadt.gdi1.gorillas.weapons.Weapon;



/**
 * Class FigureWithWeapon
 */
public class FigureWithWeapon extends MapObject {
		//
		// Fields
		//
		protected Weapon weapon = null;
		Player owner;
		
		public final static String DESTRUCTION_PATH = "gorillas/destruction.png";
		public final static boolean DEBUG = Gorillas.debug;
		Graphics2D building;
		
		int imageHeight = 0;
		int imageWidth = 0;
		
		public FigureWithWeapon(String entityID) {
			super(entityID);
			
		}
				
		private Vector2f positionOnBuilding(Building building) {
			Vector2f positionBuilding = building.getPosition();
			float posX = positionBuilding.getX();
			float posY = positionBuilding.getY();
			if(imageHeight == 0) {
				imageHeight = image.getHeight();
			}

			float newPosY = posY - (building.getHeight() /2 + imageHeight / 2);
			System.out.println("Setze G auf Gebaeude: "
					+ "gebX=" + posX
					+ " gebY=" + posY
					+ "\t=>\t"
					+ "G.x=" + posX
					+ " G.y=" + newPosY
					);
			//System.out.println("Set position: " + posX + " | " + posY);
			return new Vector2f(posX, newPosY);
			
		}
		
		public void setPosition(Building building) {
			super.setPosition(positionOnBuilding(building));
		}
		
		public void setPostion(Vector2f position) {
			super.setPosition(position);
		}

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
			if(weapon == null) {
				setWeapon(new BananaThrower());
				return weapon;
			} else {
				return weapon;
			}
		}

		public Player getOwner() {
			return owner;
		}

		public void setOwner(Player owner) {
			this.owner = owner;
		}

		public int getImageHeight() {
			return imageHeight;
		}

		public void setImageHeight(int imageHeight) {
			this.imageHeight = imageHeight;
		}

		public int getImageWidth() {
			return imageWidth;
		}

		public void setImageWidth(int imageWidth) {
			this.imageWidth = imageWidth;
		}
}
