package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.Skyline;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import eea.engine.entity.StateBasedEntityManager;

public class GameplayState extends ExtendedTWLState {

	private int stateID; // Identifier dieses GamplayState
	private StateBasedEntityManager entityManager; // zugehoeriger entityManager
	
	private static final int NUMBER_OF_BUILDINGS = 8;
	
	private final static int FORM_DISTANCE_X = 125;
	private int formOffsetX;
	private final static int FORM_OFFSET_Y = posY.A.get();
	private final static int FORM_DISTANCE_Y = 35;
	
	protected Skyline skyline;

	private Player[] players = new Player[2];
	double gravity;
	int numberOfRounds;
	int numberOfHitsForVictory;
	double windVelocityX;
	double windVelocityY;
	private Label angleLabelL; // L für links
	EditField angleInputL;
	private Label velocityLabelL;
	EditField velocityInputL;
	private Label angleLabelR; // R für rechts
	EditField angleInputR;
	private Label velocityLabelR;
	EditField velocityInputR;
	private Button throwButtonL;
	private Button throwButtonR;
	
	private int currentPlayer;

	/*
	// Methode um eine Zufallszahl zu berechnen zwischen Minimum und Maximum
	public int randomInt(int max, int min) {

		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}
	*/

	/*
	 * Setzt die Spieler
	 */
	public GameplayState(int sid, Player[] players) {
		super(sid);
		if (players.length != 2) {
			System.err.println("Bad number of players: " + players.length);
		} else {
			stateID = sid;
			entityManager = StateBasedEntityManager.getInstance();
			for (int i = 0; i < players.length; i++) {
				this.players[i] = players[i];
				System.out.println("Started with Player " + i + ": "
						+ players[i].getUsername());
			}
			skyline = new Skyline(entityManager, sid, NUMBER_OF_BUILDINGS, false);
			
			currentPlayer = 0;
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// Esc-Taste => Hauptmenü
		entityManager.addEntity(stateID, setESCListener(Gorillas.MAINMENUSTATE));

		skyline.createSkyline();
	}

	/**
	 * wird vom Frame ausgeführt
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		entityManager.renderEntities(container, game, g);
	}

	/**
	 * wird vom Frame ausgeführt
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		entityManager.updateEntities(container, game, delta);
	}

	@Override
	public int getID() {
		return stateID;
	}
	
	/*
	 * Verschiebt die Eingabemaske auf die Seite des aktuellen Spielers
	 */
	private void adjustOffset() {
		if(currentPlayer == 0) {
			formOffsetX = posX.A.get();
		} else {
			formOffsetX = posX.K.get();
		}
	}
	
	/**
	 * Wechselt den aktuellen Spieler. Aus 0 wird 1, aus 1 (allem anderen) wird 0;
	 */
	public void toggleActivePlayer() {
		currentPlayer = (currentPlayer == 0) ? 1 : 0;
		System.out.println("Aktiver Spieler ist nun: " + currentPlayer);
	}
	
	/**
	 * Initiiert Wurf und wechselt Spieler
	 * 
	 * @return 
	 */
	private Runnable buttonThrowClicked() {
		class event implements Runnable {
			private GameplayState state;
			public event(GameplayState gameplayState) {
				this.state = gameplayState;
			}
			@Override
			public void run() {
				state.toggleActivePlayer();
				state.setInputFormsPosition();
			}
		}
		Runnable c = new event(this);
		return c;
	}
	
	/**
	 * Bringt die Formular-Elemente - abhängig vom aktuellen Spieler - in Position
	 */
	public void setInputFormsPosition() {
		adjustOffset();
		int posX = formOffsetX;
		int posY = FORM_OFFSET_Y;
		
		widgets.get("LABEL_ANGLE").setPosition(posX, posY);
		widgets.get("EDIT_ANGLE").setPosition(posX + FORM_DISTANCE_X, posY - 20);
		posY += FORM_DISTANCE_Y;
		widgets.get("LABEL_VELOCITY").setPosition(posX, posY);
		widgets.get("EDIT_VELOCITY").setPosition(posX + FORM_DISTANCE_X, posY - 20);
		posY += FORM_DISTANCE_Y;
		widgets.get("BUTTON_THROW").setPosition(posX + FORM_DISTANCE_X, posY - 20);
	}
	
	/**
	 * Fügt dem EditField ein Callback hinzu, der dafür sorgt, dass nur Zahlen bis maxValue akzeptiert werden
	 * 
	 * @param editField
	 * @param maxValue
	 */
	private void addNumberInputCheck(EditField editField, int maxValue) {
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
	
	/**
	 * Erzeugt alle Formular-Elemente ohne Position
	 */
	private void initiallyDrawInputForm() {
		// Fügt erst alle Elemente ohne Position hinzu
		widgets.put("BUTTON_THROW", createButton("Wurf!", buttonThrowClicked(), 0, 0));
		widgets.put("LABEL_ANGLE", createLabel("Winkel:", 0, 0, true));
		widgets.put("EDIT_ANGLE", createEditField(0, 0, true));
		widgets.put("LABEL_VELOCITY", createLabel("Geschwindigkeit:", 0, 0, true));
		widgets.put("EDIT_VELOCITY", createEditField(0, 0, true));
		
		// Setze Breite der Eingabefelder
		int edit_width = 40;
		int edit_height = widgets.get("EDIT_ANGLE").getHeight();
		widgets.get("EDIT_ANGLE").setSize(edit_width, edit_height);
		widgets.get("EDIT_VELOCITY").setSize(edit_width, edit_height);
		
		// Setze Größe des Buttons
		widgets.get("BUTTON_THROW").setSize(40, 40);
		
		// Setze callback für Eingabefelder zur Kontrolle der Eingabe
		addNumberInputCheck((EditField) widgets.get("EDIT_ANGLE"), 360);
		addNumberInputCheck((EditField) widgets.get("EDIT_VELOCITY"), 200);
	}

	/**
	 * In dieser Methode werden in einem BasicTWLGameSate alle GUI-Elemente dem
	 * GameState mit Hilfe einer RootPane hinzugef�gt
	 */
	protected RootPane createRootPane() {
		// erstelle die RootPane
		RootPane rp = super.createRootPane();
		
		initiallyDrawInputForm();
		setInputFormsPosition();
		addAllWidgetsToRootPane(widgets);
		return rp;
	}

	/**
	 * in dieser Methode des BasicTWLGameState werden die erstellten
	 * GUI-Elemente platziert
	 */

	protected void layoutRootPane() {
		
	}

	/**
	 * Diese Methode wird aufgerufen, wenn ein Zeichen in ein EditField
	 * eingegeben wurde.
	 * 
	 * @param key
	 *            die gedrückte Taste
	 * @param editField
	 *            das EditField, in das ein Zeichen eingefügt wurde
	 * @param callback
	 *            der CallBack, der dem EditField hinzugefügt wurde
	 * @param maxValue
	 *            die größte Zahl, die in das <code>editField</code> eingegeben
	 *            werden kann
	 */
	void numberInputCheck(int key, EditField editField, Callback callback,
			int maxValue) {
		if (key == de.matthiasmann.twl.Event.KEY_NONE) {
			String inputText = editField.getText();

			if (inputText.isEmpty()) {
				return;
			}

			char inputChar = inputText.charAt(inputText.length() - 1);
			if (!Character.isDigit(inputChar)
					|| Integer.parseInt(inputText) > maxValue) {
				// a call of setText on an EditField triggers the callback, so
				// remove callback before and add it again after the call
				editField.removeCallback(callback);
				editField
						.setText(inputText.substring(0, inputText.length() - 1));
				editField.addCallback(callback);
			}
		}
	}

	/*
	/**
	 * diese Methode wird bei Klick auf den Button ausgeführt, bzw. mit dem
	 * richtigen keyboard input
	 
	// TODO Methode für keyboard input anpassen und andere Wurfgegenstände
	// vgl. Zeile 272ff. GamplayState Drop of Water
	void inputFinished() {
		Entity banana = new Entity("banana");
		banana.setPosition(new Vector2f((Integer) player.getX(),
				(Integer) player.getY()));

		try {
			// Bild laden und zuweisen
			banana.addComponent(new ImageRenderComponent(new Image(
					"assets/gorillas/banana.png")));
		} catch (SlickException e) {
			System.err.println("Cannot find file assets/gorillas/banana.png!");
			e.printStackTrace();
		}
	}
	*/

}
