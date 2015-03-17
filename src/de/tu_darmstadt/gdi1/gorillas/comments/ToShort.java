package de.tu_darmstadt.gdi1.gorillas.comments;

public enum ToShort {
	ROHRKREPIERER,
	VERHUNGERT,
	SELBST,
	BABY;
	
	public static ToShort getRandom(){
		return values()[(int) (Math.random() * values().length)];
	}
}
