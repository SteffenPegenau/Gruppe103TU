package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.Serializer;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.Bullet;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.PlayerList;
import eea.engine.entity.StateBasedEntityManager;

/**
 * @author Steffen Pegenau (steffen.pegenau@gmail.com)
 *
 */
public class GameSetupState extends ExtendedTWLState {
	private Player players[] = new Player[2];
	
	// true um Wind anzuschalten
	private boolean windOn = false;

	public GameSetupState(int sid) {
		super(sid);
	}

	public int rounds;

	public void setRounds(int r) {
		rounds = r;
	}

	public int getRounds() {
		return rounds;
	}

	private Runnable switchToPlayerSelectState(Player player, int arrayIndex) {
		class switcher implements Runnable {
			private StateBasedGame game;
			private GameSetupState state;
			private Player player;
			private int arrayIndex;
			private final static int SID = Gorillas.PLAYERSELECTSTATE;

			public switcher(GameSetupState s, StateBasedGame game,
					Player player, int arrayIndex) {
				this.game = game;
				this.player = player;
				this.arrayIndex = arrayIndex;
				this.state = s;

			}

			@Override
			public void run() {
				PlayerSelectState selectState = new PlayerSelectState(state,
						SID, player, arrayIndex, game);
				game.addState(selectState);
				StateBasedEntityManager.getInstance().addState(SID);
				game.enterState(SID);
			}
		}
		switcher s = new switcher(this, game, player, arrayIndex);
		return s;
	}

	public Runnable startGame() {
		class switcher implements Runnable {
			private Player[] players = new Player[2];
			private StateBasedGame game;
			private GameContainer container;

			public switcher(StateBasedGame game, Player[] players,
					GameContainer container) {
				this.game = game;
				this.players = players;
				this.container = container;
			}

			@Override
			public void run() {
				if (players[0] != null && players[1] != null
						&& PlayerList.usernamesOkay(players)) {
					int rounds = getEnteredNumberOfRounds();
					double gravityInput = getEnteredGravity();
					boolean windState = isWindOn();
					if (PlayerList.usernamesOkay(players) && rounds > 0
							&& gravityInput >= 0) {
						players[0].addRoundPlayed();
						players[1].addRoundPlayed();

						GameplayState gamePlayState = new GameplayState(
								Gorillas.GAMEPLAYSTATE, players, rounds,
								gravityInput, windState);
						game.addState(gamePlayState);
						StateBasedEntityManager.getInstance().addState(
								Gorillas.GAMEPLAYSTATE);

						try {
							gamePlayState.init(container, game);
						} catch (SlickException e) {
							e.printStackTrace();
						}

						game.enterState(Gorillas.GAMEPLAYSTATE);

					}
				}
			}
		}
		switcher s = new switcher(game, players, container);
		return s;
	}

	/**
	 * Ermittelt die Eingabe bei Anzahl der Runden
	 * 
	 * @return -1, wenn leer, sonst: Eingebene Zahl
	 */
	public int getEnteredNumberOfRounds() {
		EditField roundsEdit = (EditField) widgets.get("EDIT_NR_OF_ROUNDS");
		String input = roundsEdit.getText();
		if (input.isEmpty()) {
			return -1;
		} else {
			return Integer.valueOf(input);
		}
	}
// TODO : 
	public boolean windOn() {
		return true;
	}
	public boolean windOff() {
		return false;
	}
	/**
	 * Ermittelt die Eingabe bei Anzahl der Gravitation
	 * 
	 * @return -1, wenn leer, sonst: Eingebene Zahl
	 */
	public double getEnteredGravity() {
		EditField gravityEdit = (EditField) widgets.get("EDITGRAVITY");
		String input = gravityEdit.getText();
		if (input.isEmpty()) {
			return -1;
		} else {
			return Integer.valueOf(input);
		}

	}

	private void tryToRestoreSelectedPlayers() {
		Player p;

		for (int i = 0; i < players.length; i++) {
			players[i] = p = (Player) Serializer.restore(new Player(),
					"selectedPlayer" + i);
			if (p != null && p.getClass() == Player.class
					&& !p.getUsername().isEmpty()) {
				setPlayersName(players[i], i);
			}
		}

	}

	private void saveSelectedPlayers() {
		Player p;
		for (int i = 0; i < players.length; i++) {
			p = players[i];
			if (p != null && p.getClass() == Player.class
					&& !p.getUsername().isEmpty()) {
				Serializer.save(p, "selectedPlayer" + i);
			}

		}
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);

	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);
		entityManager.addEntity(stateID, setBackground(DEFAULT_BACKGROUND));
		addESCListener(Gorillas.GAMEPLAYSTATE);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		if (players[0] != null && players[1] != null) {
			if (!PlayerList.usernamesOkay(players)) {
				widgets.get("ERRMSGLABEL").setVisible(true);
			} else {
				widgets.get("ERRMSGLABEL").setVisible(false);
			}

		}
		// drawPlayerSelectWidgets();
	}

	protected void layoutRootPane() {
		// int paneHeight = this.getRootPane().getHeight();
		// int paneWidth = this.getRootPane().getWidth();

	}

	private void setPlayersName(Player p, int arrayIndex) {
		// Get the right widget
		Label label = (Label) widgets.get("PLAYERNAME" + arrayIndex);
		label.setText(p.getUsername());
		label.setVisible(true);
	}

	public void setPlayer(Player p, int arrayIndex) {
		players[arrayIndex] = p;
		setPlayersName(p, arrayIndex);
		saveSelectedPlayers();
	}

	public Player getPlayer(int arrayIndex) {
		if (arrayIndex == 0 || arrayIndex == 1) {
			return players[arrayIndex];
		} else {
			return null;
		}
	}
	
	public Runnable setWindOnOff(boolean windSetter) {
		class changer implements Runnable {
			boolean windSetter;

			public changer(boolean windSetter) {
				this.windSetter = windSetter;
			}

			@Override
			public void run() {
				windOn = windSetter;
				System.out.println("set wind to " + windSetter);
			}
			
		}
		Runnable c = new changer(windSetter);
		return c;
	}

	/**
	 * In dieser Methode werden in einem BasicTWLGameSate alle GUI-Elemente dem
	 * GameState mit Hilfe einer RootPane hinzugef�gt
	 */
	protected RootPane createRootPane() {
		// erstelle die RootPane
		RootPane rp = super.createRootPane();

		int y = 0;

		for (int i = 0; i <= 1; i++) {
			players[i] = null;
			y = startPosition + i * distance;
			widgets.put("LABELPLAYER" + i,
					createLabel("Spieler " + (i + 1), BUTTON_LEFT_X, y, true));
			widgets.put(
					"PLAYERNAME" + i,
					createLabel("PLAYERNAME" + i, BUTTON_MIDDLELEFT_X, y, false));
			widgets.put(
					"CHOOSEPLAYERNAME" + i,
					createButton("Spieler wählen",
							switchToPlayerSelectState(players[i], i),
							BUTTON_RIGHT_X, y));
		}
		tryToRestoreSelectedPlayers();
		// Rundenanzahl
		widgets.put("LABEL_NR_OF_ROUNDS",
				createLabel("Rundenanzahl: ", BUTTON_LEFT_X, 200, true));
		widgets.put("EDIT_NR_OF_ROUNDS",
				createEditField(BUTTON_LEFT_X + 120, 200, true, "3"));
		addNumberInputCheck((EditField) widgets.get("EDIT_NR_OF_ROUNDS"), 10);
		widgets.put("ERRMSGLABEL",
				createLabel("Please Check Username", 200, 300, false));
		widgets.put("GRAVITYLABEL",
				createLabel("Gravity: ", BUTTON_LEFT_X, 250, true));
		widgets.put("EDITGRAVITY",
				createEditField(BUTTON_LEFT_X + 120, 250, true, "10"));
		addNumberInputCheck((EditField) widgets.get("EDITGRAVITY"), 30);

		widgets.put("BTN_WINDON", createButton("Wind On", setWindOnOff(true), BUTTON_LEFT_X + 150, 400));
		widgets.put("BTN_WINDOFF", createButton("wind Off", setWindOnOff(false), BUTTON_LEFT_X + 350, 400));
		
		// TODO: Wind ein aus
//		widgets.put("EDITWIND",
//				createEditField(BUTTON_LEFT_X + 150, 400, true, "0"));
//		addNumberInputCheck((EditField) widgets.get("EDITWIND"), 1);
		// EditField windInput = (EditField) widgets.get("EDITWIND");
		// String windInputString = windInput.getText();
		// if (Integer.parseInt(windInputString) == 0) {
		// Bullet.setWindSOff();
		// } else {
		// Bullet.setWindSOn();
		// }

		widgets.put(
				"BUTTON_BACKTOMAINMENU",
				createButton("Zurück",
						switchState(game, Gorillas.MAINMENUSTATE),
						BUTTON_LEFT_X, BUTTON_LAST_LINE_Y));
		widgets.put(
				"BUTTON_START_GAME",
				createButton("Spiel starten", startGame(), BUTTON_RIGHT_X,
						BUTTON_LAST_LINE_Y));
		addAllWidgetsToRootPane(widgets);
		return rp;
	}

	public boolean isWindOn() {
		return windOn;
	}

	public void setWindOn(boolean windState) {
		this.windOn = windState;
	}
}
