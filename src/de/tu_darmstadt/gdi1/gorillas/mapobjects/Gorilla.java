package de.tu_darmstadt.gdi1.gorillas.mapobjects;


public class Gorilla extends FigureWithWeapon {
	protected final static String FIGURE_IMAGE = "/assets/gorillas/gorillas/gorilla.png";

	public Gorilla(String entityID) {
		super(entityID);
		setFigureImage(FIGURE_IMAGE);
	}
}
