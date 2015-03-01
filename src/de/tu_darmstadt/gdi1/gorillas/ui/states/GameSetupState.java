package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.ColumnLayout.Panel;
import de.matthiasmann.twl.Container;
import de.matthiasmann.twl.Menu;
import de.matthiasmann.twl.MenuElement;
import de.matthiasmann.twl.MenuManager;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import eea.engine.entity.StateBasedEntityManager;


/**
 * @author Steffen Pegenau (steffen.pegenau@gmail.com)
 *
 */
public class GameSetupState extends BasicTWLGameState {
	private int stateID;
	private StateBasedEntityManager entityManager;
	
	Player players[] = new Player[2];
	Container player1;
	Container player2;
	
	public GameSetupState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		entityManager.addEntity(stateID, MainMenuState.background());
		
		players[0] = null;
		players[1] = null;
		
		player1 = Player.getPlayerSelectContainer(players[0], 100, 200);
		player1.setPosition(200, 200);

	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		entityManager.renderEntities(container, game, g);
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		entityManager.updateEntities(container, game, delta);
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	@Override
	public int getID() {
		return stateID;
	}
	
	protected void layoutRootPane() {

		//int paneHeight = this.getRootPane().getHeight();
		//int paneWidth = this.getRootPane().getWidth();

	}
	

	/**
	 * In dieser Methode werden in einem BasicTWLGameSate alle GUI-Elemente dem
	 * GameState mit Hilfe einer RootPane hinzugef�gt
	 */
	protected RootPane createRootPane() {
		// erstelle die RootPane
		RootPane rp = super.createRootPane();
		rp.add(player1);
		return rp;
	}

}
