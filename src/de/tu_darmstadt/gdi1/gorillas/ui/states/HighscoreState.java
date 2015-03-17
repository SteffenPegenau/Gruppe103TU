package de.tu_darmstadt.gdi1.gorillas.ui.states;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.Widget;
import de.tu_darmstadt.gdi1.gorillas.main.Serializer;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.PlayerList;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.QuitAction;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.basicevents.MouseClickedEvent;
import eea.engine.event.basicevents.MouseEnteredEvent;

/**
 * @author Steffen Pegenau (steffen.pegenau@gmail.com)
 *
 */
public class HighscoreState extends ExtendedTWLState {

	public HighscoreState(int sid) {
		super(sid);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		entityManager.addEntity(stateID, setBackground(DEFAULT_BACKGROUND));
		entityManager.renderEntities(container, game, g);
		g.drawString("Exit vs. Warum werde ich angezeigt?", 500, 530); // Grau
																		// hinterlegen
																		// +
																		// Anzeigename
		Action action = new ChangeStateAction(Gorillas.MAINMENUSTATE);
		createButton("exit", "Warum werde ich nicht angezeigt?", action, 500,
				530);
		entityManager
				.addEntity(stateID, setESCListener(Gorillas.MAINMENUSTATE));

		PlayerList pl = new PlayerList();
		ArrayList<Player> hl = new ArrayList<Player>();
		hl = pl.highscoreList(PlayerList.restorePlayerList()
				.getPlayersAsHashMap());
		StringBuilder sb = new StringBuilder(100);

	}
	
	protected RootPane createRootPane() {
		// erstelle die RootPane
		RootPane rp = super.createRootPane();
		
		PlayerList pl = new PlayerList();
		ArrayList<Player> hl = new ArrayList<Player>();
		hl = pl.highscoreList(PlayerList.restorePlayerList()
				.getPlayersAsHashMap());
		StringBuilder sb = new StringBuilder(500);

		widgets.put(
				"FIRST_HIGHSCORE_LABEL",
				createLabel(
						"1. "
								+ hl.get(0).getUsername() + "   " 
								+ sb.append(hl.get(0).getWonRounds())
										.toString(), 50, 50, true));
		widgets.put(
				"SECOND_HIGHSCORE_LABEL",
				createLabel(
						"2. "
								+ hl.get(1).getUsername() + "    "
								+ sb.append(hl.get(0).getWonRounds())
										.toString(), 50, 150, true));
		widgets.put(
				"THIRD_HIGHSCORE_LABEL",
				createLabel(
						"3. "
								+ hl.get(2).getUsername() + "    "
								+ sb.append(hl.get(0).getWonRounds())
										.toString(), 50, 250, true));
		
		
		addAllWidgetsToRootPane(widgets);
		return rp;
		
	}
}
