package com.xyz.assessment.application;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xyz.assessment.domain.Game;
import com.xyz.assessment.domain.TurnResult;
import com.xyz.assessment.exception.UnrecognizedObjectException;
import com.xyz.assessment.objectset.ObjectSetHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xyz.assessment.domain.Turn;
import com.xyz.assessment.exception.GameFinishedException;

public class GameManager {

	private final Logger logger = LoggerFactory.getLogger(GameManager.class);

	private static GameManager instance;

	private final Map<Integer /* gameId */, Game> allGames = new HashMap<Integer, Game>();

	/**
	 * Should only be used in test cases.
	 * 
	 * @param forTestCaseOnly
	 */
	public GameManager(boolean forTestCaseOnly) {

	}

	/**
	 * Suppressed default constructor, use {@link #getInstance()}.
	 */
	private GameManager() {

	}

	/**
	 * @return The singleton instance of this class.
	 */
	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}

	/**
	 * Checks if the given turnAmount is valid by calling
	 * {@link #requireValidTurnAmount(Integer)}.
	 * 
	 * @param turnAmount
	 *            The turnAmount.
	 * @param objectSet
	 *            The objectSet.
	 * @return The gameId of the created {@link Game}
	 * @throws IllegalArgumentException
	 */
	public Integer createNewGame(int turnAmount, List<String> objectSet) throws IllegalArgumentException {
		requireValidTurnAmount(turnAmount);
		/*
		 * This will lead to errors if there are more games than
		 * Integer.MAX_VALUE
		 */
		Integer gameId = allGames.keySet().size();
		allGames.put(gameId, new Game(gameId, turnAmount, objectSet));
		logger.info("Created new game with gameId '{}': turns={}, objectSet={}", gameId, turnAmount,
				Arrays.toString(objectSet.toArray()));
		return gameId;
	}

	/**
	 * Checks if the given gameId is valid by calling
	 * {@link #requireValidGameId(Integer)}. Also checks if the game exists by
	 * calling {@link #requireGameExists(Integer)}.
	 * 
	 * @param gameId
	 *            The gameId.
	 * @return A list of all {@link Turn} instances in this {@link Game}.
	 */
	public List<Turn> getTurns(Integer gameId) {
		requireValidGameId(gameId);
		requireGameExists(gameId);
		List<Turn> turns = allGames.get(gameId).getAllTurns();
		logger.info("Getting turns for gameId '{}': {}", gameId, turns);
		return turns;
	}

	/**
	 * Checks if the given gameId is valid by calling
	 * {@link #requireValidGameId(Integer)}. Also checks if the game exists by
	 * calling {@link #requireGameExists(Integer)}.
	 * 
	 * If so, returns the {@link Game} instance for the given gameId from
	 * {@link #allGames}.
	 * 
	 * @param gameId
	 *            The gameId.
	 * @return The {@link Game} instance for the given gameId.
	 * @throws IllegalArgumentException
	 *             In case the gameId is not valid or the Game for the gameId
	 *             does not exist.
	 */
	public Game getGame(Integer gameId) throws IllegalArgumentException {
		requireValidGameId(gameId);
		requireGameExists(gameId);
		Game game = allGames.get(gameId);
		logger.info("Returning game for gameId '{}': {}", gameId, game);
		return game;
	}

	/**
	 * Checks if the given gameId is valid by calling
	 * {@link #requireValidGameId(Integer)}. Also checks if the game exists by
	 * calling {@link #requireGameExists(Integer)}.
	 * 
	 * If so, obtains the {@link Game} instance for the given gameId from
	 * {@link #allGames} and checks if the game is already finished. If yes, a
	 * {@link GameFinishedException} will be thrown. If not, a new {@link Turn}
	 * is added to the game by calling {@link #addTurnToGame(Game, String)}.
	 * 
	 * @param gameId
	 *            The gameId.
	 * @param playerObject
	 * @return
	 * @throws GameFinishedException
	 *             In case {@link Game#isFinished()} returns true.
	 * @throws IllegalArgumentException
	 *             In case the gameId is not valid or the Game for the gameId
	 *             does not exist.
	 */
	public Turn addTurn(Integer gameId, String playerObject) throws GameFinishedException, IllegalArgumentException {
		requireValidGameId(gameId);
		requireGameExists(gameId);
		Game game = allGames.get(gameId);
		requireGameIsNotFinished(game);
		Turn turn = addTurnToGame(game, playerObject);
		logger.info("added Turn to gameId '{}': {}, {}", gameId, turn, game);
		return turn;
	}

	/**
	 * Does a random turn for the bot by calling
	 * {@link GameBot#getRandomObject(List)} and getting the result by calling
	 * {@link #determineWinner(List, String, String)}. Then creates a
	 * {@link Turn} instance, fills it with the objects for player, bot and the
	 * game result and adds it to the {@link Game} by calling
	 * {@link Game#addTurn(Turn)}.
	 * 
	 * @param game
	 *            The game where the turn shall be added.
	 * @param playerObject
	 *            The object picked by the player.
	 * @return The new {@link Turn} instance.
	 * @throws UnrecognizedObjectException
	 *             In case the player chose an object that is not specified in
	 *             {@link ObjectSetHolder}.
	 */
	private Turn addTurnToGame(Game game, String playerObject) throws UnrecognizedObjectException {
		String botObject = GameBot.getRandomObject(game.getObjectSet());
		TurnResult result = determineWinner(game.getObjectSet(), playerObject, botObject);
		Turn turn = new Turn(playerObject, botObject, result);
		game.addTurn(turn);
		return turn;
	}

	/**
	 * Determines the winner by first checking if the player has won, then
	 * checking if the bot has won. If none of them has won, it's a draw.
	 * 
	 * @see GameRules#isWin(String, String, List)
	 * @param objectSet
	 *            The objectSet of the game context.
	 * @param playerObject
	 *            The object the player has chosen.
	 * @param botObject
	 *            The object the bot has chosen.
	 * @return A {@link TurnResult} mapping for the outcome of the game.
	 * @throws UnrecognizedObjectException
	 *             In case the playerObject or the botObject are not specified
	 *             in {@link ObjectSetHolder}.
	 */
	private TurnResult determineWinner(List<String> objectSet, String playerObject, String botObject)
			throws UnrecognizedObjectException {
		boolean playerWin = GameRules.isWin(playerObject, botObject, objectSet);
		boolean botWin = GameRules.isWin(botObject, playerObject, objectSet);
		TurnResult result;
		if (playerWin) {
			result = TurnResult.PLAYER_WIN;
		} else if (botWin) {
			result = TurnResult.BOT_WIN;
		} else {
			result = TurnResult.DRAW;
		}
		return result;
	}

	private void requireGameExists(Integer gameId) {
		if (!allGames.containsKey(gameId)) {
			throw new IllegalArgumentException("Game with gameId '" + gameId + "' can not be found");
		}
	}

	private void requireValidGameId(Integer gameId) {
		if (gameId == null || gameId < 0) {
			throw new IllegalArgumentException("gameId must be != null and >= 0");
		}
	}

	private void requireValidTurnAmount(Integer rounds) {
		if (rounds == null || rounds <= 0) {
			throw new IllegalArgumentException("rounds must be != null and >= 0");
		}
	}

	private void requireGameIsNotFinished(Game game) {
		if (game.isFinished()) {
			throw new GameFinishedException("The game is already finished");
		}
	}

	public Map<Integer, Game> getAllGames(boolean forTestCaseOnly) {
		return allGames;
	}
}
