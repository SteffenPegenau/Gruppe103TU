package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

public class Skyline {
	// TODO Anderes Hintergrundbild, nicht das von Drop of Water...
	protected final static String BACKGROUND = "/assets/dropofwater/background.png";

	protected StateBasedEntityManager entityManager;
	protected int stateID;
	
	protected FigureWithWeapon[] playerFigures;
	protected Building[] buildings;
	protected boolean buildingsWithRandomWidth;

	/**
	 * Gibt Entität mit Hintergrundbild (definiert in Klasse) zurück
	 * 
	 * @return Hintergrundbild
	 */
	public Entity getBackgroundEntity() {
			Entity background = new Entity("background"); // Entität für Hintergrund
			background.setPosition(new Vector2f(400, 300)); 
			Image bgImage = null;
			try {
				bgImage = new Image(BACKGROUND);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			background.addComponent(new ImageRenderComponent(bgImage));
			return background;
		}

	public Skyline(StateBasedEntityManager entityManager, int stateID, int numberOfBuildings, boolean buildingsWithRandomWidth) {
		this.entityManager = entityManager;
		this.stateID = stateID;
		this.buildingsWithRandomWidth = buildingsWithRandomWidth;
		buildings = new Building[numberOfBuildings];
	};
	
	public void createSkyline() {
		int widthUsedByBuildings = 0;
		
		for (int i = 0; i < buildings.length; i++) {
			buildings[i] = new Building("building" + i, widthUsedByBuildings, -1, (int) Gorillas.FRAME_WIDTH / buildings.length, null);
			widthUsedByBuildings += buildings[i].getWidth();
			entityManager.addEntity(stateID, buildings[i].asDestructibleImageEntity());
		}
	}

}
