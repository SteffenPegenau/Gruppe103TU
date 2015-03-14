package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.Bullet;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.Skyline;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;

public class GameplayState extends ExtendedTWLState {
	private static final int NUMBER_OF_BUILDINGS = 8;

	protected Skyline skyline;
	public ThrowForm throwForm;

	private Player[] players = new Player[2];
	
	private HashMap<String, Bullet> bullets = new HashMap<String, Bullet>();
	
	double gravity;
	int numberOfRounds;
	int numberOfHitsForVictory;
	double windVelocityX;
	double windVelocityY;

	private int currentPlayer;

	/*
	 * Setzt die Spieler
	 */
	public GameplayState(int sid, Player[] players) {
		super(sid);
		if (players.length != 2) {
			System.err.println("Bad number of players: " + players.length);
		} else {
			for (int i = 0; i < players.length; i++) {
				this.players[i] = players[i];
				this.players[i].setArrayIndex(i);
				System.out.println("Started with Player " + i + ": "
						+ players[i].getUsername());
			}
			skyline = new Skyline(entityManager, sid, NUMBER_OF_BUILDINGS,
					false);
			currentPlayer = 0;
			
			throwForm = new ThrowForm(this, currentPlayer);
		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);

	}

	/**
	 * wird vom Frame ausgeführt
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		entityManager.renderEntities(container, game, g);
		if (!skyline.isSkyline_built()) {
			skyline.createSkyline();
			for (int i = 0; i < players.length; i++) {
				players[i].setPlayersFigureToDefaultGorilla("gorilla" + i);
				players[i].getPlayersFigure().setPosition(skyline.randomBuildingForPlayer(i));
				entityManager.addEntity(stateID, players[i].getPlayersFigure());
			}
			addESCListener(Gorillas.GAMESETUPSTATE);
			addKeyPressedEvent(Input.KEY_ENTER, throwForm.getThrowAction());
			//addAllWidgetsToRootPane(widgets);
			skyline.setSkyline_built(true);
		}
	}
	
	/**
	 * Wechselt den aktuellen Spieler. Aus 0 wird 1, aus 1 (allem anderen) wird
	 * 0;
	 */
	public void toggleActivePlayer() {
		currentPlayer = (currentPlayer == 0) ? 1 : 0;
		throwForm.setCurrentPlayer(currentPlayer);
		System.out.println("Aktiver Spieler ist nun: " + currentPlayer);		
	}

	/**
	 * In dieser Methode werden in einem BasicTWLGameSate alle GUI-Elemente dem
	 * GameState mit Hilfe einer RootPane hinzugef�gt
	 */
	protected RootPane createRootPane() {
		// erstelle die RootPane
		RootPane rp = super.createRootPane();

		addAllWidgetsToRootPane(widgets);
		return rp;
	}
	
	
	
	/**
	 * in dieser Methode des BasicTWLGameState werden die erstellten
	 * GUI-Elemente platziert
	 */

	protected void layoutRootPane() {
		super.layoutRootPane();
	}

	public Player getCurrentPlayer() {
		return players[currentPlayer];
	}
	/*
	 * /** diese Methode wird bei Klick auf den Button ausgeführt, bzw. mit dem
	 * richtigen keyboard input
	 * 
	 * // TODO Methode für keyboard input anpassen und andere Wurfgegenstände //
	 * vgl. Zeile 272ff. GamplayState Drop of Water void inputFinished() {
	 * Entity banana = new Entity("banana"); banana.setPosition(new
	 * Vector2f((Integer) player.getX(), (Integer) player.getY()));
	 * 
	 * try { // Bild laden und zuweisen banana.addComponent(new
	 * ImageRenderComponent(new Image( "assets/gorillas/banana.png"))); } catch
	 * (SlickException e) {
	 * System.err.println("Cannot find file assets/gorillas/banana.png!");
	 * e.printStackTrace(); } }
	 */
	
	public void addBullet(Bullet bullet) {
		bullets.put(bullet.getID(), bullet);
		System.out.println("Added bullet " + bullet.getID() + " to gameplaystate");
	}
	
	public void removeBullet(Bullet bullet) {
		bullets.remove(bullet.getID());
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		if(bullets.size() == 0) {
			throwForm.setVisibility(true);
		}
	}
	
	public Player getPlayer(int arrayIndex) {
		System.out.println(players[arrayIndex]);
		return players[arrayIndex];
	}

	public String getPlayer1Name(){
		return players[0].getUsername();
	}
	
	public String getPlayer2Name(){
		return players[1].getUsername();
	}
}
