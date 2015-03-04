package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.ComboBox;
import de.matthiasmann.twl.PropertySheet.ComboBoxEditorFactory;
import de.matthiasmann.twl.model.CombinedListModel;
import de.matthiasmann.twl.model.SimpleChangableListModel;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import eea.engine.entity.StateBasedEntityManager;

/**
 * @author Steffen Pegenau (steffen.pegenau@gmail.com)
 *
 */
public class PlayerSelectState extends ExtendedTWLState {
	private int stateID;
	private StateBasedEntityManager entityManager;
	private StateBasedGame game;
	
	GameSetupState setupState;
	
	private int playerArrayIndex;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private SimpleChangableListModel<Player> model;
	private ComboBox<Player> box;
	private Player player;
	
	Button btnSelectPlayer;
	
	CombinedListModel<Player> listModel;
	ComboBoxEditorFactory<Player> factory;
	
	public PlayerSelectState(GameSetupState setupState, int sid, Player player, int playerArrayIndex, StateBasedGame game) {
		stateID = sid;
		entityManager = StateBasedEntityManager.getInstance();
		this.player = player;
		this.playerArrayIndex = playerArrayIndex;
		this.setupState = setupState;
		this.game = game;
	}	
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		entityManager.addEntity(stateID, MainMenuState.background());
		this.game = game;
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		entityManager.renderEntities(container, game, g);
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		entityManager.updateEntities(container, game, delta);
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	@Override
	public int getID() {
		return stateID;
	}
	
	protected void layoutRootPane() {

		//int paneHeight = this.getRootPane().getHeight();
		//int paneWidth = this.getRootPane().getWidth();

	}
	
	private Runnable choosePlayer() {
		class Choose implements Runnable {
			private Player player;
			private int playerArrayIndex;
			private GameSetupState setupState;
			private ComboBox<Player> box;
			private SimpleChangableListModel<Player> model;
			private StateBasedGame game;
			
			public Choose(GameSetupState state, SimpleChangableListModel<Player> model,
					ComboBox<Player> box, int index, StateBasedGame game) {
				playerArrayIndex = index;
				setupState = state;
				this.model = model;
				this.box = box;
				this.game = game;
			}
			@Override
			public void run() {
				player = model.getEntry(box.getSelected());
				setupState.setPlayer(player, playerArrayIndex);
				System.out.println("Try to change to state " + setupState.getID());
				game.enterState(setupState.getID());
			}
			
		}
		Runnable c = new Choose(setupState, model, box, playerArrayIndex, game);
		return c;
		
	}
	

	/**
	 * In dieser Methode werden in einem BasicTWLGameSate alle GUI-Elemente dem
	 * GameState mit Hilfe einer RootPane hinzugef�gt
	 */
	protected RootPane createRootPane() {
		// erstelle die RootPane
		entityManager.addEntity(stateID, MainMenuState.background());
		RootPane rp = super.createRootPane();
		
		playerList.add(new Player("Steffen"));
		playerList.add(new Player("dooferAffe"));
		
		model = new SimpleChangableListModel<Player>(playerList); 
		box = new ComboBox<Player>(model);
		box.setPosition(50, 50);
		box.setSize(500, 50);
		
		rp.add(box);
		
		btnSelectPlayer = createButton("Speichern", choosePlayer(), BUTTON_LEFT_X, BUTTON_LAST_LINE_Y);
		rp.add(btnSelectPlayer);
		
		return rp;
	}
}
