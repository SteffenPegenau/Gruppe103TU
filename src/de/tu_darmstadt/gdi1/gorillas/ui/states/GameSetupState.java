package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.util.Map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.main.Serializer;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import eea.engine.entity.StateBasedEntityManager;


/**
 * @author Steffen Pegenau (steffen.pegenau@gmail.com)
 *
 */
public class GameSetupState extends ExtendedTWLState {
	private Player players[] = new Player[2];
	
	public GameSetupState(int sid) {
		super(sid);
	}	
	
	private Runnable switchToPlayerSelectState(Player player, int arrayIndex) {
		class switcher implements Runnable {
			private StateBasedGame game;
			private GameSetupState state;
			private Player player;
			private int arrayIndex;
			private final static int SID = Gorillas.PLAYERSELECTSTATE;
			
			public switcher(GameSetupState s, StateBasedGame game, Player player, int arrayIndex) {
				this.game = game;
				this.player = player;
				this.arrayIndex = arrayIndex;
				this.state = s;
			}
			
			@Override
			public void run() {
				PlayerSelectState selectState = new PlayerSelectState(state, SID, player, arrayIndex, game);
				//game.init(container);
				game.addState(selectState);
				StateBasedEntityManager.getInstance().addState(SID);
				game.enterState(SID);
			}
		}
		switcher s = new switcher(this, game, player, arrayIndex);
		return s;
	}
	
	private void tryToRestoreSelectedPlayers() {
		Player p;
		
		for (int i = 0; i < players.length; i++) {
			players[i] = p = (Player) Serializer.restore(new Player(), "selectedPlayer" + i);
			if(p != null && p.getClass() == Player.class && !p.getUsername().isEmpty()) {
				setPlayersName(players[i], i);
			}
		}
		
	}
	
	private void saveSelectedPlayers() {
		Player p;
		for (int i = 0; i < players.length; i++) {
			p = players[i];
			if (p != null && p.getClass() == Player.class && !p.getUsername().isEmpty()) {
				Serializer.save(p, "selectedPlayer" + i);
			}
			
		}
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);
		int y = 0;

		for(int i = 0; i <= 1; i++) {
			players[i] = null;
			y = startPosition + i * distance;
			widgets.put("LABELPLAYER" + i, createLabel("Spieler " + (i + 1), BUTTON_LEFT_X, y, true));
			widgets.put("PLAYERNAME" + i, createLabel("PLAYERNAME" + i, BUTTON_MIDDLELEFT_X, y, false));
			widgets.put("CHOOSEPLAYERNAME" + i, createButton("Spieler wählen", switchToPlayerSelectState(players[i], i), BUTTON_RIGHT_X, y));
		}
		tryToRestoreSelectedPlayers();
		widgets.put("BUTTON_BACKTOMAINMENU", createButton("Zurück", switchState(game, Gorillas.MAINMENUSTATE), BUTTON_LEFT_X, BUTTON_LAST_LINE_Y));
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);
		//drawPlayerSelectWidgets();
	}
	
	protected void layoutRootPane() {
		//int paneHeight = this.getRootPane().getHeight();
		//int paneWidth = this.getRootPane().getWidth();

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
}
