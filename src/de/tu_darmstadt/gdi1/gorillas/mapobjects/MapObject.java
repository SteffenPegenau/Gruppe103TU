package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import java.util.Random;

import eea.engine.entity.Entity;

/**
 * Class MapObject
 */
public class MapObject extends Entity {
	Entity entity;

	public MapObject(String entityID) {
		super(entityID);
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
