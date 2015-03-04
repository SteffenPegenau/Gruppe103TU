/**
 * 
 */
package de.tu_darmstadt.gdi1.gorillas.ui.states;

/**
 * @author Steffen Pegenau (steffen.pegenau@gmail.com)
 *	Speichert zur einfachen Ausrichtung von Elementen x Werte im Bereich 50 - 750
 */
public enum posX {
	A (50),
	B (100),
	C (150), 
	D (200),
	E (250),
	F (300),
	G (350),
	H (400),
	I (450),
	J (500),
	K (550),
	L (600),
	M (650),
	N (700),
	O (750);
	
	private final int X;
	
	posX(int x) {
		X = x;
	}
	
	public int get() {
		return X;
	}
	
	
}
