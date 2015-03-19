package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import de.tu_darmstadt.gdi1.gorillas.test.setup.TestGorillas;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.DestructibleImageEntity;
import eea.engine.entity.Entity;

/**
 * Class MapObject
 */
public class MapObject extends Entity {
	protected final static String FIGURE_IMAGE = null;
	protected Image image = null;
	
	public MapObject(String entityID) {
		super(entityID);
	}
	
	public void log(String msg) {
		System.out.println(this.getClass().getSimpleName() + ": " + msg);
	}

	
	public void setFigureImage(String pathToImage) {
		if(!TestGorillas.debug) {
			try {
				//System.out.println("Figure Image: " + pathToImage);
				image = new Image(pathToImage);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			this.addComponent(new ImageRenderComponent(image));
		}
	}
	
	/**
	 * Erzeugt zufällige ganze Zahl zwischen min und max
	 * 
	 * @param min Minimum
	 * @param max Maximum
	 * @return Zufallszahl
	 */
	public static int randomInt(int min, int max) {

		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

}
