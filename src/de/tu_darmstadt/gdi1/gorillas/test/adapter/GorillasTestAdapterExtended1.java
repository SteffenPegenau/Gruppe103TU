package de.tu_darmstadt.gdi1.gorillas.test.adapter;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

import de.tu_darmstadt.gdi1.gorillas.main.Gorillas;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.Gorilla;
import de.tu_darmstadt.gdi1.gorillas.mapobjects.Skyline;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.Player;
import de.tu_darmstadt.gdi1.gorillas.mapobjectsowners.PlayerList;
import de.tu_darmstadt.gdi1.gorillas.ui.states.GameplayState;
import eea.engine.entity.StateBasedEntityManager;

public class GorillasTestAdapterExtended1 extends GorillasTestAdapterMinimal {
	public Skyline skyline;

	public GorillasTestAdapterExtended1() {
		super();
	}

	@Override
	public void rememberGameData() {

		// TODO: Implement

		super.rememberGameData();
	}

	@Override
	public void restoreGameData() {

		// TODO: Implement

		super.restoreGameData();
	}

	/**
	 * This method should create a new RANDOM map. The map should consist of the
	 * coordinates of the left and the right gorilla and the upper left edges of
	 * the buildings of the skyline. The gorilla positions should mark the
	 * center of each gorilla. The buildings of the skyline are rectangular, and
	 * not higher than <code>frameHeight</code> minus 100.
	 * 
	 * Important: The y coordinate of the gorillas and the buildings should be
	 * denoted with the y axis pointing downwards, as slick demands this.
	 * 
	 * The left gorilla has to be placed on the first, second or third building
	 * from the left. Accordingly, the right gorilla has to be placed on the
	 * first, second or third building from the right.
	 * 
	 * Gorillas should be placed in the middle of a building and stand on the
	 * building.
	 * 
	 * The map should be remembered as the current one. Hence,
	 * {@link #getBuildingCoordinates()}, {@link #getLeftGorillaCoordinate()}
	 * and {@link #getRightGorillaCoordinate()} should after an invocation of
	 * this method return the values of the newly created map.
	 * 
	 * The wind should not blow at all in the map.
	 * 
	 * @param frameWidth
	 *            the width of the frame/screen of the gorillas game
	 * @param frameHeight
	 *            the height of the frame/screen of the gorillas game
	 */
	public void createRandomMap(int frameWidth, int frameHeight,
			int gorillaWidth, int gorillaHeight) {
		StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();
		skyline = new Skyline(entityManager, Gorillas.GAMEPLAYSTATE, 8, false);
		skyline.setFrameHeight(frameHeight);
		skyline.setFrameWidth(frameWidth);
		skyline.setGorillaWidth(gorillaWidth);
		skyline.setGorillaHeight(gorillaHeight);
		
		Gorilla g1 = new Gorilla("g1");
		g1.setImageHeight(gorillaHeight);
		g1.setImageWidth(gorillaWidth);
		
		Gorilla g2 = new Gorilla("g2");
		g2.setImageHeight(gorillaHeight);
		g2.setImageWidth(gorillaWidth);
				
		skyline.createSkyline();
		
		g1.setPosition(skyline.randomBuildingForPlayer(0));
		//entityManager.addEntity(Gorillas.GAMEPLAYSTATE, g1);
		skyline.setFigureWithWeapon(0, g1);
		
		g2.setPosition(skyline.randomBuildingForPlayer(1));
		//entityManager.addEntity(Gorillas.GAMEPLAYSTATE, g2);
		skyline.setFigureWithWeapon(1, g2);
	}

	/**
	 * creates a map, which is NOT RANDOM based on the given parameters
	 * 
	 * @param paneWidth
	 *            the width of the frame/window/pane of the game
	 * @param paneHeight
	 *            the height of the frame/window/pane of the game
	 * @param yOffsetCity
	 *            the top y offset of the city
	 * @param buildingCoordinates
	 *            the building coordinates of the city skyline
	 * @param leftGorillaCoordinate
	 *            the coordinate of the left gorilla
	 * @param rightGorillaCoordinate
	 *            the coordinate of the right gorilla
	 */
	public void createCustomMap(int paneWidth, int paneHeight, int yOffsetCity,
			ArrayList<Vector2f> buildingCoordinates,
			Vector2f leftGorillaCoordinate, Vector2f rightGorillaCoordinate) {
		StateBasedEntityManager entityManager = StateBasedEntityManager.getInstance();
		skyline = new Skyline(entityManager, Gorillas.GAMEPLAYSTATE, buildingCoordinates.size(), false);
		skyline.setFrameHeight(paneHeight);
		skyline.setFrameWidth(paneWidth);
		skyline.setyOffsetCity(yOffsetCity);
		skyline.createSkyline(buildingCoordinates);

		
		Gorilla g1 = new Gorilla("g1");
		Gorilla g2 = new Gorilla("g2");
		
		g1.setPosition(leftGorillaCoordinate);
		entityManager.addEntity(Gorillas.GAMEPLAYSTATE, g1);
		skyline.setFigureWithWeapon(0, g1);
		
		g2.setPosition(rightGorillaCoordinate);
		entityManager.addEntity(Gorillas.GAMEPLAYSTATE, g2);
		skyline.setFigureWithWeapon(1, g2);
	}

	/**
	 * the current, which was created with {@link #createRandomMap(int,int,int,int)} of
	 * {@link #createCustomMap(int,int,int,ArrayList,Vector2f,Vector2f)}
	 * should be set as current map in the game, if the game is in GamePlayState
	 */
	public void startCurrrentMap() {
		if(gorillas.getCurrentStateID() == Gorillas.GAMEPLAYSTATE) {
			GameplayState state = (GameplayState) gorillas.getCurrentState();
			state.setSkyline(skyline);
		} 
	}

	/**
	 * should return the building coordinates of the current map
	 * 
	 * @return all the upper left corner's coordinates of the buildings of the
	 *         current map, ordered from left to right
	 */
	public ArrayList<Vector2f> getBuildingCoordinates() {
		return skyline.getBuildingLeftUpperCornerCoordinates();
	}

	/**
	 * should return the coordinate of the left gorilla in the current map
	 * 
	 * @return the center coordinate of the left gorilla
	 */
	public Vector2f getLeftGorillaCoordinate() {
		return skyline.getFigureWithWeapon(0).getPosition();
	}

	/**
	 * should return the coordinate of the right gorilla in the current map
	 * 
	 * @return the center coordinate of the right gorilla
	 */
	public Vector2f getRightGorillaCoordinate() {
		return skyline.getFigureWithWeapon(1).getPosition();
	}

	/**
	 * should return the frameWidth, which was given to create the current map
	 * 
	 * @return the frameWidth which was used to create the current map
	 */
	public float getMapFrameWidth() {
		return skyline.getFrameWidth();
	}

	/**
	 * should return the frameHeight, which was given to create the current map
	 * 
	 * @return the frameHeight which was used to create the current map
	 */
	public float getMapFrameHeight() {
		return skyline.getFrameHeight();
	}

	/**
	 * should return the gorillaHeight, which was given to create the current
	 * map
	 * 
	 * @return the gorillaHeight which was used to create the current map
	 */
	public float getGorillaHeight() {
		return skyline.getGorillaHeight();
	}

	/**
	 * should return the gorillaWidth, which was given to create the current map
	 * 
	 * @return the gorillaWidth which was used to create the current map
	 */
	public float getGorillaWidth() {
		return skyline.getGorillaWidth();
	}

	/**
	 * adds a highscore to the highscore list
	 * 
	 * @param name
	 *            the name of the player
	 * @param numberOfRounds
	 *            the number of rounds played
	 * @param roundsWon
	 *            the number of rounds the player has one
	 * @param bananasThrown
	 *            the number of bananas the player has thrown
	 */
	public void addHighscore(String name, int numberOfRounds, int roundsWon,
			int bananasThrown) {
		/* ALTE ABER HALBSWEGS TAUGLICHE IMPLEMENTIERUNG
		Player p = new Player(name);
		p.setRoundsPlayed(numberOfRounds);
		p.setRoundsWon(roundsWon);
		p.setNumberOfThrownObjects(bananasThrown);
		PlayerList.savePlayer(p);
		*/
		
		Player p = PlayerList.getPlayer(name);
		if(p == null) {
			p = new Player(name);
			p.setRoundsPlayed(numberOfRounds);
			p.setRoundsWon(roundsWon);
			p.setNumberOfThrownObjects(bananasThrown);
			PlayerList.savePlayer(p);
		} else {
			p.setRoundsPlayed(p.getRoundsPlayed() + numberOfRounds);
			p.setRoundsWon(p.getRoundsWon() + roundsWon);
			p.setNumberOfThrownObjects(p.getNumberOfThrownObjects() + bananasThrown);
			PlayerList.savePlayer(p);
		}
		
	}

	/**
	 * Resets the highscores. Alle entries are deleted and @see
	 * {@link #getHighscoreCount()} should return 0.
	 */
	public void resetHighscore() {
		PlayerList.deleteAllPlayers();
	}

	/**
	 * Returns the numnber of highscore entries currently stored.
	 * 
	 * @return number of highscore entries
	 */
	public int getHighscoreCount() {
		PlayerList list = PlayerList.restorePlayerList();
		return list.size();
	}

	/**
	 * Returns the player name of the highscore entry at the passed position.
	 * The best highscore should be at position 0. See the specification in the
	 * task assignment for the definition of best highscore. Positions that are
	 * invalid should return null.
	 * 
	 * @param position
	 *            position of the highscore entry
	 * @return playername of the highscore entry at the passed position or null
	 *         if position is invalid
	 */
	public String getNameAtHighscorePosition(int position) {
		Player p = PlayerList.getPositionOfHighscore(position);
		if(p == null) {
			return null;
		} else {
			return p.getUsername();
		}
	}

	/**
	 * Returns the number of rounds played in total of the highscore entry a the
	 * passed position The best highscore should be at position 0. See the
	 * specification in the task assignment for the definition of best
	 * highscore. Positions that are invalid should return -1.
	 * 
	 * @param position
	 *            position of the highscore entry
	 * @return number of rounds played in total of the highscore entry at the
	 *         passed position or -1 if position is invalid
	 */
	public int getRoundsPlayedAtHighscorePosition(int position) {
		Player p = PlayerList.getPositionOfHighscore(position);
		if(p == null) {
			return -1;
		} else {
			return p.getRoundsPlayed();
		}
	}

	/**
	 * Returns the number of rounds won of the highscore entry a the passed
	 * position The best highscore should be at position 0. See the
	 * specification in the task assignment for the definition of best
	 * highscore. Positions that are invalid should return -1.
	 * 
	 * @param position
	 *            position of the highscore entry
	 * @return number of rounds won of the highscore entry at the passed
	 *         position or -1 if position is invalid
	 */
	public int getRoundsWonAtHighscorePosition(int position) {
		Player p = PlayerList.getPositionOfHighscore(position);
		if(p == null) {
			return -1;
		} else {
			return p.getRoundsWon();
		}
	}

	/**
	 * Returns the percentage of rounds won of the highscore entry a the passed
	 * position The best highscore should be at position 0. See the
	 * specification in the task assignment for the definition of best
	 * highscore. Positions that are invalid should return -1.
	 * 
	 * @param position
	 *            position of the highscore entry
	 * @return percentage of rounds won of the highscore entry at the passed
	 *         position or -1 if position is invalid
	 */
	public int getPercentageWonAtHighscorePosition(int position) {
		Player p = PlayerList.getPositionOfHighscore(position);
		if(p == null) {
			return -1;
		} else {
			return p.getPercentageWon();
		}
	}

	/**
	 * Returns the mean accuracy of the highscore entry a the passed position
	 * The best highscore should be at position 0. See the specification in the
	 * task assignment for the definition of best highscore. Positions that are
	 * invalid should return -1.
	 * 
	 * @param position
	 *            position of the highscore entry
	 * @return mean accuracy of the highscore entry at the passed position or -1
	 *         if position is invalid
	 */
	public double getMeanAccuracyAtHighscorePosition(int position) {
		Player p = PlayerList.getPositionOfHighscore(position);
		if(p == null) {
			return -1;
		} else {
			return p.getThrowsForAHit();
		}
	}

	/**
	 * if the game is in the GamePlayState, this method should return the
	 * current score of player one
	 * 
	 * @return the current score of player one or -1 if the game is not in the
	 *         GamePlayState
	 */
	public int getPlayer1Score() {
		if(gorillas.getCurrentStateID() == Gorillas.GAMEPLAYSTATE) {
			GameplayState state = (GameplayState) gorillas.getCurrentState();
			return state.getPlayer(0).getNumberOfHits();
		} else {
			return -1;
		}
	}

	/**
	 * if the game is in the GamePlayState, this method should return the
	 * current score of player two
	 * 
	 * @return the current score of player two or -1 if the game is not in the
	 *         GamePlayState
	 */
	public int getPlayer2Score() {
		if(gorillas.getCurrentStateID() == Gorillas.GAMEPLAYSTATE) {
			GameplayState state = (GameplayState) gorillas.getCurrentState();
			return state.getPlayer(1).getNumberOfHits();
		} else {
			return -1;
		}
	}

	/**
	 * if the game is in the GamePlayState, this method should return whether it
	 * is the turn of player one
	 * 
	 * If it is the turn of a player is decided on the fact if the player is
	 * currently able to parameterize a shot.
	 * 
	 * @return true if it is the turn of player one, false if it is the turn of
	 *         player two or the game is not in GamePlayState or it is not the
	 *         turn of anyone
	 */
	public boolean isPlayer1Turn() {
		if(gorillas.getCurrentStateID() == Gorillas.GAMEPLAYSTATE) {
			GameplayState state = (GameplayState) gorillas.getCurrentState();
			if(state.getCurrentPlayer().getArrayIndex() == 0 && state.throwForm.isVisible()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * if the game is in the GamePlayState, this method should return whether it
	 * is the turn of player two
	 * 
	 * If it is the turn of a player is decided on the fact if the player is
	 * currently able to parameterize a shot.
	 * 
	 * @return true if it is the turn of player two, false if it is the turn of
	 *         player one or the game is not in GamePlayState or it is not the
	 *         turn of anyone
	 */
	public boolean isPlayer2Turn() {
		if(gorillas.getCurrentStateID() == Gorillas.GAMEPLAYSTATE) {
			GameplayState state = (GameplayState) gorillas.getCurrentState();
			if(state.getCurrentPlayer().getArrayIndex() == 1 && state.throwForm.isVisible()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}