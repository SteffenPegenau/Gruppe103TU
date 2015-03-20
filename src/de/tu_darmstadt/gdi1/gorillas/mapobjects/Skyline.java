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

	protected int yOffsetCity = 0;

	public int getyOffsetCity() {
		return yOffsetCity;
	}

	public void setyOffsetCity(int yOffsetCity) {
		this.yOffsetCity = yOffsetCity;
	}

	protected Sun sun;
	public Sun getSun() {
		return sun;
	}

	public void setSun(Sun sun) {
		this.sun = sun;
	}

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
		
		if(frameWidth == 0) {
			frameWidth = Gorillas.FRAME_WIDTH;
		}
		
		sun.setPosition(frameWidth);

		if (!TestGorillas.debug) {
			// Hintergrund setzen
			entityManager.addEntity(stateID, getBackgroundEntity());
			entityManager.addEntity(stateID, sun);
		}

		// Hochhäuser erzeugen
		createBuildings();
	}

	/**
	 * Generiert die Skyline anhand vorgegebener Koordinaten, die die linken
	 * oberen Ecken der Gebaeude beschreiben
	 * 
	 * @param buildingCoordinates
	 *            ArrayList der linken, oberen Eck/Vektoren der Gebaeude
	 */
	public void createSkyline(ArrayList<Vector2f> buildingCoordinates) {
		// Sonne erzeugen
		sun = new Sun("sun");
		sun.setPosition(new Vector2f(400, 33));

		// Entitaeten setzen (Sonne + Hintergrund)
		if (!TestGorillas.debug) {
			entityManager.addEntity(stateID, getBackgroundEntity());
			entityManager.addEntity(stateID, sun);
		}

		// Hochhäuser erzeugen
		createBuildings(buildingCoordinates);
	}

	/**
	 * Erzeugt zufällige Gebäude und fügt sie der Skyline hinzu
	 */
	public void createBuildings() {
		if (frameWidth == 0) {
			frameWidth = Gorillas.FRAME_WIDTH;
		}

		// Standard-Breite eines Gebaeudes
		int widthOfBuilding = (int) ((double) frameWidth / buildings.length);

		// alle Gebäude setzen
		int widthUsedByBuildings = 0;
		for (int i = 0; i < buildings.length; i++) {
			String name = "building" + i;
			buildings[i] = new Building(name, widthUsedByBuildings, -1,
					widthOfBuilding, null);
			widthUsedByBuildings += buildings[i].getWidth();
			if (!TestGorillas.debug) {
				entityManager.addEntity(stateID,
						buildings[i].asDestructibleImageEntity());
			}

		}
	}

	/**
	 * Erstellt Gebaeude anhand einer Liste von Vektoren, die die linke obere
	 * Ecke der Gebaeude angeben
	 * 
	 * @param positions
	 */
	public void createBuildings(ArrayList<Vector2f> positions) {
		if (frameWidth == 0) {
			frameWidth = Gorillas.FRAME_WIDTH;
		}
		if (frameHeight == 0) {
			frameHeight = Gorillas.FRAME_HEIGHT;
		}

		for (int i = 0; i < positions.size(); i++) {
			float width = 0;
			float x = positions.get(i).x;
			if (i + 1 < positions.size()) {
				// Die Breite des Gebauedes ist bis zum naechsten Gebaeude
				width = positions.get(i).x - x;
			} else {
				// Das letzte Gebaude => Breite bis zum Rand
				width = frameWidth - x;
			}

			Vector2f centerCoordinate = leftTopToCenterCoordinate(
					positions.get(i), width);
			int height = frameHeight - (int) positions.get(i).y;
			int centerX = (int) centerCoordinate.x;

			buildings[i] = new Building("building" + i, centerX, height, width,
					null);
			if (!TestGorillas.debug) {
				entityManager.addEntity(stateID,
						buildings[i].asDestructibleImageEntity());
			}
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

	public ArrayList<Vector2f> getBuildingLeftUpperCornerCoordinates() {
		ArrayList<Vector2f> coordinates = new ArrayList<Vector2f>();
		for (int i = 0; i < buildings.length; i++) {
			Building b = buildings[i];
			coordinates.add(b.getPositionLeftUpperCorner());
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

	/**
	 * Rechnet die Gebaeude-Position linke, obere Ecke in den Mittelpunkt des
	 * Gebaeudes um
	 * 
	 * ACHTUNG: frameHeight und frameWidth muss gesetzt sein!
	 * 
	 * @return Mittelpunkt des Gebaudes
	 */
	public Vector2f leftTopToCenterCoordinate(Vector2f leftTopCoordinate,
			float width) {
		float x = leftTopCoordinate.x + width / 2;
		float y = (frameHeight - leftTopCoordinate.y) / 2;
		return new Vector2f(x, y);
	}

	/**
	 * Rechnet die Gebaeude-Position (Mittelpunkt) des Gebaeudes um in linke,
	 * obere Ecke
	 * 
	 * ACHTUNG: frameHeight und frameWidth muss gesetzt sein!
	 * 
	 * @return Linke, obere Ecke des Gebaudes
	 */
	public Vector2f CenterToLeftTopCoordinate(Vector2f centerCoordinate,
			int width) {
		float x = centerCoordinate.x - ((float) width / 2);
		float y = frameHeight - 2 * (frameHeight - centerCoordinate.y);
		System.out.println("CenterX=" + centerCoordinate.x + " CenterY="
				+ centerCoordinate.y + "  =>  " + "CornerX=" + x + " CornerY="
				+ y + "(mit width=" + width + ", frameHeight=" + frameHeight
				+ ", yOffset=" + getyOffsetCity() + ")");
		return new Vector2f(x, y);
	}

}
