package de.tu_darmstadt.gdi1.gorillas.mapobjectsowners;

import de.tu_darmstadt.gdi1.gorillas.mapobjects.FigureWithWeapon;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.Gorilla;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.MapObject;



/**
 * Class Owner
 */
public class Owner {
	protected FigureWithWeapon playersFigure = null;

	public FigureWithWeapon getPlayersFigure() {
		if(playersFigure == null) {
			setPlayersFigureToDefaultGorilla(String.valueOf(MapObject.randomInt(1, 99999999)));
		}
		return playersFigure;
	}

	public void setPlayersFigure(FigureWithWeapon playersFigure) {
		this.playersFigure = playersFigure;
	}
	
	public void setPlayersFigureToDefaultGorilla(String entityID) {
		playersFigure = new Gorilla(entityID);
	}
}
