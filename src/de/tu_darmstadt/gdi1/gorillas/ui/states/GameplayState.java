package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.EditField.Callback;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.slick.BasicTWLGameState;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.DestructibleImageEntity;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.action.basicactions.ChangeStateAction;

public class GameplayState extends BasicTWLGameState {

	private int stateID; // Identifier dieses GamplayState
	private StateBasedEntityManager entityManager; // zugehoeriger entityManager
	
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

	// Methode um eine Zufallszahl zu berechnen zwischen Minimum und Maximum
	public int randomInt(int max, int min) {

		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	// Konsturktor
	public GameplayState(int sid) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
	}
	
	/**
	 * 
	 */
	public GameplayState(int sid, Player[] players) {
		if(players.length != 2) {
			System.err.println("Bad number of players: " + players.length);
		} else {
			stateID = sid;
			entityManager = StateBasedEntityManager.getInstance();
			for (int i = 0; i < players.length; i++) {
				this.players[i] = players[i];
				System.out.println("Started with Player " + i + ": " + players[i].getUsername());
			}
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {

		// Bei Drücken der ESC-Taste zurück ins Hauptmenü wechseln
		Entity escListener = new Entity("ESC_Listener");
		KeyPressedEvent escPressed = new KeyPressedEvent(Input.KEY_ESCAPE);
		escPressed.addAction(new ChangeStateAction(Gorillas.MAINMENUSTATE));
		escListener.addComponent(escPressed);
		entityManager.addEntity(stateID, escListener);

		// TODO Anderes Hintergrundbild, nicht das von Drop of Water...
		// Hintergrund laden
		Entity background = new Entity("background"); // Entität für Hintergrund
		background.setPosition(new Vector2f(400, 300)); // Startposition des
														// Hintergrunds
		background.addComponent(new ImageRenderComponent(new Image(
				"/assets/dropofwater/background.png"))); // Bildkomponente

		// Hintergrund-Entiät an StateBasedEntityManager übergeben
		StateBasedEntityManager.getInstance().addEntity(stateID, background);
		
		// Aus diesem Array wird eine zufällige höhe ausgewählt.
		int[] heightOfBuilding = { (int) (Math.random() * 500),
				(int) (Math.random() * 500), (int) (Math.random() * 500),
				(int) (Math.random() * 500), (int) (Math.random() * 500),
				(int) (Math.random() * 500), (int) (Math.random() * 500),
				(int) (Math.random() * 500) };
		
		Image gorilla = new Image("/assets/gorillas/gorillas/gorilla.png");
		Entity monkeyA = new Entity("monkeyA");
		monkeyA.setPosition(new Vector2f(50, 600 - (heightOfBuilding[0] + 21)));
		monkeyA.addComponent(new ImageRenderComponent(gorilla));
		StateBasedEntityManager.getInstance().addEntity(stateID, monkeyA);
		
		Entity monkeyB = new Entity("monkeyB");
		monkeyB.setPosition(new Vector2f(650, 600 - (heightOfBuilding[6] + 21)));
		monkeyB.addComponent(new ImageRenderComponent(gorilla));
		StateBasedEntityManager.getInstance().addEntity(stateID, monkeyB);

		// game.getContainer().getWidth() / 8, weil man 8 häuser hat.
		BufferedImage image1 = new BufferedImage(
				game.getContainer().getWidth() / 8, heightOfBuilding[0],
				BufferedImage.TYPE_INT_ARGB);
		BufferedImage image2 = new BufferedImage(
				game.getContainer().getWidth() / 8, heightOfBuilding[1],
				BufferedImage.TYPE_INT_ARGB);
		BufferedImage image3 = new BufferedImage(
				game.getContainer().getWidth() / 8, heightOfBuilding[2],
				BufferedImage.TYPE_INT_ARGB);
		BufferedImage image4 = new BufferedImage(
				game.getContainer().getWidth() / 8, heightOfBuilding[3],
				BufferedImage.TYPE_INT_ARGB);
		BufferedImage image5 = new BufferedImage(
				game.getContainer().getWidth() / 8, heightOfBuilding[4],
				BufferedImage.TYPE_INT_ARGB);
		BufferedImage image6 = new BufferedImage(
				game.getContainer().getWidth() / 8, heightOfBuilding[5],
				BufferedImage.TYPE_INT_ARGB);
		BufferedImage image7 = new BufferedImage(
				game.getContainer().getWidth() / 8, heightOfBuilding[6],
				BufferedImage.TYPE_INT_ARGB);
		BufferedImage image8 = new BufferedImage(
				game.getContainer().getWidth() / 8, heightOfBuilding[7],
				BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D building1 = image1.createGraphics();
		Graphics2D building2 = image2.createGraphics();
		Graphics2D building3 = image3.createGraphics();
		Graphics2D building4 = image4.createGraphics();
		Graphics2D building5 = image5.createGraphics();
		Graphics2D building6 = image6.createGraphics();
		Graphics2D building7 = image7.createGraphics();
		Graphics2D building8 = image8.createGraphics();

		building1.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		building2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		building3.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		building4.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		building5.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		building6.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		building6.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		building6.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));

		// Aus diesem Color Array werden die Farben ausgesucht.
		Color[] basicColors = { new Color(255, 0, 0), new Color(0, 0, 255),
				new Color(0, 255, 0), new Color(0, 255, 120),
				new Color(255, 255, 0), new Color(0, 255, 255),
				new Color(255, 0, 255), new Color(255, 160, 0) };

		// Damit man nicht zwei mal hintereinander die gleiche Farbe bekommt
		// wird hier immer abwechselnd
		// eine Farbe aus den ersten 5 Farben ausgewählt und dann eine aus den
		// letzten 3.
		building1.setColor(basicColors[(int) (Math.random() * 4)]);
		building2.setColor(basicColors[7 - (int) (Math.random() * 2)]);
		building3.setColor(basicColors[(int) (Math.random() * 4)]);
		building4.setColor(basicColors[7 - (int) (Math.random() * 2)]);
		building5.setColor(basicColors[(int) (Math.random() * 4)]);
		building6.setColor(basicColors[7 - (int) (Math.random() * 2)]);
		building7.setColor(basicColors[(int) (Math.random() * 4)]);
		building8.setColor(basicColors[7 - (int) (Math.random() * 2)]);

		building1.fillRect(0, 0, game.getContainer().getWidth() / 8,
				heightOfBuilding[0]);
		building2.fillRect(0, 0, game.getContainer().getWidth() / 8,
				heightOfBuilding[1]);
		building3.fillRect(0, 0, game.getContainer().getWidth() / 8,
				heightOfBuilding[2]);
		building4.fillRect(0, 0, game.getContainer().getWidth() / 8,
				heightOfBuilding[3]);
		building5.fillRect(0, 0, game.getContainer().getWidth() / 8,
				heightOfBuilding[4]);
		building6.fillRect(0, 0, game.getContainer().getWidth() / 8,
				heightOfBuilding[5]);
		building7.fillRect(0, 0, game.getContainer().getWidth() / 8,
				heightOfBuilding[6]);
		building8.fillRect(0, 0, game.getContainer().getWidth() / 8,
				heightOfBuilding[7]);

		DestructibleImageEntity skyScraper1 = new DestructibleImageEntity(
				"obstacle1", image1, "gorillas/destruction.png", false);
		DestructibleImageEntity skyScraper2 = new DestructibleImageEntity(
				"obstacle2", image2, "gorillas/destruction.png", false);
		DestructibleImageEntity skyScraper3 = new DestructibleImageEntity(
				"obstacle3", image3, "gorillas/destruction.png", false);
		DestructibleImageEntity skyScraper4 = new DestructibleImageEntity(
				"obstacle4", image4, "gorillas/destruction.png", false);
		DestructibleImageEntity skyScraper5 = new DestructibleImageEntity(
				"obstacle5", image5, "gorillas/destruction.png", false);
		DestructibleImageEntity skyScraper6 = new DestructibleImageEntity(
				"obstacle6", image6, "gorillas/destruction.png", false);
		DestructibleImageEntity skyScraper7 = new DestructibleImageEntity(
				"obstacle6", image7, "gorillas/destruction.png", false);
		DestructibleImageEntity skyScraper8 = new DestructibleImageEntity(
				"obstacle6", image8, "gorillas/destruction.png", false);

		skyScraper1.setPosition(new Vector2f(
				game.getContainer().getWidth() / 16, game.getContainer()
						.getHeight() - (heightOfBuilding[0]) / 2));
		skyScraper2.setPosition(new Vector2f(
				game.getContainer().getWidth() / 16 * 3, game.getContainer()
						.getHeight() - (heightOfBuilding[1]) / 2));
		skyScraper3.setPosition(new Vector2f(
				game.getContainer().getWidth() / 16 * 5, game.getContainer()
						.getHeight() - (heightOfBuilding[2]) / 2));
		skyScraper4.setPosition(new Vector2f(
				game.getContainer().getWidth() / 16 * 7, game.getContainer()
						.getHeight() - (heightOfBuilding[3]) / 2));
		skyScraper5.setPosition(new Vector2f(
				game.getContainer().getWidth() / 16 * 9, game.getContainer()
						.getHeight() - (heightOfBuilding[4]) / 2));
		skyScraper6.setPosition(new Vector2f(
				game.getContainer().getWidth() / 16 * 11, game.getContainer()
						.getHeight() - (heightOfBuilding[5]) / 2));
		skyScraper7.setPosition(new Vector2f(
				game.getContainer().getWidth() / 16 * 13, game.getContainer()
						.getHeight() - (heightOfBuilding[6]) / 2));
		skyScraper8.setPosition(new Vector2f(
				game.getContainer().getWidth() / 16 * 15, game.getContainer()
						.getHeight() - (heightOfBuilding[7]) / 2));

		entityManager.addEntity(stateID, skyScraper1);
		entityManager.addEntity(stateID, skyScraper2);
		entityManager.addEntity(stateID, skyScraper3);
		entityManager.addEntity(stateID, skyScraper4);
		entityManager.addEntity(stateID, skyScraper5);
		entityManager.addEntity(stateID, skyScraper6);
		entityManager.addEntity(stateID, skyScraper7);
		entityManager.addEntity(stateID, skyScraper8);

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
		//System.out.println("Container: " + container + "\tGame: " + game + "\tDelta: " + delta);
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

	}
}
