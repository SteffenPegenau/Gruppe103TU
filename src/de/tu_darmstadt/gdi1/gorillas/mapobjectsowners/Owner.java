package de.tu_darmstadt.gdi1.gorillas.mapobjectsowners;

import de.tu_darmstadt.gdi1.gorillas.mapobjects.Building;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.FigureWithWeapon;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.Gorilla;



/**
 * Class Owner
 */
public class Owner {
	protected FigureWithWeapon playersFigure;

	public FigureWithWeapon getPlayersFigure() {
		return playersFigure;
	}

	public void setPlayersFigure(FigureWithWeapon playersFigure) {
		this.playersFigure = playersFigure;
	}
	
	public void setPlayersFigureToDefaultGorilla(String entityID) {
		playersFigure = new Gorilla(entityID);
	}
}
