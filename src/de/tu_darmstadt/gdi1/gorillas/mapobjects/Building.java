package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import eea.engine.entity.DestructibleImageEntity;

/**
 * Class Building
 */
public class Building extends MapObject {
	// WIRD NUR BEI ZUFÄLLIGER HÖHE BEACHTET! SIEHE KONSTRUKTOR
	public final static int MAX_HEIGHT = Gorillas.FRAME_HEIGHT - 100;
	public final static int MIN_HEIGHT = 100;

	public final static Color[] BASIC_COLORS = { new Color(255, 0, 0),
			new Color(0, 0, 255), new Color(0, 255, 0), new Color(0, 255, 120),
			new Color(255, 255, 0), new Color(0, 255, 255),
			new Color(255, 0, 255), new Color(255, 160, 0) };

	public final static String DESTRUCTION_PATH = "gorillas/destruction.png";
	public final static boolean DEBUG = Gorillas.debug;
	
	private Vector2f position;

	private int height;
	private int width;

	private Color color;

	BufferedImage image;
	Graphics2D building;
	
	/**
	 * Erzeugt ein Hochhaus
	 * 
	 * @param entityID
	 * @param posX linke Kante
	 * @param height
	 *            , wenn height < 0, wird zufällige Höhe g
	 * @param width
	 *            , KEIN Zufall
	 * @param color
	 *            , Farbe der Klasse Color. Falls keine Farbe übergeben wird,
	 *            wird Farbe zufällig gewählt
	 */
	public Building(String entityID, int posX, int height, int width,
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
		
		// Koordinatensystem des Vektors hat in der oberen, linken Ecke den Ursprung
		posX = posX + (int) (width / 2);
		int posY = Gorillas.FRAME_HEIGHT - (int)(height / 2);
		
		position = new Vector2f(posX, posY);

		createBuilding();
	}

	public int getWidth() {
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
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		building = image.createGraphics();
		building.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		building.setColor(this.color);
		building.fillRect(0, 0, width, height);
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
		return randomInt(MIN_HEIGHT, MAX_HEIGHT);
	}
	


	public Vector2f getPosition() {
		return position;
	}

	public int getHeight() {
		return height;
	}
	
	

	/*
	
	public void imageSetter(int width, int height, int imageType) {
		BufferedImages = new LinkedList<BufferedImage>();

		imageX = new BufferedImage(width, height, imageType);
		BufferedImage i = imageX;
		BufferedImages.add(i);

	}

	public void buildingGraphicsSetter(List<BufferedImage> BuffImag, int x,
			int y, int width, int height) {
		for (BufferedImage image : BuffImag) {
			buildingX = image.createGraphics();
			buildingX.setComposite(AlphaComposite
					.getInstance(AlphaComposite.SRC));
			buildingX.setColor(BuildingColors.get((int) (10 * RandomNr))); // Müssen
																			// 0
																			// bis
																			// 9
																			// Farben
																			// übergeben
																			// werden
																			// sonst
																			// Laufzeitfehler!
			buildingX.fillRect(x, y, width, height); // Richtig Ausfüllen!
			Graphics2D building = buildingX;
			BuildingList.add(building);
		}
	}

	public void destructableImageEntityAdder(int index, float x, float y) {
		DestructibleImageEntity skyScraper;
		skyScraperX = new DestructibleImageEntity("obstracle",
				BufferedImages.get(index), "gorillas/destruction.png", false);
		skyScraperX.setPosition(new Vector2f(x, y)); // Position setzen
		skyScraper = skyScraperX;
		DestructerList.add(skyScraper);

	}

	public void colorAdder(Color c) {
		BuildingColors.add(c);
	}
	*/

}
