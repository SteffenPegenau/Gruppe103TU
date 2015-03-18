package de.tu_darmstadt.gdi1.gorillas.ui.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import de.matthiasmann.twl.slick.RootPane;
import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.PlayerList;

/**
 * @author Steffen Pegenau (steffen.pegenau@gmail.com)
 *
 */
public class HighscoreState extends ExtendedTWLState {
	
	// Definiert die X-Positionen der einzelnen Spalten der Tabelle
	private final static int COLUMN_RANKING = posX.A.get();
	private final static int COLUMN_USERNAME = posX.B.get();
	private final static int COLUMN_ROUNDS_PLAYED = posX.E.get();
	private final static int COLUMN_ROUNDS_WON = posX.H.get();
	private final static int COLUMN_PLAYED_WON_RATIO = posX.K.get();
	private final static int COLUMN_ACCURACY = posX.M.get();
	
	private final static int ROW_HEIGHT = 30;
	

	public HighscoreState(int sid) {
		super(sid);
	}
	
	protected RootPane createRootPane() {
		// erstelle die RootPane
		RootPane rp = super.createRootPane();
		

		entityManager.addEntity(stateID, setBackground(DEFAULT_BACKGROUND));
		addESCListener(Gorillas.MAINMENUSTATE);
		
		createTableHeader();
		createTableContent();
		addAllWidgetsToRootPane(widgets);
		return rp;
		
	}

	protected void createTableHeader() {
		String prefix = "LABEL_CAPTION";
		widgets.put(prefix + "_RANKING", createLabel("Platz", COLUMN_RANKING, posY.A.get(), true));
		widgets.put(prefix + "_USERNAME", createLabel("Name", COLUMN_USERNAME, posY.A.get(), true));
		widgets.put(prefix + "_ROUNDS_PLAYED", createLabel("Runden gespielt", COLUMN_ROUNDS_PLAYED, posY.A.get(), true));
		widgets.put(prefix + "_ROUNDS_WON", createLabel("Runden gewonnen", COLUMN_ROUNDS_WON, posY.A.get(), true));
		widgets.put(prefix + "_PLAYED_WON_RATIO", createLabel("Verhältnis", COLUMN_PLAYED_WON_RATIO, posY.A.get(), true));
		widgets.put(prefix + "_THROWS_FOR_HIT", createLabel("Würfe/Treffer", COLUMN_ACCURACY, posY.A.get(), true));
	}
	
	protected void createTableContent() {
		Player[] highscore = PlayerList.getHighscore();
		
		int y = posY.C.get();
		for (int i = 0; i < highscore.length; i++) {
			Player p = highscore[i];
			String rank = String.valueOf(i + 1);
			String roundsPlayed = String.valueOf(p.getRoundsPlayed());
			String roundsWon = String.valueOf(p.getWonRounds());
			String ratio = String.valueOf(p.getPercentageWon());
			String throwsForHit = String.valueOf(p.getThrowsForAHit());
			
			
			String prefix = "LABEL_RANK" + i;
			widgets.put(prefix + "_RANKING", createLabel(rank, COLUMN_RANKING, y, true));
			widgets.put(prefix + "_USERNAME", createLabel(p.getUsername(), COLUMN_USERNAME, y, true));
			widgets.put(prefix + "_ROUNDS_PLAYED", createLabel(roundsPlayed, COLUMN_ROUNDS_PLAYED, y, true));
			widgets.put(prefix + "_ROUNDS_WON", createLabel(roundsWon, COLUMN_ROUNDS_WON, y, true));
			widgets.put(prefix + "_PLAYED_WON_RATIO", createLabel(ratio, COLUMN_PLAYED_WON_RATIO, y, true));
			widgets.put(prefix + "_THROWS_FOR_HIT", createLabel(throwsForHit, COLUMN_ACCURACY, y, true));
			y += ROW_HEIGHT;
		}
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		super.init(container, game);
		
	}
}
