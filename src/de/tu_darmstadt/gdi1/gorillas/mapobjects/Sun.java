package de.tu_darmstadt.gdi1.gorillas.mapobjects;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.gorillas.test.setup.TestGorillas;


/**
 * Class Sun
 */
public class Sun extends MapObject{

		//
		// Fields
		//

	protected String figure_image = "/assets/gorillas/sun/sun_smiling.png";
	protected String collision_image = "/assets/gorillas/sun/sun_astonished.png";
	boolean astonished = false;
	
	protected int height = 48;
	protected int width = 48;

		
		//
		// Constructors
		//	
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Sun(String entityID) {
		super(entityID);
		setFigureImage(figure_image);
		
		// Fuer Tests notwendig (die gehen von einer 100x100 Sonne aus)
		if(TestGorillas.debug) {
			String sunImageForTests = "/assets/gorillas/sun/sun_tests.png";
			figure_image = sunImageForTests;
			collision_image = sunImageForTests;
		}
	}
		
		//
		// Methods
		//
	
	public void changeImage(){
			setFigureImage(collision_image);
			astonished = true;
		}
	

		//
		// Accessor methods
		//
	
	public void changeBackImage() {
		setFigureImage(figure_image);
		astonished = false;
	}

		//
		// Other methods
		//
	
	public Boolean getAstonished() {
		return astonished;
	}
	
	public void setPosition(int frameWidth) {
		float posY = getHeight() / 2 + 5;
		super.setPosition(new Vector2f(frameWidth / 2, posY));
	}

}
