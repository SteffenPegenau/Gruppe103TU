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
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

/**
 * @author Steffen Pegenau (steffen.pegenau@gmail.com)
 *
 */
public class ExtendedTWLState extends BasicTWLGameState {
	protected int stateID;
	protected StateBasedEntityManager entityManager;

	protected final int distance = 200;
	protected final int startPosition = 90;

	public final static int BUTTON_DEFAULT_WIDTH = 120;
	public final static int BUTTON_DEFAULT_HEIGHT = 40;

	public final static int BUTTON_LAST_LINE_Y = 525;
	public final static int BUTTON_RIGHT_X = 625;
	public final static int BUTTON_LEFT_X = 50;
	public final static int BUTTON_MIDDLELEFT_X = 150;

	protected final static String DEFAULT_BACKGROUND = "/assets/gorillas/backgrounds/gorilla_face.png";

	HashMap<String, Widget> widgets = new HashMap<String, Widget>();

	protected RootPane rootPane = null;
	protected StateBasedGame game = null;
	protected GameContainer container = null;

	//Konstruktor
	public ExtendedTWLState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}

	/**
	 * Returns all registered widgets
	 * 
	 * @return
	 */
	public HashMap<String, Widget> getWidgets() {
		return widgets;
	}

	/**
	 * Erzeugt ein key-pressed-Event in Form einer Entität
	 * 
	 * @param key
	 *            Taste, die gedrückt wurde
	 * @param a
	 *            Action, die bei Tastendruck ausgeführt werden soll
	 * @return Entität mit Event
	 */
	protected Entity keyPressedEvent(int key, Action a) {
		Entity listener = new Entity("Listener_KEY_" + key);
		KeyPressedEvent keyPressed = new KeyPressedEvent(key);
		keyPressed.addAction(a);
		listener.addComponent(keyPressed);
		return listener;
	}

	/**
	 * Erzeugt ein keyPressedEvent und fügt es dem entity manager hinzu
	 * 
	 * @param key
	 *            Taste, die gedrückt wurde
	 * @param a
	 *            , Action die bei Tastendruck ausgeführt werden soll
	 */
	protected void addKeyPressedEvent(int key, Action a) {
		entityManager.addEntity(stateID, keyPressedEvent(key, a));
	}

	protected Entity setESCListener(int newState) {
		Entity escListener = new Entity("ESC_Listener");
		KeyPressedEvent escPressed = new KeyPressedEvent(Input.KEY_ESCAPE);
		escPressed.addAction(new ChangeStateAction(newState));
		escListener.addComponent(escPressed);
		return escListener;
	}

	protected void addESCListener(int newState) {
		entityManager.addEntity(stateID, setESCListener(newState));
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
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
		if (entityManager == null) {
			entityManager = StateBasedEntityManager.getInstance();
		}
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
		// int paneHeight = this.getRootPane().getHeight();
		// int paneWidth = this.getRootPane().getWidth();

	}

	public Button createButton(String title, Runnable callback, int x, int y) {
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
	 * Funktion kann mit neuer StateID als Callback bspw. Buttons hinzugefuegt
	 * werden
	 * 
	 * @param sid
	 *            Der State, der eintreten soll
	 * @return Callback-Methode
	 */
	protected Runnable switchState(StateBasedGame game, int sid) {
		class switcher implements Runnable {
			private int nextState = -1;
			private StateBasedGame game = null;

			public void setNextState(int sid) {
				nextState = sid;
			}

			public void setGame(StateBasedGame g) {
				game = g;
			}

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
	 * 
	 * @param rp
	 */
	protected void addAllWidgetsToRootPane(Map<String, Widget> widgets) {
		for (Map.Entry<String, Widget> entry : widgets.entrySet()) {
			rootPane.add(entry.getValue());
		}
		System.out.println("Added " + widgets.size() + " to the root pane");
	}

	public Label createLabel(String title, int posX, int posY, boolean isVisible) {
		Label label = new Label(title);
		label.setPosition(posX, posY);
		label.setVisible(isVisible);
		return label;
	}

	/**
	 * Erzeugt ein EditField an posX|posY, das (de)aktiviert ist
	 * 
	 * @param posX X-Koordinate
	 * @param posY Y-Koordinate
	 * @param isEnabled Wenn false, lässt sich nichts eingeben
	 * @return EditField
	 */
	public EditField createEditField(int posX, int posY, boolean isEnabled) {
		EditField field = new EditField();
		int yOffset = (posY - 20 < 0) ? 0 : -20;
		field.setPosition(posX, posY + yOffset);
		field.setEnabled(isEnabled);
		field.setSize(300, 30);
		return field;
	}
	
	/**
	 * Erzeugt ein EditField an posX|posY, das (de)aktiviert ist, mit eingegebem defaultText
	 * 
	 * @param posX X-Koordinate
	 * @param posY Y-Koordinate
	 * @param isEnabled Wenn false, lässt sich nichts eingeben
	 * @param defaultText Text, der nach Erstellung in EditField stehen soll
	 * @return EditField
	 */
	public EditField createEditField(int posX, int posY, boolean isEnabled, String defaultText) {
		EditField field = createEditField(posX, posY, isEnabled);
		field.setText(defaultText);
		return field;
	}

	protected Runnable nullRun() {
		class nothing implements Runnable {
			@Override
			public void run() {
				// NOTHING
			}

		}
		return new nothing();
	}

	public Widget getWidget(String name) {
		return widgets.get(name);
	}
/////////////////////////////HINZUGEFÜGTE METHODE FÜR RUNDENBASIERTES SPIEL (aus ThrowForm Klasse)////////////////////////////////////////////
	public void addNumberInputCheck(EditField editField, int maxValue) {
		class NumberCheck implements Callback {
			private EditField editField;
			private int maxValue;

			public NumberCheck(EditField editField, int maxValue) {
				this.editField = editField;
				this.maxValue = maxValue;
			}

			@Override
			public void callback(int buttonPressed) {
				numberInputCheck(buttonPressed, editField, this, maxValue);

			}
		}
		NumberCheck cb = new NumberCheck(editField, maxValue);
		editField.addCallback(cb);
	}
	public void numberInputCheck(int key, EditField editField,
			Callback callback, int maxValue) {
		if (key == de.matthiasmann.twl.Event.KEY_NONE) {
			String inputText = editField.getText();

			if (inputText.isEmpty()) {
				return;
			}

			char inputChar = inputText.charAt(inputText.length() - 1);
			addCharToEditField(inputChar, editField, callback, maxValue);
		}
	}
	public static void addCharToEditField(char inputChar, EditField editField,
			Callback callback, int maxValue) {
		String inputText = editField.getText();
		// System.out.println("INPUT TEXT: " + inputText);
		if (!Character.isDigit(inputChar)
				|| Integer.parseInt(inputText) > maxValue) {
			// a call of setText on an EditField triggers the callback, so
			// remove callback before and add it again after the call
			editField.removeCallback(callback);

			String newText = "";
			if (inputText.length() - 1 >= 0) {
				newText = inputText.substring(0, inputText.length() - 1);
			}

			editField.setText(newText);
			editField.addCallback(callback);
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
/////////////////////////////////////HINZUGEFÜGTE METHODE CREATE BUTTON FÜR HIGHSCORESTATE (aus MainMenuState)/////////////////////
	
	protected void createButton(String name, String title, Action action, int x, int y) {
		Image menu_entry_background = null;
		ArrayList<String> menuentries = new ArrayList<String>();
		
		Entity button = new Entity(name);
		button.setPosition(new Vector2f(x, y));
		button.setScale(0.28f);
		
		if(menu_entry_background == null) {
			try {
				menu_entry_background = new Image("assets/gorillas/button/entry.png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		button.addComponent(new ImageRenderComponent(menu_entry_background));

		// Erstelle das Ausloese-Event und die zugehoerige Action
		ANDEvent mainEventsQ = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());
		mainEventsQ.addAction(action);
		button.addComponent(mainEventsQ);
		menuentries.add(title);
		entityManager.addEntity(stateID, button);
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
}
