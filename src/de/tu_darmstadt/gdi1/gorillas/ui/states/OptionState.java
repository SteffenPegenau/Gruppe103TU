package de.tu_darmstadt.gdi1.gorillas.ui.states;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

public class OptionState extends ExtendedTWLState {
	
	
	Image  layer_underneath = null;
	Image menu_entry_background = null;
	
	public OptionState(int sid) {
		super(sid);
	}
	
	protected void createButton() {
		Entity button = new Entity("exit");
		button.setPosition(new Vector2f(600, 540));
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
		mainEventsQ.addAction(new ChangeStateAction(Gorillas.MAINMENUSTATE));
		button.addComponent(mainEventsQ);
		entityManager.addEntity(stateID, button);
		
		Entity layer = new Entity("LAYER");
		layer.setPosition(new Vector2f(370, 240));
		layer.setScale(0.95f);
		
		if(layer_underneath == null) {
			try {
				layer_underneath = new Image("assets/gorillas/backgrounds/Frame.png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
		layer.addComponent(new ImageRenderComponent(layer_underneath));
		entityManager.addEntity(stateID, layer);		
	
	}

	
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		super.render(container, game, g);
		
		entityManager.addEntity(stateID, setBackground(DEFAULT_BACKGROUND));
		
		createButton();
		
		entityManager.renderEntities(container, game, g);
		
		
		g.setColor(Color.cyan);
		g.scale(1.5f, 1.5f);
		g.drawString("Die Mitglieder der Gruppe 103 sind: \n Steffen, Sabina, Len und Adam!\n"
				+ "    Viel Spaß beim Spielen!", 100, 100);
		
		
		g.scale(1.0f / 1.5f, 1.0f / 1.5f);
		
		g.drawString("Exit", 500, 530);
		entityManager.addEntity(stateID, setESCListener(Gorillas.MAINMENUSTATE));
	}

	
	 // In dieser Methode werden in einem BasicTWLGameSate alle GUI-Elemente dem
	 // GameState mit Hilfe einer RootPane hinzugef�gt
	 
	protected RootPane createRootPane() {
		// erstelle die RootPane
		RootPane rp = super.createRootPane();
		return rp;
	}

}

