package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.Widget;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.Bullet;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.FigureWithWeapon;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.Skyline;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import de.tu_darmstadt.gdi1.gorillas.weapons.Weapon;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.StateBasedEntityManager;

public class ThrowForm {
	private final static int FORM_DISTANCE_X = 125;
	private int formOffsetX;
	private final static int FORM_OFFSET_Y = posY.A.get();
	private final static int FORM_DISTANCE_Y = 35;

	private GameplayState gameplayState;
	private HashMap<String, Widget> widgets;
	
	private EditField angleField;
	private EditField velocityField;

	// private boolean isVisible = true;
	private int currentPlayer;
	
	private boolean visible;

	public ThrowForm(GameplayState state, int currentPlayer) {
		widgets = state.getWidgets();
		gameplayState = state;
		this.currentPlayer = currentPlayer;
		initiallyDrawInputForm();
		setInputFormsPosition();
	}

	/**
	 * Gibt HashMap zurück, die nur die Form-Widgets enthält
	 * 
	 * @return
	 */
	public HashMap<String, Widget> getFormWidgets() {
		HashMap<String, Widget> formWidgets = new HashMap<String, Widget>();
		String widgetName;
		for (Map.Entry<String, Widget> entry : widgets.entrySet()) {
			widgetName = entry.getKey();
			if (widgetName.startsWith("FORM_")) {
				formWidgets.put(widgetName, entry.getValue());
			}
		}
		return formWidgets;

	}

	public Action getThrowAction() {
		class action implements Action {
			ThrowForm throwForm;

			public action(ThrowForm throwForm) {
				this.throwForm = throwForm;
			}

			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				if (gameplayState.getBullets().size() == 0) {
					throwForm.buttonThrowClicked(gameplayState).run();
				}
			}
		}
		Action a = new action(this);
		return a;
	}

	/**
	 * Setzt die Sichtbarkeit aller Form-Elemente auf Wert visibility
	 * 
	 * @param visibility
	 */
	public void setVisibility(boolean visibility) {
		// this.isVisible = visibility;
		visible = visibility;
		for (Map.Entry<String, Widget> entry : getFormWidgets().entrySet()) {
			entry.getValue().setVisible(visibility);
		}
	}
	
	protected Action tabController() {
		class action implements Action {

			@Override
			public void update(GameContainer gc, StateBasedGame sb, int delta,
					Component event) {
				if (angleField.hasKeyboardFocus()){
					velocityField.requestKeyboardFocus();
				} else {
					angleField.requestKeyboardFocus();
				}
			}
		}
		Action a = new action();
		return a;
	}

	/**
	 * Setzt den aktuellen Spieler
	 * 
	 * @param currentPlayer
	 *            arrayIndex des aktuellen Spielers (0 für ersten Spieler, etc)
	 */
	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	private Runnable callThrowAction() {
		class runner implements Runnable {
			ThrowForm form;
			/**
			 * @param throwForm
			 */
			public runner(ThrowForm throwForm) {
				form = throwForm;
			}

			@Override
			public void run() {
				if (gameplayState.getBullets().size() == 0) {
					form.buttonThrowClicked(gameplayState).run();
				}
			}
		}
		Runnable r = new runner(this);
		return r;
	}
	
	/**
	 * Erzeugt alle Formular-Elemente ohne Position
	 */
	private void initiallyDrawInputForm() {
		// Fügt erst alle Elemente ohne Position hinzu
		angleField = gameplayState.createEditField(0, 0, true, "0");
		velocityField = gameplayState.createEditField(0, 0, true, "0");
		widgets.put("FORM_BUTTON_THROW",
				gameplayState.createButton("Wurf!", callThrowAction(), 0, 0));
		widgets.put("FORM_LABEL_ANGLE",
				gameplayState.createLabel("Winkel:", 0, 0, true));
		widgets.put("FORM_EDIT_ANGLE",
				angleField);
		widgets.put("FORM_LABEL_VELOCITY",
				gameplayState.createLabel("Geschwindigkeit:", 0, 0, true));
		widgets.put("FORM_EDIT_VELOCITY",
				velocityField);

		// Setze Breite der Eingabefelder
		int edit_width = 40;
		int edit_height = widgets.get("FORM_EDIT_ANGLE").getHeight();
		widgets.get("FORM_EDIT_ANGLE").setSize(edit_width, edit_height);
		widgets.get("FORM_EDIT_VELOCITY").setSize(edit_width, edit_height);

		// Setze Größe des Buttons
		widgets.get("FORM_BUTTON_THROW").setSize(40, 40);
		// Setze callback für Eingabefelder zur Kontrolle der Eingabe
		addNumberInputCheck((EditField) widgets.get("FORM_EDIT_ANGLE"), 360);
		addNumberInputCheck((EditField) widgets.get("FORM_EDIT_VELOCITY"), 200);
		
	}

	/**
	 * Überprüft, ob der Übergebene Charakter eine Zahl ist und ob
	 * 
	 * @param inputChar
	 * @param editField
	 * @param callback
	 * @param maxValue
	 */
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

	/*
	 * Verschiebt die Eingabemaske auf die Seite des aktuellen Spielers
	 */
	private void adjustOffset() {
		if (currentPlayer == 0) {
			formOffsetX = posX.A.get();
		} else {
			formOffsetX = posX.K.get();
		}
	}

	/**
	 * Initiiert Wurf und wechselt Spieler
	 * 
	 * @return
	 */
	public Runnable buttonThrowClicked(GameplayState state) {
		class event implements Runnable {
			GameplayState gameplayState;
			StateBasedEntityManager entityManager;
			ThrowForm throwForm;
			Skyline skyline;
			double angle;
			float velocity;
			
			public event(GameplayState gameplayState) {
				this.gameplayState = gameplayState;
				entityManager = gameplayState.entityManager;
				throwForm = gameplayState.throwForm;
				skyline = gameplayState.getSkyline();
			}

			@Override
			public void run() {
				angle = throwForm.getAngle();
				velocity = throwForm.getVelocity();
				if(angle >= 0 && velocity >= 0) {
					Player player = gameplayState.getCurrentPlayer();
					
					if(skyline.getFigureWithWeapon(player.getArrayIndex()) != null) {
						// Spieler 1 Figur aus Skyline
						int index = player.getArrayIndex();
						FigureWithWeapon figure1SavedInSkyline = skyline.getFigureWithWeapon(index);
						figure1SavedInSkyline.setOwner(player);
						player.setPlayersFigure(figure1SavedInSkyline);
						
						// Spieler 2 Figur aus Skyline
						Player otherPlayer = gameplayState.getNotCurrentPlayer();
						index = otherPlayer.getArrayIndex();
						FigureWithWeapon figure2SavedInSkyline = skyline.getFigureWithWeapon(index);
						figure2SavedInSkyline.setOwner(gameplayState.getNotCurrentPlayer());
						gameplayState.getNotCurrentPlayer().setPlayersFigure(figure2SavedInSkyline);
					}
					FigureWithWeapon fig = player.getPlayersFigure();
					Weapon weapon = fig.getWeapon();
					
					System.out.println(this.getClass().getSimpleName() + ": Fig: " + fig);
					Bullet bullet = weapon
							.shot(player, fig, angle, velocity, state);
					entityManager.addEntity(state.getID(), bullet);
					state.addBullet(bullet);
					throwForm.saveEnteredValues(throwForm.currentPlayer);
					state.toggleActivePlayer();
					throwForm.restoreEnteredValues(throwForm.currentPlayer);
					setInputFormsPosition();

					throwForm.setVisibility(false);
				}
			}
		}
		Runnable c = new event(state);
		//Runnable c = new event(gameplayState, this);
		return c;
	}

	/**
	 * Bringt die Formular-Elemente - abhängig vom aktuellen Spieler - in
	 * Position
	 */
	public void setInputFormsPosition() {
		adjustOffset();
		int posX = formOffsetX;
		int posY = FORM_OFFSET_Y;
		widgets.get("FORM_LABEL_ANGLE").setPosition(posX, posY);
		widgets.get("FORM_EDIT_ANGLE").setPosition(posX + FORM_DISTANCE_X,
				posY - 20);
		posY += FORM_DISTANCE_Y;
		widgets.get("FORM_LABEL_VELOCITY").setPosition(posX, posY);
		widgets.get("FORM_EDIT_VELOCITY").setPosition(posX + FORM_DISTANCE_X,
				posY - 20);
		posY += FORM_DISTANCE_Y;
		widgets.get("FORM_BUTTON_THROW").setPosition(posX + FORM_DISTANCE_X,
				posY - 20);
	}

	/**
	 * Returns the currently Value of the Angle Edit Field
	 * 
	 * @return Angle
	 */
	public double getAngle() {
		EditField editAngle = (EditField) widgets.get("FORM_EDIT_ANGLE");
		String input = editAngle.getText();
		if (input.isEmpty()) {
			return -1.0;
		} else {
			return Double.parseDouble(input);
		}
	}

	/**
	 * Returns the currently value of the velocity edit field
	 * 
	 * @return Velocity
	 */
	public float getVelocity() {
		EditField editVelocity = (EditField) widgets.get("FORM_EDIT_VELOCITY");
		String input = editVelocity.getText();
		if (input.isEmpty()) {
			return -1;
		} else {
			return Float.parseFloat(input);
		}

	}

	/**
	 * Fügt dem EditField ein Callback hinzu, der dafür sorgt, dass nur Zahlen
	 * bis maxValue akzeptiert werden
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
	 * Speichert die aktuelle Eingabe im Objekt des Spielers
	 * 
	 * @param arrayIndex
	 *            Index des Spielers, dessen Werte gespeichert werden sollen
	 */
	public void saveEnteredValues(int arrayIndex) {
		Player p = gameplayState.getPlayer(arrayIndex);
		EditField angle = (EditField) widgets.get("FORM_EDIT_ANGLE");
		p.enteredAngle = angle.getText();
		EditField velocity = (EditField) widgets.get("FORM_EDIT_VELOCITY");
		p.enteredVelocity = velocity.getText();
	}

	/**
	 * Überschreibt die Eingabefelder angle und velocity mit den bei dem Spieler
	 * gespeicherten Werten
	 * 
	 * @param arrayIndex
	 *            Index des Spielers, von dem die Daten geholt werden sollen
	 */
	public void restoreEnteredValues(int arrayIndex) {
		EditField angle = (EditField) widgets.get("FORM_EDIT_ANGLE");

		String enteredAngle = gameplayState.getPlayer(arrayIndex).enteredAngle;
		String enteredVelocity = gameplayState.getPlayer(arrayIndex).enteredVelocity;
		if (enteredAngle != null && enteredVelocity != null) {
			angle.setText(enteredAngle);
			EditField velocity = (EditField) widgets.get("FORM_EDIT_VELOCITY");
			velocity.setText(enteredVelocity);
		}

	}

	public boolean isVisible() {
		return visible;
	}

}
