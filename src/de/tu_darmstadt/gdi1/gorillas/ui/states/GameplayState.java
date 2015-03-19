package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
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
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;

public class GameplayState extends ExtendedTWLState {
	private static final int NUMBER_OF_BUILDINGS = 8;
	private Skyline skyline;
	public ThrowForm throwForm;
	private Player[] players = new Player[2];
	private HashMap<String, Bullet> bullets = new HashMap<String, Bullet>();
	double gravity;
	int numberOfRounds;
	int numberOfHitsForVictory;
	double windVelocityX;
	double windVelocityY;
	public int currentPlayer;
	private Player winner = null;
	public static Random r = new Random();
	public static int low = -15;
	public static int high = 15;
	public static int wind = r.nextInt(high - low) + low; // Wind zwischen -15 -
															// 15
	Image layer_underneath = null;
	boolean windOnOff;
	public RootPane rp = super.createRootPane();
	Label label;
	ArrayList<Bullet> listOfBullets = new ArrayList<Bullet>();

	public javax.swing.Timer t = new javax.swing.Timer(2000,
			new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					label.setText(null);
				}
			});

	/*
	 * Setzt die Spieler ins Array
	 */
	public GameplayState(int sid, Player[] players, int rounds, double gravity,
			boolean wind) {
		super(sid);
		if (players.length != 2) {
			System.err.println("Bad number of players: " + players.length);
		} else {
			for (int i = 0; i < players.length; i++) {
				this.players[i] = players[i];
				this.players[i].setArrayIndex(i);
				System.out.println("Started with Player " + i + ": "
						+ players[i].getUsername());
				this.players[i].setLifesLeft(rounds);
			}
			skyline = new Skyline(entityManager, sid, NUMBER_OF_BUILDINGS,
					false);
			currentPlayer = 0;
			playersStatisticInformation();
			widgets.put("DIALOG_OWNER", new Widget());
			throwForm = new ThrowForm(this, currentPlayer);
			this.gravity = gravity;
			this.windOnOff = wind;
			System.out.println("Wind: " + wind);
			System.out.println("Wind an? " + wind);
		}
	}

	/**
	 * Erstellt eine neue Skyline
	 */

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
				players[i].getPlayersFigure().getWeapon()
						.setGravityInput(gravity);
				players[i].getPlayersFigure().setPosition(
						skyline.randomBuildingForPlayer(i));
				entityManager.addEntity(stateID, players[i].getPlayersFigure());
			}

			positionPlayer();

			if (!skyline.isSkyline_built()) {
				createNewSkyline();
				// TODO addKeyPressedEvent(Input.KEY_TAB, throwForm.);
				// addAllWidgetsToRootPane(widgets);
				skyline.setSkyline_built(true);

				if (windOnOff) {
					Entity arrow = new Entity("arrow");
					arrow.addComponent(new ImageRenderComponent(new Image(
							"assets/gorillas/button/arrow.png")));
					arrow.setPosition(new Vector2f(350, 100));
					if (wind < 0) {
						arrow.setRotation(180.0f);
					} else {
						arrow.setRotation(0.0f);
					}
					arrow.setScale((1.0f / 5) * (float) Math.abs(wind));
					entityManager.addEntity(this.stateID, arrow);
				}
			}
		}
	}

	public void decideComment(int distance) {
		de.tu_darmstadt.gdi1.gorillas.comments.EnumToString enumToString = new de.tu_darmstadt.gdi1.gorillas.comments.EnumToString();
		if (listOfBullets.size() > 0) {
			distance = listOfBullets.get(0).getDist(getNotCurrentPlayer());
			if (distance == 0) {
				label = new Label(enumToString.printHit());
			}
			if (distance <= 150 && distance >= -150) {
				System.out.println("ich bin in der ersten if Abfrage");
				label = new Label(enumToString.printClose());
				label.setSize(200, 100);
				label.setPosition(300, 30);
				t.setRepeats(false);
				t.start();
				rp.add(label);
			}
			if (distance >= 150) {
				System.out.println("ich bin in der zweiten if Abfrage");
				label = new Label(enumToString.printToShort());
				label.setSize(200, 100);
				label.setPosition(300, 30);
				t.setRepeats(false);
				t.start();
				rp.add(label);
			} else if (distance < -150) {
				System.out.println("ich bin in der dritten if Abfrage");
				label = new Label(enumToString.printFarOff());
				label.setSize(300, 100);
				label.setPosition(150, 30);
				t.setRepeats(false);
				t.start();
				rp.add(label);
			}
		}
	}

	/**
	 * Erstellt eine neue Skyline mit Gorillas drauf
	 */
	public void createNewSkyline() {
		entityManager.clearEntitiesFromState(stateID);
		skyline.createSkyline();
		positionPlayer();
		addESCListener(Gorillas.MAINMENUSTATE);
		addKeyPressedEvent(Input.KEY_ENTER, throwForm.getThrowAction());
		addKeyPressedEvent(Input.KEY_TAB, throwForm.tabController());
	}

	/**
	 * Setzt die Spieler auf die Gebäude
	 */
	public void positionPlayer() {
		for (int i = 0; i < players.length; i++) {
			players[i].setPlayersFigureToDefaultGorilla("gorilla" + i);
			players[i].getPlayersFigure().getWeapon().setGravityInput(gravity);
			players[i].getPlayersFigure().setPosition(
					skyline.randomBuildingForPlayer(i));
			entityManager.addEntity(stateID, players[i].getPlayersFigure());
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
		Bullet.perfectDegreeShot(80, getCurrentPlayer(), getNotCurrentPlayer(),
				gravity);
	}

	public void farOff() {
		de.tu_darmstadt.gdi1.gorillas.comments.EnumToString enumToString = new de.tu_darmstadt.gdi1.gorillas.comments.EnumToString();
		label = new Label(enumToString.printFarOff());
		label.setSize(200, 100);
		label.setPosition(300, 30);
		t.setRepeats(false);
		t.start();
		rp.add(label);
	}

	public void addBullet(Bullet bullet) {
		bullets.put(bullet.getID(), bullet);
		System.out.println("Added bullet " + bullet.getID()
				+ " to gameplaystate");
		listOfBullets.add(bullet);
		System.out.println("the size of this ListOfBullets is "
				+ listOfBullets.size());
	}

	public void removeBullet(Bullet bullet) {
		bullets.remove(bullet.getID());
		listOfBullets.remove(0);
		System.out
				.println("the size of the ListOfBullets after removing the bullet is "
						+ listOfBullets.size());
	}

	/**
	 * In dieser Methode werden in einem BasicTWLGameSate alle GUI-Elemente dem
	 * GameState mit Hilfe einer RootPane hinzugef�gt
	 */
	protected RootPane createRootPane() {
		// erstelle die RootPane

		// TODO

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

	

	public HashMap<String, Bullet> getBullets() {
		return bullets;
	}
	
	public boolean commentAlreadyVisible() {
		if (t.isRunning()) {
			return true;
		}
		
		else return false;
	}
	
	public ArrayList<Bullet> getListOfBullets(){
		return listOfBullets;
		
		 
		
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
			updatePlayersStaticInformation();
			// Hat ein Spieler gewonnen?
			winner = getWinner();
			if (winner != null) {
				playerWins(winner);
			}
			//
			// if (wind != 0) {
			// Entity arrow = new Entity("arrow");
			// arrow.addComponent(new ImageRenderComponent(new
			// Image("assets/gorillas/button/arrow.png")));
			//
			// arrow.setPosition(new Vector2f(350, 100));
			// arrow.setScale((1 / 15) * wind);
			// entityManager.addEntity(this.stateID, arrow);
			// }
		}

	}

	public Player getPlayer(int arrayIndex) {
		// System.out.println(players[arrayIndex]);
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
			// System.out.println("Player " + players[i].getUsername() +
			// " hat noch " + lifesLeft + " Leben");
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
		winner.won();
		// Ausgabe auf Konsole
		System.out.println("**********************************");
		System.out.println("Spieler " + winner.getUsername() + " gewinnt!");
		System.out.println("**********************************");

		Entity winLayer = new Entity("WINLAYER");
		winLayer.setPosition(new Vector2f(300, 400));
		winLayer.setScale(0.5f);

		if (layer_underneath == null) {
			try {
				layer_underneath = new Image(
						"assets/gorillas/backgrounds/WhiteFrame.png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		winLayer.addComponent(new ImageRenderComponent(layer_underneath));
		entityManager.addEntity(stateID, winLayer);
		// widgets.put("QUITAFTERWINBTN", createButton("Leave Game",
		// Gorillas.MAINMENUSTATE, BUTTON_LEFT_X + 150, 400));

		// Pausiere das Spiel
		container.pause();
		// Formular unsichtbar
		throwForm.setVisibility(false);
	}

	// public Runnable backToMainMenue(int state) {
	// class changer implements Runnable {
	// int mainMenueState
	//
	// public changer(int state) {
	// this.mainMenueState = state;
	// }
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// }
	// }

	public void updatePlayersStaticInformation() {
		Label labelName1 = (Label) widgets.get("Spielernamen1");
		labelName1.setText(players[0].getUsername());
		Label labelLifes1 = (Label) widgets.get("Leben1");
		labelLifes1.setText("Lifes left: " + players[0].getLifesLeft());

		Label labelName2 = (Label) widgets.get("Spielernamen2");
		labelName2.setText(players[1].getUsername());
		Label labelLifes2 = (Label) widgets.get("Leben2");
		labelLifes2.setText("Lifes left: " + players[1].getLifesLeft());
	}

	public void playersStatisticInformation() {
		widgets.put("Spielernamen1",
				createLabel("", posX.A.get(), posY.A.get() - 30, true));
		widgets.put("Leben1",
				createLabel("", posX.A.get() + 125, posY.A.get() - 30, true));
		widgets.put("Spielernamen2",
				createLabel("", posX.K.get(), posY.A.get() - 30, true));
		widgets.put("Leben2",
				createLabel("", posX.K.get() + 125, posY.A.get() - 30, true));
	}

	public Skyline getSkyline() {
		return skyline;
	}

	public void setSkyline(Skyline skyline) {
		this.skyline = skyline;
	}

	public void setWindOnOff(boolean windOnOff) {
		this.windOnOff = windOnOff;
	}

	public boolean isWindOnOff() {
		return windOnOff;
	}

}
// PopupWindow popup = new PopupWindow(widgets.get("DIALOG_OWNER"));
// DialogLayout dialog = new DialogLayout();
// DialogLayout.Group horizontalGroup = dialog.createSequentialGroup();
// DialogLayout.Group verticalGroup = dialog.createSequentialGroup();
// Label label = createLabel(
// "Herzlichen Glückwunsch, " + winner.getUsername(), 0, 0, true);
// Button button = createButton("OK", closeDialog(dialog), 120, 15);
// button.adjustSize();
// horizontalGroup.addWidgets(label, button);
// verticalGroup.addWidgets(label, button);
// dialog.setHorizontalGroup(horizontalGroup);
// dialog.setVerticalGroup(verticalGroup);
// popup.add(dialog);
// popup.setSize(400, 200);
// popup.setRequestCloseCallback(switchState(game, Gorillas.MAINMENUSTATE));
// popup.setPosition(Gorillas.FRAME_WIDTH / 2 - popup.getWidth() / 2,
// Gorillas.FRAME_HEIGHT / 2 - popup.getHeight() / 2);
// popup.openPopup();

//
// public Runnable closeDialog(DialogLayout dialog) {
// class close implements Runnable {
// DialogLayout dialog;
//
// public close(DialogLayout dialog) {
// this.dialog = dialog;
// }
//
// @Override
// public void run() {
// dialog.setEnabled(false);
// dialog.removeAllChildren();
// switchState(game, Gorillas.MAINMENUSTATE).run();
// container.setPaused(false);
// }
// }
// Runnable c = new close(dialog);
// return c;
// }
