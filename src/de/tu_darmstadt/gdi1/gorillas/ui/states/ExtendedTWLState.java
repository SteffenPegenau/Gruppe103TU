/**
 * 
 */
package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

/**
 * @author Steffen Pegenau (steffen.pegenau@gmail.com)
 *
 */
public class ExtendedTWLState extends BasicTWLGameState {
	protected int stateID;
	protected StateBasedEntityManager entityManager;

	protected final int distance = 200;
	protected final int startPosition = 90;

	private final static int BUTTON_DEFAULT_WIDTH = 120;
	private final static int BUTTON_DEFAULT_HEIGHT = 40;

	public final static int BUTTON_LAST_LINE_Y = 525;
	public final static int BUTTON_RIGHT_X = 625;
	public final static int BUTTON_LEFT_X = 50;
	public final static int BUTTON_MIDDLELEFT_X = 150;
	
	
	protected final static String DEFAULT_BACKGROUND = "/assets/gorillas/backgrounds/gorilla_face.png";

	Map<String, Widget> widgets = new HashMap<String, Widget>();

	protected RootPane rootPane = null;
	protected StateBasedGame game = null;
	protected GameContainer container = null;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		entityManager.addEntity(stateID, setBackground(null));
		this.game = game;
		this.container = container;

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		entityManager.renderEntities(container, game, g);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		entityManager.updateEntities(container, game, delta);

	}

	@Override
	public int getID() {
		return stateID;
	}
	
	/**
	 * In dieser Methode werden in einem BasicTWLGameSate alle GUI-Elemente dem
	 * GameState mit Hilfe einer RootPane hinzugef�gt
	 */
	protected RootPane createRootPane() {
		// erstelle die RootPane
		RootPane rp = super.createRootPane();
		rootPane = rp;
		
		return rp;
	}


	protected void layoutRootPane() {
		//int paneHeight = this.getRootPane().getHeight();
		//int paneWidth = this.getRootPane().getWidth();

	}

	protected Button createButton(String title, Runnable callback, int x, int y) {
		Button btn = new Button();
		btn.setText(title);
		btn.setSize(BUTTON_DEFAULT_WIDTH, BUTTON_DEFAULT_HEIGHT);
		btn.addCallback(callback);
		btn.setPosition(x, y);
		return btn;
	}

	/**
	 * Returns the entity of a background (default) image
	 * 
	 * @param PathToImage
	 *            For example "/path/img.png", if null, the default picture is
	 *            choosen
	 * @return Entity of background
	 */
	protected Entity setBackground(String pathToImage) {
		Entity background = new Entity("menu"); // Entitaet fuer Hintergrund
		background.setPosition(new Vector2f(400, 300)); // Startposition des
														// Hintergrunds
		Image bgPicture = null;
		try {
			if (pathToImage == null) {
				bgPicture = new Image(DEFAULT_BACKGROUND);
			} else {
				bgPicture = new Image(pathToImage);
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
		background.addComponent(new ImageRenderComponent(bgPicture)); // Bildkomponente
		return background;
	}
	
	/**
	 * Funktion kann mit neuer StateID als Callback bspw. Buttons hinzugefuegt werden
	 * 
	 * @param sid Der State, der eintreten soll
	 * @return Callback-Methode
	 */
	protected Runnable switchState(StateBasedGame game, int sid) {
		class switcher implements Runnable {
			private int nextState = -1;
			private StateBasedGame game = null;
			public void setNextState(int sid) { nextState = sid; }
			public void setGame(StateBasedGame g) { game = g;}
			@Override
			public void run() {
				game.enterState(nextState);
			}
		}
		switcher s = new switcher();
		s.setGame(game);
		s.setNextState(sid);
		return s;
	}
	
	/**
	 * Adds all registered widgets to the root pane
	 * @param rp
	 */
	protected void addAllWidgetsToRootPane() {
		for (Map.Entry<String, Widget> entry : widgets.entrySet()) {
			rootPane.add(entry.getValue());
		    // ...
		}
	}
	
	protected Label createLabel(String title, int posX, int posY, boolean isVisible) {
		Label label = new Label(title);
		label.setPosition(posX, posY);
		label.setVisible(isVisible);
		return label;
		
	}
}
