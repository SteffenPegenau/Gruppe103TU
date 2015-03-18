package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import de.tu_darmstadt.gdi1.gorillas.test.setup.TestGorillas;
import de.tu_darmstadt.gdi1.gorillas.ui.states.GameplayState;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

public class Skyline {
	// TODO Anderes Hintergrundbild, nicht das von Drop of Water...
	protected final static String BACKGROUND = "/assets/gorillas/backgrounds/Himmel.png";

	protected StateBasedEntityManager entityManager;
	protected int stateID;

	protected boolean skyline_built = false;

	protected int frameWidth = 0;
	protected int frameHeight = 0;

	protected Sun sun;
	protected Player[] players;
	protected FigureWithWeapon[] playerFigures = new FigureWithWeapon[2];
	protected Building[] buildings;
	protected boolean buildingsWithRandomWidth;
	
	protected int gorillaWidth;
	protected int gorillaHeight;

	GameplayState gameplayState;
	GameContainer container;
	StateBasedGame game;
	Graphics g;

	/**
	 * Gibt Entität mit Hintergrundbild (definiert in Klasse) zurück
	 * 
	 * @return Hintergrundbild
	 */
	private Entity getBackgroundEntity() {
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

	public Skyline(StateBasedEntityManager entityManager, int stateID,
			int numberOfBuildings, boolean buildingsWithRandomWidth) {
		this.entityManager = entityManager;
		this.stateID = stateID;

		this.buildingsWithRandomWidth = buildingsWithRandomWidth;
		buildings = new Building[numberOfBuildings];

	};

	public void createSkyline() {
		// Sonne setzen
		sun = new Sun("sun");
		sun.setPosition(new Vector2f(400, 33));
		
		if(!TestGorillas.debug) {
			// Hintergrund setzen
			entityManager.addEntity(stateID, getBackgroundEntity());
			entityManager.addEntity(stateID, sun);
		}

		

		// Hochhäuser erzeugen
		createBuildings();
	}

	public void createSkyline(ArrayList<Vector2f> buildingCoordinates) {
		// Hintergrund setzen
		entityManager.addEntity(stateID, getBackgroundEntity());

		// Sonne setzen
		sun = new Sun("sun");
		sun.setPosition(new Vector2f(400, 33));
		entityManager.addEntity(stateID, sun);

		// Hochhäuser erzeugen
		createBuildings(buildingCoordinates);
	}

	/**
	 * Löscht die Hochhäuser, erzeugt sie zufällig neu und platziert die Figuren
	 * 
	 */
	public void rebuildSkyline() {
		removeBuildingEntities();
		createBuildings();
	}

	/**
	 * Entfernt alle Gebäude aus dem EntityManager
	 */
	public void removeBuildingEntities() {
		String entityName;
		for (int i = 0; i < buildings.length; i++) {
			entityName = buildings[i].getID();
			// entityManager.getEntity(gameplayState.getID(),
			// entityName).setVisible(false);
			// entityManager.removeEntity(gameplayState.getID(), buildings[i]);
			// buildings[i].render(container, game, g);
		}
	}

	/**
	 * Erzeugt zufällige Gebäude und fügt sie der Skyline hinzu
	 */
	public void createBuildings() {
		if (frameWidth == 0) {
			frameWidth = Gorillas.FRAME_WIDTH;
		}
		// alle Gebäude setzen
		int widthUsedByBuildings = 0;
		for (int i = 0; i < buildings.length; i++) {
			buildings[i] = new Building("building" + i, widthUsedByBuildings,
					-1, frameWidth / buildings.length, null);
			widthUsedByBuildings += buildings[i].getWidth();
			if(!TestGorillas.debug) {
				entityManager.addEntity(stateID,
						buildings[i].asDestructibleImageEntity());
			}
			
		}
	}

	public void createBuildings(ArrayList<Vector2f> positions) {
		if (frameWidth == 0) {
			frameWidth = Gorillas.FRAME_WIDTH;
		}
		if (frameHeight == 0) {
			frameHeight = Gorillas.FRAME_HEIGHT;
		}
		int counter = 0;
		for (Vector2f pos : positions) {
			buildings[counter] = new Building("building" + counter,
					(int) pos.x, (int) ((frameHeight - pos.y) * 2), frameWidth
							/ buildings.length, null);
			entityManager.addEntity(stateID,
					buildings[counter].asDestructibleImageEntity());
			counter++;
		}
	}

	public Building getRandomBuilding(int minArrayIndex, int maxArrayIndex) {
		int random = MapObject.randomInt(minArrayIndex, maxArrayIndex);
		return buildings[random];
	}

	public Building randomBuildingForPlayer(int player) {
		if (player == 0) {
			int min = 0;
			int max = (2 > buildings.length) ? buildings.length : 2;
			return getRandomBuilding(min, max);
		} else {
			// the third last building, except the skyline is too small
			int min = (buildings.length - 3 < 0) ? 0 : buildings.length - 3;
			// last building, except size is zero...
			int max = (buildings.length - 1 < 0) ? 0 : buildings.length - 1;
			return getRandomBuilding(min, max);
		}

	}

	public boolean isSkyline_built() {
		return skyline_built;
	}

	public void setSkyline_built(boolean skyline_built) {
		this.skyline_built = skyline_built;
	}

	public void setContainer(GameContainer container) {
		this.container = container;
	}

	public void setGame(StateBasedGame game) {
		this.game = game;
	}

	public void setG(Graphics g) {
		this.g = g;
	}

	public void setGameplayState(GameplayState gameplayState) {
		this.gameplayState = gameplayState;
	}

	public int getFrameWidth() {
		return frameWidth;
	}

	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}

	public int getFrameHeight() {
		return frameHeight;
	}

	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}

	public ArrayList<Vector2f> getBuildingCoordinate() {
		ArrayList<Vector2f> coordinates = new ArrayList<Vector2f>();
		
		for (int i = 0; i < buildings.length; i++) {
			coordinates.add(buildings[i].getPosition());
		}
		return coordinates;
	}

	public FigureWithWeapon getFigureWithWeapon(int index) {
		return playerFigures[index];
	}

	public void setFigureWithWeapon(int index, FigureWithWeapon figure) {
		playerFigures[index] = figure;
	}

	public int getGorillaWidth() {
		return gorillaWidth;
	}

	public void setGorillaWidth(int gorillaWidth) {
		this.gorillaWidth = gorillaWidth;
	}

	public int getGorillaHeight() {
		return gorillaHeight;
	}

	public void setGorillaHeight(int gorillaHeight) {
		this.gorillaHeight = gorillaHeight;
	}

}
