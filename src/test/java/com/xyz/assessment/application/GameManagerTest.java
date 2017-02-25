package com.xyz.assessment.application;

import java.util.List;
import java.util.Map;

import com.xyz.assessment.domain.Game;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.xyz.assessment.domain.Turn;
import com.xyz.assessment.exception.GameFinishedException;
import com.xyz.assessment.objectset.ObjectSetHolder;

public class GameManagerTest {

    private GameManager gameManager;

    @Before
    public void setup() {
        gameManager = new GameManager(true);
        Assert.assertTrue(gameManager.getAllGames(true).keySet().size() == 0);
    }

    @After
    public void after() {
        gameManager = new GameManager(true);
        Assert.assertTrue(gameManager.getAllGames(true).keySet().size() == 0);
    }

    @Test
    public void testGetInstance() {
        GameManager gameManager1 = GameManager.getInstance();
        GameManager gameManager2 = GameManager.getInstance();
        Assert.assertTrue(gameManager1 == gameManager2);
    }

    @Test
    public void testCreateNewGame() {

        createTestGames(ObjectSetHolder.objectSetA, ObjectSetHolder.objectSetB);
        Map<Integer, Game> testGames = gameManager.getAllGames(true);

        Game game0 = testGames.get(0);
        Assert.assertEquals(game0.getObjectSet(), ObjectSetHolder.objectSetB);
        Assert.assertEquals(game0.getCurrentTurn(), 0);

        Game game2 = testGames.get(2);
        Assert.assertEquals(game2.getObjectSet(), ObjectSetHolder.objectSetB);
        Assert.assertEquals(game2.getCurrentTurn(), 0);

        Game game9 = testGames.get(9);
        Assert.assertEquals(game9.getObjectSet(), ObjectSetHolder.objectSetA);
        Assert.assertEquals(game9.getCurrentTurn(), 0);
    }

    private void createTestGames(List<String> objectSet1, List<String> objectSet2) {
        for (int i = 1; i <= 10; i++) {
            gameManager.createNewGame(i, i % 2 == 0 ? objectSet1 : objectSet2);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNonExistentGame() {
        gameManager.getGame(1337);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateBrokenGame1() {
        gameManager.createNewGame(-1, ObjectSetHolder.objectSetA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateBrokenGame2() {
        gameManager.createNewGame(0, ObjectSetHolder.objectSetB);
    }

    @Test
    public void testAddTurn() {
        Integer gameId = gameManager.createNewGame(3, ObjectSetHolder.objectSetA);

        // -------------------- Turn 1

        Turn turn1 = gameManager.addTurn(gameId, ObjectSetHolder.ROCK);
        Assert.assertNotNull(turn1);

        List<Turn> turns = gameManager.getTurns(gameId);
        Assert.assertEquals(turns.size(), 1);

        Turn testTurn = turns.get(0);
        Assert.assertEquals(testTurn.getPlayerObject(), ObjectSetHolder.ROCK);
        Assert.assertEquals(testTurn, turn1);

        // -------------------- Turn 2

        Turn turn2 = gameManager.addTurn(gameId, ObjectSetHolder.PAPER);
        Assert.assertNotNull(turn2);

        List<Turn> turns2 = gameManager.getTurns(gameId);
        Assert.assertEquals(turns2.size(), 2);

        Turn testTurn2 = turns2.get(1);
        Assert.assertEquals(testTurn2.getPlayerObject(), ObjectSetHolder.PAPER);
        Assert.assertEquals(testTurn2, turn2);

        // -------------------- Turn 3

        Turn turn3 = gameManager.addTurn(gameId, ObjectSetHolder.SCISSOR);
        Assert.assertNotNull(turn3);

        List<Turn> turns3 = gameManager.getTurns(gameId);
        Assert.assertEquals(turns3.size(), 3);

        Turn testTurn3 = turns3.get(2);
        Assert.assertEquals(testTurn3.getPlayerObject(), ObjectSetHolder.SCISSOR);
        Assert.assertEquals(testTurn3, turn3);
    }

    @Test(expected = GameFinishedException.class)
    public void testAddTurnOnFinishedGame() {
        Integer gameId = gameManager.createNewGame(1, ObjectSetHolder.objectSetA);
        gameManager.addTurn(gameId, ObjectSetHolder.ROCK);
        gameManager.addTurn(gameId, ObjectSetHolder.SCISSOR);
        gameManager.addTurn(gameId, ObjectSetHolder.PAPER);
    }
}
