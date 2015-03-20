package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import de.tu_darmstadt.gdi1.gorillas.test.setup.TestGorillas;
import de.tu_darmstadt.gdi1.gorillas.weapons.BananaThrower;
import de.tu_darmstadt.gdi1.gorillas.weapons.Weapon;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.DestructibleImageEntity;


/**
 * Class FigureWithWeapon
 */
public class FigureWithWeapon extends DestructibleImageEntity {
		protected Weapon weapon = null;
		Player owner;
		
		public final static String DESTRUCTION_PATH = "gorillas/destruction.png";
		public final static boolean DEBUG = Gorillas.debug;
		Graphics2D building;
		

		//protected static BufferedImage image = new BufferedImage(37, 42, BufferedImage.TYPE_INT_ARGB);
		protected static BufferedImage image =new BufferedImage(37, 42, BufferedImage.TYPE_INT_ARGB);
		protected Graphics2D gorilla;
		protected Vector2f position;
		
		int imageHeight = 0;
		int imageWidth = 0;
		
		public FigureWithWeapon(String entityID) {
			this(entityID, image, DESTRUCTION_PATH, TestGorillas.debug);
		}
		
		private FigureWithWeapon(String entityID, BufferedImage image,
				String destructionPath, boolean debug) {
			super(entityID, image, destructionPath, debug);
		}
		
		public void setFigureImage(String pathToImage) {
			if(!TestGorillas.debug) {
				Image i = null;
				try {
					//System.out.println("Figure Image: " + pathToImage);
					i = new Image(pathToImage);
				} catch (SlickException e) {
					e.printStackTrace();
				}
				this.addComponent(new ImageRenderComponent(i));
			}
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

		@Override
		public String toString() {
			return "Figure of " + owner + " at pos " + this.getPosition();
		}
		
		
}
