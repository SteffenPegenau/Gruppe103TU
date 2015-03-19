package de.tu_darmstadt.gdi1.gorillas.comments;

public class EnumToString {
	ToShort toShort;
	FarOff farOff;
	Close close;
	Hit hit;
	
	public ToShort initToShort(){
		return toShort = ToShort.getRandom();
	}
	
	public FarOff initFarOff(){
		return farOff = FarOff.getRandom();
	}
	
	public Close initClose(){
		return close = Close.getRandom();
	}
	
	public Hit initHit(){
		return hit = Hit.getRandom();
	}
	
	public String printToShort(){
		switch (initToShort()) {
		case ROHRKREPIERER:
			return "Was für ein Rohrkrepierer!";
		case VERHUNGERT:
			return "Der ist verhungert...";
		case SELBST:
			return "Wolltest du dich selbst treffen?";
		case BABY:
			return "Selbst ein Baby wirft weiter!";
		}
		return "";
	}
	
	public String printFarOff(){
		switch (initFarOff()) {
		case GALAXY:
			return "Meine Kollegin in der Nachbargalaxy \n hatte Angst getroffen zu werden!";
		case FLIEGER:
			return "Flieger, grüß mir die Sonne...";
		case HOMERUN:
			return "HOMERUN! Ach Mist, andere \n Baustelle.";
		case FEST:
			return "Wenn nichts mehr geht, \n fest geht immer, nicht wahr?";
		}
		return "";
	}
	
	public String printClose(){
		switch (initClose()) {
		case ZIELWASSER:
			return "Nimm 'nen Schluck Zielwasser,\n dann sitzt der nächste.";
		case BRILLE:
			return "Du solltest deine Brille richten, \n Kollege.";
		case KNAPP:
			return "Knapp vorbei ist auch daneben!";
		case SCHIELEN:
			return "Schielst du?";
		case DOSE:
			return "Eine Dose Mitleid...";
		}
		return "";
	}
	
	public String printHit(){
		switch (initHit()) {
		case TREFFER:
			return "Treffer, versenkt.";
		case SITZT:
			return "Der hat gesessen!";
		case WEH:
			return "Das tat bestimmt weh...";
		case GIB:
			return "Gib's ihm!";
		}
		return "";
	}
}
