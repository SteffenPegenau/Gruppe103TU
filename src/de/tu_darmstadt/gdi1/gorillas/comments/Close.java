package de.tu_darmstadt.gdi1.gorillas.comments;

public enum Close {
	ZIELWASSER,
	BRILLE,
	SCHIELEN,
	KNAPP,
	DOSE;
	
	public static Close getRandom(){
		return values()[(int) (Math.random() * values().length)];
	}
}
