package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.gorillas.weapons.BananaThrower;
import eea.engine.entity.DestructibleImageEntity;


public class Gorilla extends FigureWithWeapon {
	protected final static String FIGURE_IMAGE = "/assets/gorillas/gorillas/gorilla.png";
	
	BufferedImage image;
	Graphics2D gorilla;
	Vector2f position;
	
	public Gorilla(String entityID) {
		super(entityID);
		setFigureImage(FIGURE_IMAGE);
		setWeapon(new BananaThrower());
	}
//	
//	public DestructibleImageEntity asDestructibleImageEntity() {
//		DestructibleImageEntity entity = new DestructibleImageEntity(
//				this.getID(), image, Building.DESTRUCTION_PATH, Building.DEBUG);
//		entity.setPosition(position);
//		return entity;
//	}
}
