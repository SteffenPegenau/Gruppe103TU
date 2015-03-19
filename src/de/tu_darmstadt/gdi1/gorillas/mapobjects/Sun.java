package de.tu_darmstadt.gdi1.gorillas.mapobjects;


/**
 * Class Sun
 */
public class Sun extends MapObject{

		//
		// Fields
		//

	protected final static String FIGURE_IMAGE = "/assets/gorillas/sun/sun_smiling.png";
	protected final static String COLLISION_IMAGE = "/assets/gorillas/sun/sun_astonished.png";
	boolean astonished = false;

		
		//
		// Constructors
		//	
	
	public Sun(String entityID) {
		super(entityID);
		setFigureImage(FIGURE_IMAGE);
	}
		
		//
		// Methods
		//
	
	public void changeImage(){
			setFigureImage(COLLISION_IMAGE);
			astonished = true;
		}
	

		//
		// Accessor methods
		//
	
	public void changeBackImage() {
		setFigureImage(FIGURE_IMAGE);
		astonished = false;
	}

		//
		// Other methods
		//
	
	public Boolean getAstonished() {
		return astonished;
	}

}
