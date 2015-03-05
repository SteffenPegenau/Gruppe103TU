package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.ComboBox;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.PropertySheet.ComboBoxEditorFactory;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.CombinedListModel;
import de.matthiasmann.twl.model.SimpleChangableListModel;
import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.PlayerList;
import eea.engine.entity.StateBasedEntityManager;

/**
 * @author Steffen Pegenau (steffen.pegenau@gmail.com)
 *
 */
public class PlayerSelectState extends ExtendedTWLState {
	GameSetupState setupState;
	private StateBasedGame game;
	private int playerArrayIndex;
	private SimpleChangableListModel<Player> model;
	private ComboBox<Player> box;
	private Player selectedPlayer;

	Button btnSelectPlayer;

	CombinedListModel<Player> listModel;
	ComboBoxEditorFactory<Player> factory;

	public PlayerSelectState(GameSetupState setupState, int sid, Player player,
			int playerArrayIndex, StateBasedGame game) {
		super(sid);
		if (player == null) {
			this.selectedPlayer = new Player();
		} else {
			this.selectedPlayer = player;
		}
		this.playerArrayIndex = playerArrayIndex;
		this.setupState = setupState;
		this.game = game;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);
		entityManager.addEntity(stateID, setBackground(null));
		System.out.println("Hello World!!!");
		widgets.put("LABEL_HELLOWORLD",
				createLabel("Hello WOrld", 400, 400, true));

		System.out.println("SIZE: " + widgets.size());
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer,
	 * org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		super.update(container, game, delta);

	}

	protected void layoutRootPane() {

		// int paneHeight = this.getRootPane().getHeight();
		// int paneWidth = this.getRootPane().getWidth();

	}

	private Runnable choosePlayer() {
		class Choose implements Runnable {
			private Player player;
			private int playerArrayIndex;
			private GameSetupState setupState;
			private StateBasedGame game;
			private PlayerSelectState playerSelectState;
			SimpleChangableListModel<Player> model;
			ComboBox<Player> box;

			public Choose(GameSetupState state, int index, StateBasedGame game,
					SimpleChangableListModel<Player> model, ComboBox<Player> box, PlayerSelectState playerSelectState) {
				this.playerArrayIndex = index;
				this.setupState = state;
				this.game = game;
				this.model = model;
				this.box = box;
				this.playerSelectState = playerSelectState;
			}

			@Override
			public void run() {
				int boxSelection = box.getSelected();
				if(boxSelection < 0) {
					System.err.println("Bad combo box selection!");
				} else {
					player = model.getEntry(boxSelection);
					if(player.getClass() != Player.class) {
						System.err.println("The selected value isn't a player!");
					} else {
						System.out.println("Try to save player " + player.getUsername()
								+ " in Slot " + playerArrayIndex);
						setupState.setPlayer(player, playerArrayIndex);
						
						System.out.println("Try to change to state "
								+ setupState.getID());
						game.enterState(setupState.getID());
					}
				}
			}

		}
		Runnable c = new Choose(setupState, playerArrayIndex, game, model, box, this);
		return c;

	}

	private ArrayList<Player> getPlayers() {
		PlayerList playerList = PlayerList.restorePlayerList();
		return playerList.getPlayers();
	}

	private Widget createComboBox(int posX, int posY) {
		model = new SimpleChangableListModel<Player>(getPlayers());
		box = new ComboBox<Player>(model);
		box.setDisplayTextNoSelection("Spieler wählen:");
		box.setPosition(posX, posY);
		box.setSize(400, 30);
		box.addCallback(playerSelectedInComboBoxEvent());
		return box;
	}

	protected Runnable playerSelectedInComboBoxEvent() {
		class playerSelected implements Runnable {
			ComboBox<Player> box;
			SimpleChangableListModel<Player> model;
			Player player;
			Map<String, Widget> widgets;

			public playerSelected(ComboBox<Player> box,
					SimpleChangableListModel<Player> model, Player player,
					Map<String, Widget> widgets) {
				this.box = box;
				this.model = model;
				this.player = player;
				this.widgets = widgets;
			}

			@Override
			public void run() {
				int boxSelection = box.getSelected();
				if (boxSelection < 0) {

				} else {
					player = model.getEntry(boxSelection);
					EditField editUsername = (EditField) widgets
							.get("EDIT_USERNAME");
					EditField editFullname = (EditField) widgets
							.get("EDIT_FULLNAME");

					editUsername.setText(player.getUsername());
					editFullname.setText(player.getFullname());
				}
			}
		}
		Runnable r = new playerSelected(box, model, selectedPlayer, widgets);
		return r;
	}

	protected Runnable deletePlayerClick() {
		class deletePlayer implements Runnable {
			private Map<String, Widget> widgets;
			private SimpleChangableListModel<Player> model;
			private ComboBox<Player> box;

			public deletePlayer(Map<String, Widget> widgets,
					SimpleChangableListModel<Player> model, ComboBox<Player> box) {
				this.widgets = widgets;
				this.model = model;
				this.box = box;
			}

			@Override
			public void run() {
				model.removeElement(box.getSelected());
				PlayerList.savePlayerList(model);
				box.setModel(model);
				EditField editUsername = (EditField) widgets.get("EDIT_USERNAME");
				editUsername.setText("");
				EditField editFullname = (EditField) widgets.get("EDIT_FULLNAME");
				editFullname.setText("");
			}

		}
		Runnable run = new deletePlayer(widgets, model, box);
		return run;
	}

	protected Runnable savePlayerClick() {
		class createNewPlayer implements Runnable {
			private Map<String, Widget> widgets;
			private SimpleChangableListModel<Player> model;

			public createNewPlayer(Map<String, Widget> widgets,
					SimpleChangableListModel<Player> model) {
				this.widgets = widgets;
				this.model = model;
			}

			@Override
			public void run() {
				EditField editFieldUsername = (EditField) widgets
						.get("EDIT_USERNAME");
				editFieldUsername.setEnabled(false);
				String username = editFieldUsername.getText();
				EditField editFieldFullname = (EditField) widgets
						.get("EDIT_FULLNAME");
				String fullname = editFieldFullname.getText();
				Player newPlayer = new Player(username, fullname);
				model.addElement(newPlayer);
				box.setSelected(model.findElement(newPlayer));
				PlayerList.savePlayerList(model);
			}

		}
		Runnable run = new createNewPlayer(widgets, model);
		return run;
	}

	protected Runnable newPlayerClick() {
		class createNewPlayer implements Runnable {
			private Map<String, Widget> widgets;

			public createNewPlayer(Map<String, Widget> widgets) {
				this.widgets = widgets;
			}

			@Override
			public void run() {
				for (HashMap.Entry<String, Widget> entry : widgets.entrySet()) {
					if (entry.getKey().contains("EDIT_")) {
						EditField field = (EditField) entry.getValue();
						field.setText("Eingabe");
						field.setEnabled(true);
					}
				}
			}

		}
		Runnable run = new createNewPlayer(widgets);
		return run;
	}

	/**
	 * In dieser Methode werden in einem BasicTWLGameSate alle GUI-Elemente dem
	 * GameState mit Hilfe einer RootPane hinzugef�gt
	 */
	protected RootPane createRootPane() {
		// erstelle die RootPane
		rootPane = super.createRootPane();

		entityManager.addEntity(stateID, setBackground(DEFAULT_BACKGROUND));

		widgets.put("COMBOBOX_PLAYER_SELECT",
				createComboBox(posX.A.get(), posY.A.get()));

		widgets.put("LABEL_USERNAME",
				createLabel("Username:", posX.A.get(), posY.D.get(), true));
		widgets.put("EDIT_USERNAME",
				createEditField(posX.C.get(), posY.D.get(), false));
		widgets.put("LABEL_FULLNAME",
				createLabel("Ganzer Name:", posX.A.get(), posY.F.get(), true));
		widgets.put("EDIT_FULLNAME",
				createEditField(posX.C.get(), posY.F.get(), false));

		widgets.put(
				"BTN_SAVE_PLAYER_TO_LIST",
				createButton("Spieler speichern", savePlayerClick(),
						posX.M.get(), posY.C.get()));
		widgets.put(
				"BTN_DELETE_PLAYER",
				createButton("Spieler Löschen", deletePlayerClick(),
						posX.M.get(), posY.E.get()));
		widgets.put(
				"BTN_NEW_PLAYER",
				createButton("Neuer Spieler", newPlayerClick(), posX.M.get(),
						posY.G.get()));
		widgets.put(
				"BTN_CHOOSE_SELECTED_PLAYER",
				createButton("Spieler wählen", choosePlayer(), posX.M.get(),
						posY.T.get()));

		addAllWidgetsToRootPane(widgets);
		return rootPane;
	}
}
