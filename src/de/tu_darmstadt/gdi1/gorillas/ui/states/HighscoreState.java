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
		g.drawString("Exit vs. Warum werde ich angezeigt?", 500, 530); // Grau hinterlegen, und warum wird nicht der String in der Folgenden zeile ausgeführt..?
		Action action = new ChangeStateAction(Gorillas.MAINMENUSTATE);
		createButton("exit", "Warum werde ich nicht angezeigt?", action, 500, 530);
		entityManager.addEntity(stateID, setESCListener(Gorillas.MAINMENUSTATE));
		
		// Aufbauidee
		g.drawString("Username", posX.A.get(), posY.A.get());
		g.drawString("Played R.", posX.A.get() + 100, posY.A.get());
		g.drawString("Won R.", posX.A.get() + 200, posY.A.get());
		g.drawString("%-Won R.", posX.A.get() + 300, posY.A.get());
		g.drawString("Throw accuracy", posX.A.get() + 400, posY.A.get());
		
		g.drawString("1. ", posX.A.get() - 15, posY.A.get() + 25);
		g.drawString("2. ", posX.A.get() - 15, posY.A.get() + 45);
		g.drawString("3. ", posX.A.get() - 15, posY.A.get() + 65);
		g.drawString("4. ", posX.A.get() - 15, posY.A.get() + 85);
		g.drawString("5. ", posX.A.get() - 15, posY.A.get() + 105);
		g.drawString("6. ", posX.A.get() - 15, posY.A.get() + 125);
		g.drawString("7. ", posX.A.get() - 15, posY.A.get() + 145);
		g.drawString("8. ", posX.A.get() - 15, posY.A.get() + 165);
		g.drawString("9. ", posX.A.get() - 15, posY.A.get() + 185);
		g.drawString("10.", posX.A.get() - 15, posY.A.get() + 205);

	}
	
	
	

}
	
	





/**
 * In dieser Methode werden in einem BasicTWLGameSate alle GUI-Elemente dem
 * GameState mit Hilfe einer RootPane hinzugef�gt
 */



//PlayerList allPlayers = new PlayerList();
//
//public List<Player> getAllPlayers() {
//	return allPlayers.getAllPlayers();
	//allPlayers.restorePlayerList();
	
	
	/*protected RootPane createRootPane() {
		RootPane rp = super.createRootPane();
		
		for(int i=0; i<getAllPlayers().size(); i++) {
		Player thisPlayer = allPlayers.getAllPlayers().remove(i);
		positionX = 300;
		positionY = positionY + 100;
		
		
		Labels[i] = new Label (thisPlayer.getFullname());
		Labels[i].adjustSize();
		Labels[i].setSize(100, 50);
		Labels[i].setPosition(positionX, positionY);
		//HighScore = new Label ("HighScore");
		//HighScore.adjustSize();
		//HighScore.setSize(200, 100);
		//HighScore.setPosition(370, 0);
		
		rp.add(Labels[i]);
		return rp; */
	
	
//	}

	
	


