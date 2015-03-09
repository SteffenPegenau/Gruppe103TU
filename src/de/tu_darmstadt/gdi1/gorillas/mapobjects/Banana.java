package de.tu_darmstadt.gdi1.gorillas.mapobjects;

public class Banana extends Bullet {

	protected final static String FIGURE_IMAGE = "assets/gorillas/banana.png";
	
	public Banana(String entityID) {
		super(entityID);
		setFigureImage(FIGURE_IMAGE);
	}
}
