/**
 * 
 */
package de.tu_darmstadt.gdi1.gorillas.ui.states;

/**
 * @author Steffen Pegenau (steffen.pegenau@gmail.com)
 * Speichert zur einfachen Ausrichtung von Elementen moegl. y Werte im Bereich 50 - 550
 */
public enum posY {
	A (50),
	B (75),
	C (100), 
	D (125),
	E (150),
	F (175),
	G (200),
	H (225),
	I (250),
	J (275),
	K (300),
	L (325),
	M (350), 
	N (375),
	O (400),
	P (425),
	Q (450),
	R (475),
	S (500),
	T (525),
	U (550);
	
private final int Y;
	
	posY(int y) {
		Y = y;
	}
	
	public int get() {
		return Y;
	}
}