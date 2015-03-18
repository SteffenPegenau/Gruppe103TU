package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.DialogLayout;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.PopupWindow;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.Bullet;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.Skyline;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.PlayerList;
//=======
//>>>>>>> 843ae3314d23e3585482ce247f1c2d241d30d25d

public class GameplayState extends ExtendedTWLState implements java.io.Serializable {
	private static final int NUMBER_OF_BUILDINGS = 8;

	public Skyline skyline;
	public ThrowForm throwForm;

	private Player[] players = new Player[2];

	private HashMap<String, Bullet> bullets = new HashMap<String, Bullet>();

	double gravity;
	int numberOfRounds;
	int numberOfHitsForVictory;
	double windVelocityX;
	double windVelocityY;

	private int currentPlayer;
	boolean gameOver = false;
	private Player winner = null;

	/*
	 * Setzt die Spieler
	 */
	public GameplayState(int sid, Player[] players, int rounds) {
		super(sid);

		for (int i = 0; i < players.length; i++) {
			this.players[i] = players[i];
			this.players[i].setArrayIndex(i);
			System.out.println("Started with Player " + i + ": "
					+ players[i].getUsername());
			this.players[i].setLifesLeft(rounds);
		}
		skyline = new Skyline(entityManager, sid, NUMBER_OF_BUILDINGS, false);
		currentPlayer = 0;
		//playersStatisticInformation();
		widgets.put("DIALOG_OWNER", new Widget());
		throwForm = new ThrowForm(this, currentPlayer);
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
			skyline.setContainer(container);
			skyline.setGame(game);
			skyline.setG(g);
			skyline.setGameplayState(this);
			skyline.createSkyline();
			for (int i = 0; i < players.length; i++) {
				players[i].setPlayersFigureToDefaultGorilla("gorilla" + i);
				players[i].getPlayersFigure().setPosition(
						skyline.randomBuildingForPlayer(i));
				entityManager.addEntity(stateID, players[i].getPlayersFigure());
			}
			addESCListener(Gorillas.GAMESETUPSTATE);
			addKeyPressedEvent(Input.KEY_ENTER, throwForm.getThrowAction());
			// addAllWidgetsToRootPane(widgets);
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
		//System.out.println("Aktiver Spieler ist nun: " + currentPlayer);
		Bullet.perfectDegreeShot(80, getCurrentPlayer(), getNotCurrentPlayer());
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
		//System.out.println("Added bullet " + bullet.getID()
		//		+ " to gameplaystate");
	}

	public void removeBullet(Bullet bullet) {
		bullets.remove(bullet.getID());
	}

	public HashMap<String, Bullet> getBullets() {
		return bullets;
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// Die pause()-Funktion scheint nicht zu funktionieren, daher dieser
		// if-Workaround
		if (!container.isPaused()) {
			super.update(container, game, delta);
			if (bullets.size() == 0) {
				throwForm.setVisibility(true);
			}
			//updatePlayersStaticInformation();
			// Hat ein Spieler gewonnen?
			if (!gameOver && getWinner() != null) {
				gameOver = true;
				playerWins(getWinner());
			}
		}
	}

	public Player getPlayer(int arrayIndex) {
		//System.out.println(players[arrayIndex]);
		return players[arrayIndex];
	}

	public Player getNotCurrentPlayer() {
		int notCurrentPlayer = (currentPlayer == 0) ? 1 : 0;
		return getPlayer(notCurrentPlayer);
	}

	public String getPlayer1Name() {
		return players[0].getUsername();
	}

	public String getPlayer2Name() {
		return players[1].getUsername();
	}

	public Player getWinner() {
		int lifesLeft;
		for (int i = 0; i < players.length; i++) {
			lifesLeft = players[i].getLifesLeft();
//			 System.out.println("Player " + players[i].getUsername() +
//			 " hat noch " + lifesLeft + " Leben");
			if (lifesLeft <= 0) {
				return players[Player.getOtherPlayersArrayIndex(players[i])];
			}
		}
		return null;
	}

	/**
	 * Erzeugt den Dialog, in dem dem siegreichen Spieler gratuliert wird
	 * 
	 * @param winner
	 */
	public void playerWins(Player winner) {
		winner.setWonRounds(1);
		PlayerList.savePlayer(winner);
		

		// Ausgabe auf Konsole
		System.out.println("**********************************");
		System.out.println("Spieler " + winner.getUsername() + " gewinnt!");
		System.out.println("**********************************");

		PopupWindow popup = new PopupWindow(widgets.get("DIALOG_OWNER"));

		DialogLayout dialog = new DialogLayout();

		DialogLayout.Group horizontalGroup = dialog.createSequentialGroup();
		DialogLayout.Group verticalGroup = dialog.createSequentialGroup();

		Label label = createLabel(
				"Herzlichen Glückwunsch, " + winner.getUsername(), 0, 0, true);

		Button button = createButton("OK", closeDialog(dialog), 120, 15);
		button.adjustSize();

		horizontalGroup.addWidgets(label, button);
		verticalGroup.addWidgets(label, button);

		dialog.setHorizontalGroup(horizontalGroup);
		dialog.setVerticalGroup(verticalGroup);
		popup.add(dialog);

		popup.setSize(400, 200);
		popup.setRequestCloseCallback(switchState(game, Gorillas.MAINMENUSTATE));
		popup.setPosition(Gorillas.FRAME_WIDTH / 2 - popup.getWidth() / 2,
				Gorillas.FRAME_HEIGHT / 2 - popup.getHeight() / 2);
		popup.openPopup();

		/*
		 * // Erzeuge Dialog, in dem dem Sieger gratuliert wird SimpleDialog
		 * dialog = new SimpleDialog(); dialog.setTitle("Spieler " +
		 * winner.getUsername() + " gewinnt!");
		 * dialog.setMessage("Herzlichen Glückwunsch!");
		 * dialog.showDialog(widgets.get("DIALOG_OWNER"));
		 * 
		 * // Was passiert bei Click auf OK dialog.setOkCallback(new Runnable()
		 * {
		 * 
		 * @Override public void run() { System.out.println("Called!");
		 * switchState(game, Gorillas.MAINMENUSTATE).run();
		 * container.setPaused(false); } });
		 * 
		 * // Was passiert bei Click auf Cancel?
		 * dialog.setCancelCallback(closeDialog());
		 */
		// Pausiere das Spiel
		container.pause();

		// Formular unsichtbar
		throwForm.setVisibility(false);

	}

	public Runnable closeDialog(DialogLayout dialog) {
		class close implements Runnable {
			DialogLayout dialog;
			GameplayState gameplayState;

			public close(DialogLayout dialog, GameplayState gameplayState) {
				this.dialog = dialog;
				this.gameplayState = gameplayState;
			}

			@Override
			public void run() {
				System.out.println("Back to MainMenuState");
				dialog.setEnabled(false);
				dialog.removeAllChildren();
				game.enterState(Gorillas.MAINMENUSTATE);
				//switchState(game, Gorillas.MAINMENUSTATE).run();
				//container.setPaused(true);
				//rootPane.destroy();
			}
		}
		Runnable c = new close(dialog, this);
		
		return c;
	}

	public void updatePlayersStaticInformation() {
		Label label = (Label) widgets.get("Freie Leben");
		label.setText(players[0].getUsername() + " Life's left: "
				+ players[0].getLifesLeft() + "\n" + players[1].getUsername()
				+ " Life's left: " + players[1].getLifesLeft());
	}

}
