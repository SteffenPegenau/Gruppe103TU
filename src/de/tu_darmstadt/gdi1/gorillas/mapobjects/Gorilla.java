package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import de.tu_darmstadt.gdi1.gorillas.weapons.BananaThrower;


public class Gorilla extends FigureWithWeapon {
	protected final static String FIGURE_IMAGE = "/assets/gorillas/gorillas/gorilla.png";
	
	
	public Gorilla(String entityID) {
		super(entityID);
		setFigureImage(FIGURE_IMAGE);
		
		URL input = null;
		try {
			input = new URL(FIGURE_IMAGE);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		File input = new File(FIGURE_IMAGE);

		try {
			image = ImageIO.read(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setWeapon(new BananaThrower());
		this.setPassable(false);
	}
//	
//	public DestructibleImageEntity asDestructibleImageEntity() {
//		DestructibleImageEntity entity = new DestructibleImageEntity(
//				this.getID(), image, Building.DESTRUCTION_PATH, Building.DEBUG);
//		entity.setPosition(position);
//		return entity;
//	}
}
