package de.tu_darmstadt.gdi1.gorillas.ui.states;



import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
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
import eea.engine.entity.StateBasedEntityManager;
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
		g.drawString("Exit", 500, 530);
		
		entityManager.addEntity(stateID, setESCListener(Gorillas.MAINMENUSTATE));
	}

	private int positionX;
	private int positionY;
	/**
	 * In dieser Methode werden in einem BasicTWLGameSate alle GUI-Elemente dem
	 * GameState mit Hilfe einer RootPane hinzugef�gt
	 */
	
	
	private Label HighScore;
	private Label Label1;
	private Label Label2;
	private Label Label3;
	private Label Label4;
	private Label Label5;
	private Label Label6;
	private Label Label7;
	private Label Labels[] = new Label[10];
	
	PlayerList allPlayers = new PlayerList();
	
	public List<Player> getAllPlayers() {
		return allPlayers.getAllPlayers();
		//allPlayers.restorePlayerList();
	}
	
	
	
	
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
	
	
	}

	
	


