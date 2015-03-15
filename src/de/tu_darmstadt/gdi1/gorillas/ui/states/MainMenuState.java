package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.QuitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class MainMenuState extends ExtendedTWLState {
	private Image menu_entry_background = null;

	private final int distance = 110;
	private final int startPosition = 90;
	
	private ArrayList<String> menuentries = new ArrayList<String>();

	public MainMenuState(int sid) {
		super(sid);
	}

	protected void createButton(String name, String title, Action action, int x, int y) {
		Entity button = new Entity(name);
		button.setPosition(new Vector2f(x, y));
		button.setScale(0.28f);
		
		if(menu_entry_background == null) {
			try {
				menu_entry_background = new Image("assets/gorillas/button/entry.png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		button.addComponent(new ImageRenderComponent(menu_entry_background));

		// Erstelle das Ausloese-Event und die zugehoerige Action
		ANDEvent mainEventsQ = new ANDEvent(new MouseEnteredEvent(),
				new MouseClickedEvent());
		mainEventsQ.addAction(action);
		button.addComponent(mainEventsQ);
		menuentries.add(title);
		entityManager.addEntity(stateID, button);
	}

	private void drawMenu() {
		Action action;
		int counter = 0;
		
		action = new ChangeStateAction(Gorillas.GAMESETUPSTATE);
		createButton("newGameWithSteup", "Neues Spiel mit Setup", action, 220, startPosition + counter * distance);
		
		counter++;
		action = new ChangeStateAction(Gorillas.HIGHSCORESTATE);
		createButton("highscore", "Highscore", action, 220, startPosition + counter * distance);
		
		counter++;
		action = new ChangeStateAction(Gorillas.OPTIONSTATE);
		createButton("options", "Optionen", action, 220, startPosition + counter * distance);
		
		counter++;
		action = new ChangeStateAction(Gorillas.INSTRUCTIONSSTATE);
		createButton("instructions", "Instruktionen", action, 220, startPosition + counter * distance);

		createButton("exit", " ", new QuitAction(), 600, 540);
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);
		addKeyPressedEvent(Input.KEY_N, new ChangeStateAction(Gorillas.GAMESETUPSTATE));
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {

		entityManager.updateEntities(container, game, delta);
		
	}

	private void drawButtonLabels(Graphics g) {
		int counter = 0;
		
		for(String title : menuentries) {
			g.drawString(title, 110, startPosition + counter * distance - 10);
			counter++;
		}
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);
		entityManager.renderEntities(container, game, g);
		entityManager.addEntity(stateID, setBackground(DEFAULT_BACKGROUND));
		drawMenu();
		drawButtonLabels(g);
		g.drawString("Exit", 500, 530);
		
	}
	
	@Override
	protected RootPane createRootPane() {
		RootPane rp = super.createRootPane();
		//addKeyPressedEvent(Input.KEY_N, new ChangeStateAction(Gorillas.GAMESETUPSTATE));
		return rp;
	}

	@Override
	protected void layoutRootPane() {

		//int paneHeight = this.getRootPane().getHeight();
		//int paneWidth = this.getRootPane().getWidth();

	}
}