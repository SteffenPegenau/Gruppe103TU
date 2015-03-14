package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import de.tu_darmstadt.gdi1.gorillas.weapons.BananaThrower;


public class Gorilla extends FigureWithWeapon {
	protected final static String FIGURE_IMAGE = "/assets/gorillas/gorillas/gorilla.png";
	
	
	public Gorilla(String entityID) {
		super(entityID);
		setFigureImage(FIGURE_IMAGE);
		setWeapon(new BananaThrower());
	}
	
}
