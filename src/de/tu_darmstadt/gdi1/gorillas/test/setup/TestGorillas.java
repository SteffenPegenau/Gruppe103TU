package de.tu_darmstadt.gdi1.gorillas.test.setup;

import java.net.URL;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import de.tu_darmstadt.gdi1.gorillas.ui.states.GameSetupState;
import de.tu_darmstadt.gdi1.gorillas.ui.states.HighscoreState;
import de.tu_darmstadt.gdi1.gorillas.ui.states.InstructionState;
import de.tu_darmstadt.gdi1.gorillas.ui.states.MainMenuState;
import de.tu_darmstadt.gdi1.gorillas.ui.states.OptionState;
import eea.engine.entity.StateBasedEntityManager;

/**
 * 
 * This class mainly does the same as Gorillas does, but it extends
 * TWLTestStateBasedGame instead of TWLStateBasedGame. TestGorillas is used for
 * testing the Gorillas game; TWLTestStateBasedGame does not do any rendering in
 * contrast to TWLStateBasedGame.
 * 
 * @author Peter Kloeckner, Sebastian Fach
 * 
 */
public class TestGorillas extends TWLTestStateBasedGame {

	// Each state is represented by an integer value
	public static final int MAINMENUSTATE = 0;
	public static final int GAMESETUPSTATE = 1;
	public static final int GAMEPLAYSTATE = 2;
	public static final int HIGHSCORESTATE = 3;
	public static final int OPTIONSTATE = 4;
	public static final int INSTRUCTIONSSTATE = 5;

	public static boolean debug = false;

	public TestGorillas(boolean debug) {
		super("Gorillas");
		setDebug(debug);
	}

	public static void setDebug(boolean debuging) {
		debug = debuging;
	}

	@Override
	public void initStatesList(GameContainer gameContainer)
			throws SlickException {

		// Add states to the StateBasedGame
		this.addState(new MainMenuState(MAINMENUSTATE));
		this.addState(new GameSetupState(GAMESETUPSTATE));
		this.addState(new HighscoreState(HIGHSCORESTATE));
		this.addState(new InstructionState(INSTRUCTIONSSTATE));
		this.addState(new OptionState(OPTIONSTATE));
		//this.addState(new GameplayState(GAMEPLAYSTATE));
		//this.addState(new PlayerSelectState(PLAYERSELECTSTATE));
		
		// Add states to the StateBasedEntityManager
		StateBasedEntityManager.getInstance().addState(MAINMENUSTATE);
		StateBasedEntityManager.getInstance().addState(GAMESETUPSTATE);
		StateBasedEntityManager.getInstance().addState(HIGHSCORESTATE);
		StateBasedEntityManager.getInstance().addState(INSTRUCTIONSSTATE);
		StateBasedEntityManager.getInstance().addState(OPTIONSTATE);
		//StateBasedEntityManager.getInstance().addState(GAMEPLAYSTATE);
		//StateBasedEntityManager.getInstance().addState(PLAYERSELECTSTATE);
	}

	@Override
	protected URL getThemeURL() {
		return getClass().getResource("/theme.xml");
	}
}