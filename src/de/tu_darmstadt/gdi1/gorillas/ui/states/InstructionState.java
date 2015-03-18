package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import eea.engine.action.basicactions.QuitAction;


 
 
public class InstructionState extends ExtendedTWLState {
	public InstructionState(int sid) {
		super(sid);
	}

	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);
		entityManager.addEntity(stateID, setBackground(DEFAULT_BACKGROUND));
		entityManager.renderEntities(container, game, g);
		
		g.drawString("Instruktionen", 350, 0);
		g.drawString("1. Um das Spiel zu starten klicken auf 'Neues Spiel mit Setup'.", 5, 130);
		g.drawString("2. Nach der Auswahl der Spieler und dem Eingeben der RunderAnzahl klicke auf ", 5, 155);
		g.drawString("'Spiel starten' um zu starten.", 5, 170);
		g.drawString("3. Oben links und Oben rechts sind jeweils zwei kästchen mit der Beschriftung  ", 5, 195);
		g.drawString("Winkel und Geschwindigkeit. Gebe beides ein und Klicke auf 'Wurf!'.", 5, 210);
		g.drawString("4. Ziel des Spiels ist es den gegnerischen Affen mit der Banane zu treffen.", 5, 235);
		g.drawString("5. Pro getroffene Banane wird dem Gegner ein Leben abgezogen.", 5, 260);
		g.drawString("6. Der Spieler der zuerst 0 leben hat, hat verloren.", 5, 285);
		g.drawString("Viel Spaß beim Spielen wünscht euch Gruppe103! ;) ", 200, 330);
		g.drawString("Exit", 500, 530);
		entityManager.addEntity(stateID, setESCListener(Gorillas.MAINMENUSTATE));
	}

	
	 // In dieser Methode werden in einem BasicTWLGameSate alle GUI-Elemente dem
	 // GameState mit Hilfe einer RootPane hinzugef�gt
	 
	protected RootPane createRootPane() {
		// erstelle die RootPane
		RootPane rp = super.createRootPane();
		return rp;
	}

}

