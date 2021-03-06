package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.test.setup.TestGorillas;
import eea.engine.entity.DestructibleImageEntity;

/**
 * Class Building
 */
public class Building extends MapObject {
	// WIRD NUR BEI ZUFÄLLIGER HÖHE BEACHTET! SIEHE KONSTRUKTOR
	public final static int MAX_HEIGHT = Gorillas.FRAME_HEIGHT - 100;
	public final static int MIN_HEIGHT = 100;

	public final static Color[] BASIC_COLORS = { new Color(143, 210, 90),
			new Color(177, 115, 176), new Color(70, 70, 70),
			new Color(47, 183, 145), new Color(240, 21, 25),
			new Color(10, 18, 36), new Color(215, 190, 21),
			new Color(10, 131, 36) };

	public final static String DESTRUCTION_PATH = "gorillas/destruction.png";
	public final static boolean DEBUG = Gorillas.debug;

	private Vector2f position;

	private float height;
	private float width;

	private Color color;

	BufferedImage image;
	Graphics2D building;

	/**
	 * Erzeugt ein Hochhaus
	 * 
	 * @param entityID
	 * @param posX
	 *            linke Kante
	 * @param height
	 *            , wenn height < 0, wird zufällige Höhe g
	 * @param width
	 *            , KEIN Zufall
	 * @param color
	 *            , Farbe der Klasse Color. Falls keine Farbe übergeben wird,
	 *            wird Farbe zufällig gewählt
	 */
	public Building(String entityID, float posX, float height, float width,
			Color color) {
		super(entityID);

		// Setze Höhe
		if (height < 0) {
			this.height = getRandomHeight();
		} else {
			this.height = height;
		}

		// Setze Breite
		this.width = width;

		// Farbe auswerten
		if (color != null && color.getClass() == Color.class) {
			this.color = color;
		} else {
			this.color = randomColor();
		}

		// Koordinatensystem des Vektors hat in der oberen, linken Ecke den
		// Ursprung
		float posXNew = posX + width / 2;
		float posYNew = Gorillas.FRAME_HEIGHT - height / 2;

		position = new Vector2f(posXNew, posYNew);

		createBuilding();
	}

	public float getWidth() {
		return width;
	}

	/**
	 * Gibt Gebäude als zerstörbare Entität zurück
	 * 
	 * @return DestructibleImageEntity
	 */
	public DestructibleImageEntity asDestructibleImageEntity() {
		DestructibleImageEntity entity = new DestructibleImageEntity(
				this.getID(), image, DESTRUCTION_PATH, DEBUG);
		entity.setPosition(position);
		return entity;
	}

	/**
	 * Erzeugt Gebäude
	 */
	private void createBuilding() {
		if (width > 0 && height > 0) {
			image = new BufferedImage((int)width, (int)height,
					BufferedImage.TYPE_INT_ARGB);
			if (!TestGorillas.debug) {
				building = image.createGraphics();
				building.setComposite(AlphaComposite
						.getInstance(AlphaComposite.SRC));
				building.setColor(this.color);
				building.fillRect(0, 0, (int)width, (int)height);
			}
		}
	}

	/**
	 * Nimmt eine zufällige Farbe aus dem in der Klasse definierten Pool
	 * 
	 * @return Color, zufällige Farbe
	 */
	public Color randomColor() {
		return BASIC_COLORS[randomInt(0, BASIC_COLORS.length - 1)];
	}

	private int getRandomHeight() {
		int randomHeight = 1;
		// Ungerade Hoehen sind doof (beim Teilen durch 2 kommt es zu cast-fehlern) => nur gerade gehen durch!
		while(randomHeight % 2 != 0) {
			randomHeight = randomInt(MIN_HEIGHT, MAX_HEIGHT);
		}
		return randomHeight;
	}

	public Vector2f getPosition() {
		return position;
	}
	
	/**
	 * Gibt Vektor auf obere linke Ecke zurueck
	 * @return Vector2f
	 */
	public Vector2f getPositionLeftUpperCorner() {
		Vector2f centerPosition = getPosition();
		float width = getWidth();
		float height = this.height;
		float two = 2.0f;
		float x = centerPosition.x - width / two;
		float y = centerPosition.y - height / 2;
		return new Vector2f(x, y);
	}

	public float getHeight() {
		return height;
	}

}
