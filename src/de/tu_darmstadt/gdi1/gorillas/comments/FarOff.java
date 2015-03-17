package de.tu_darmstadt.gdi1.gorillas.comments;

public enum FarOff {
	GALAXY,
	FLIEGER,
	HOMERUN,
	FEST;
	
	public static FarOff getRandom(){
		return values()[(int) (Math.random() * values().length)];
	}
}
