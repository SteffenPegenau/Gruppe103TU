package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.Skyline;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.DestructibleImageEntity;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;

public class GameplayState extends ExtendedTWLState {

	private int stateID; // Identifier dieses GamplayState
	private StateBasedEntityManager entityManager; // zugehoeriger entityManager
	
	private static final int NUMBER_OF_BUILDINGS = 8;
	
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
	private Player player;

	// Methode um eine Zufallszahl zu berechnen zwischen Minimum und Maximum
	public int randomInt(int max, int min) {

		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

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
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		// Esc-Taste => Hauptmenü
		entityManager.addEntity(stateID, setESCListener(Gorillas.MAINMENUSTATE));

		
		// Hintergrundbild der Skyline setzen
		entityManager.addEntity(stateID, skyline.getBackgroundEntity());

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
		// System.out.println("Container: " + container + "\tGame: " + game +
		// "\tDelta: " + delta);
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
		// erstelle ein Label mit der Aufschrift "angle:"
		angleLabelL = new Label("angle:");
		angleLabelR = new Label("angle:");
		// erstelle ein EditField. Es dient der Eingabe von Text
		angleInputL = new EditField();
		angleInputR = new EditField();
		// mit der Methode addCallBack l�sst sich dem EditField ein CallBack
		// hinzufügen, in dessen Methode callback(int key) bestimmt wird, was
		// geschehen soll, wenn ein Zeichen eingegeben wird
		angleInputL.addCallback(new Callback() {
			public void callback(int key) {
				// in unserem Fall wird der Input in der Methode
				// handleEditFieldInput verarbeitet (siehe weiter unten in
				// dieser Klasse, was diese tut, und was es mit ihren Parametern
				// auf sich hat)
				handleEditFieldInput(key, angleInputL, this, 360);
			}
		});
		angleInputR.addCallback(new Callback() {
			public void callback(int key) {
				handleEditFieldInput(key, angleInputR, this, 360);
			}
		});
		// analog zu einer Eingabemöglichkeit für x-Werte wird auch eine für
		// y-Werte kreiert
		// TODO im späteren Spielverlauf Wurfstärke anpassen
		velocityLabelL = new Label("velocity:");
		velocityLabelR = new Label("velocity:");
		velocityInputL = new EditField();
		velocityInputR = new EditField();
		velocityInputL.addCallback(new Callback() {
			public void callback(int key) {
				handleEditFieldInput(key, velocityInputL, this, 500);
			}
		});
		velocityInputR.addCallback(new Callback() {
			public void callback(int key) {
				handleEditFieldInput(key, velocityInputR, this, 500);
			}
		});

		// TODO Callback: keyboard input ergänzen + vgl. GameplayState Drop of
		// Water Zeile 172ff.
		throwButtonL = new Button("throw");
		throwButtonL.addCallback(new Runnable() {
			public void run() {
				inputFinished();
			}
		});

		throwButtonR = new Button("throw");
		throwButtonR.addCallback(new Runnable() {
			public void run() {
				inputFinished();
			}
		});

		// Hinzfügen aller GUI-Elemente er Rootpane
		rp.add(angleLabelL);
		rp.add(angleLabelR);
		rp.add(angleInputL);
		rp.add(angleInputR);
		rp.add(velocityLabelL);
		rp.add(velocityLabelR);
		rp.add(velocityInputL);
		rp.add(velocityInputR);
		rp.add(throwButtonL);
		rp.add(throwButtonR);

		return rp;
	}

	/**
	 * in dieser Methode des BasicTWLGameState werden die erstellten
	 * GUI-Elemente platziert
	 */

	protected void layoutRootPane() {

		// Ersten bzw. linker Player
		int xOffsetL = 50;
		int yOffsetL = 50;
		int gap = 5;

		// alle GUI-Elemente müssen eine Gruppe zugewiesen bekommen
		// adjustSize() muss aufgerufen werden, wenn die Größe automatisch über
		// die Beschriftung des GUI-Elemnts bestimmt werden soll
		angleLabelL.adjustSize();
		velocityLabelL.adjustSize();

		// sonst wird die Größe manuell mit set size gesetzt
		angleInputL.setSize(50, 25);
		velocityInputL.setSize(50, 25);
		throwButtonL.setSize(50, 25);

		// Nachdem alle Gruppen adjustiert wruden, muss allen GUI-Elementen eine
		// Position (linke obere Ecke) zugewiesen werden
		angleLabelL.setPosition(xOffsetL, yOffsetL);
		angleInputL.setPosition(xOffsetL + angleLabelL.getWidth() + gap,
				yOffsetL);
		velocityLabelL.setPosition(xOffsetL - 13,
				yOffsetL + angleLabelL.getHeight() + gap);
		velocityInputL.setPosition(xOffsetL + velocityLabelL.getWidth() - 14
				+ gap, yOffsetL + angleLabelL.getHeight() + gap);
		throwButtonL.setPosition(xOffsetL - 13 + velocityLabelL.getWidth()
				+ gap, yOffsetL + angleLabelR.getHeight() + 25 + gap
				+ velocityLabelL.getHeight() + gap);

		// TODO Plazierung der Eingabefelder + throw Button präziser anpassen --
		// Fertig durch Ausprobieren..ist das ok?
		int xOffsetR = 700 - xOffsetL;
		int yOffsetR = yOffsetL;

		angleLabelR.adjustSize();
		velocityLabelR.adjustSize();

		angleInputR.setSize(angleInputL.getWidth(), angleInputL.getHeight());
		velocityInputR.setSize(velocityInputL.getWidth(),
				velocityInputL.getHeight());
		throwButtonR.setSize(throwButtonL.getWidth(), throwButtonL.getHeight());

		angleLabelR.setPosition(xOffsetR + 14, yOffsetR);
		angleInputR.setPosition(xOffsetR + angleLabelR.getWidth() + 14 + gap,
				yOffsetR);
		velocityLabelR.setPosition(xOffsetR, yOffsetR + angleLabelR.getHeight()
				+ gap);
		velocityInputR.setPosition(xOffsetR + velocityLabelR.getWidth() + gap,
				yOffsetR + velocityLabelR.getHeight() + gap);
		throwButtonR.setPosition(xOffsetR + 62, yOffsetL + 55);

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
	void handleEditFieldInput(int key, EditField editField, Callback callback,
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

	/**
	 * diese Methode wird bei Klick auf den Button ausgeführt, bzw. mit dem
	 * richtigen keyboard input
	 */
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

}
