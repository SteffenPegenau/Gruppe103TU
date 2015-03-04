package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.DestructibleImageEntity;

/**
 * Class Building
 */
public class Building {

	List<BufferedImage> BufferedImages;
	List<Graphics2D> BuildingList;
	List<Color> BuildingColors;
	List<DestructibleImageEntity> DestructerList;

	double RandomNr = Math.random();
	BufferedImage imageX;
	Graphics2D buildingX;
	DestructibleImageEntity skyScraperX;

	public void imageSetter(int width, int height, int imageType) {
		BufferedImages = new LinkedList<BufferedImage>();

		imageX = new BufferedImage(width, height, imageType);
		BufferedImage i = imageX;
		BufferedImages.add(i);

	}
	
	public void buildingGraphicsSetter(List<BufferedImage> BuffImag, int x, int y, int width, int height) {
		for (BufferedImage image : BuffImag) {
			buildingX = image.createGraphics();
			buildingX.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
			buildingX.setColor(BuildingColors.get((int) (10 * RandomNr))); // Müssen 0 bis 9 Farben übergeben werden sonst Laufzeitfehler!
			buildingX.fillRect(x, y, width, height); // Richtig Ausfüllen!
			Graphics2D building = buildingX;
			BuildingList.add(building);
		}
	}
	
	public void destructableImageEntityAdder(int index, float x, float y) {
		DestructibleImageEntity skyScraper;
		skyScraperX = new DestructibleImageEntity("obstracle", BufferedImages.get(index), "gorillas/destruction.png", false);
		skyScraperX.setPosition(new Vector2f(x, y)); // Position setzen
		skyScraper = skyScraperX;
		DestructerList.add(skyScraper);
		
	}

	public void colorAdder(Color c) {
		BuildingColors.add(c);
	}
	
}
