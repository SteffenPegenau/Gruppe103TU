package de.tu_darmstadt.gdi1.gorillas.comments;

public enum Hit {
	TREFFER,
	SITZT,
	WEH,
	GIB;
	
	public static Hit getRandom(){
		return values()[(int) (Math.random() * values().length)];
	}
}
