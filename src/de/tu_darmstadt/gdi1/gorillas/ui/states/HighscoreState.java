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
public class HighscoreState extends ExtendedTWLState {
	public HighscoreState(int sid) {
		super(sid);
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		entityManager.addEntity(stateID, setBackground(DEFAULT_BACKGROUND));
		entityManager.renderEntities(container, game, g);
		g.drawString("Exit", 500, 530);
		
		entityManager.addEntity(stateID, setESCListener(Gorillas.MAINMENUSTATE));
	}

	/**
	 * In dieser Methode werden in einem BasicTWLGameSate alle GUI-Elemente dem
	 * GameState mit Hilfe einer RootPane hinzugef�gt
	 */
	protected RootPane createRootPane() {
		RootPane rp = super.createRootPane();
		return rp;
	}

}
