package de.tu_darmstadt.gdi1.gorillas.mapobjectsowners;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
public class DancingGorilla {

	private Animation jubeln;
	
	public DancingGorilla(){
		
		
	}
	
	
	public void init() throws SlickException{
		
	Image[] dance = {new Image("/assets/gorillas/gorillas/gorilla_left_up.png"),
	new Image("/assests/gorillas/gorillas/gorilla_right_up.png")};
	
	jubeln = new Animation(dance,200,false);
	
	}	
		
	
	public void update(int delta) {
		jubeln.update(delta);
	}
	
	public void render (int x, int y) throws SlickException {
		jubeln.draw(x, y);
		
	}
	
	public int getWidth(){
		return jubeln.getWidth();
		
	}
		
	}

// danach aufrufen jubelnderGorilla = new DancingGorilla();
// in inti methode :
//jubelnderGorilla.init();
//in update:
//jubelnderGorilla.update(delta ( nimm einfach das int von der update));
//dann in render
// jubelnderGorilla.render(x,y koordinate ? not sure..check)
	
	
	
	
	
	
	
	
	

