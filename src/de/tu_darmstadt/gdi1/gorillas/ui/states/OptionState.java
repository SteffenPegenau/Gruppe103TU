package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;

/**
 * @author Steffen Pegenau (steffen.pegenau@gmail.com)
 *
 */
public class OptionState extends ExtendedTWLState {
	public OptionState(int sid) {
		super(sid);
	}	
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);
		entityManager.renderEntities(container, game, g);
		entityManager.addEntity(stateID, setBackground(null));
		g.drawString("Exit", 500, 530);
		
		addESCListener(Gorillas.MAINMENUSTATE);
	}

	/**
	 * In dieser Methode werden in einem BasicTWLGameSate alle GUI-Elemente dem
	 * GameState mit Hilfe einer RootPane hinzugef�gt
	 */
	protected RootPane createRootPane() {
		// erstelle die RootPane
		RootPane rp = super.createRootPane();
		return rp;
	}
}
