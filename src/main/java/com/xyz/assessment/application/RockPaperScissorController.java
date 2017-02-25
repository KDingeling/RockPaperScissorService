package com.xyz.assessment.application;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.xyz.assessment.domain.Game;
import com.xyz.assessment.objectset.ObjectSetHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.assessment.domain.Turn;

@RestController
public class RockPaperScissorController {

    private final Logger logger = LoggerFactory.getLogger(RockPaperScissorController.class);

    private static final String version1 = "v1";
    private static final String version2 = "v2";

    @SuppressWarnings("serial")
    private static final List<String> supportedVersions = new LinkedList<String>() {
        {
            add(version1);
            add(version2);
        }
    };

    private GameManager gameManager = GameManager.getInstance();

    @RequestMapping(method = RequestMethod.GET, path = "/" + version1 + "/games")
    public
    @ResponseBody
    Game createGameV1(@RequestParam(value = "turns") int turns) {
        Integer gameId = createGame(turns, ObjectSetHolder.objectSetA);
        return gameManager.getGame(gameId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/" + version2 + "/games")
    public
    @ResponseBody
    Game createGameV2(@RequestParam(value = "turns") int turns) {
        Integer gameId = createGame(turns, ObjectSetHolder.objectSetB);
        return gameManager.getGame(gameId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{versionNo}/games/{gameId}/turns")
    public
    @ResponseBody
    List<Turn> queryTurns(
            @PathVariable("versionNo") String versionNo,
            @PathVariable("gameId") Integer gameId) {
        requireValidVersionNo(versionNo);
        List<Turn> turns = gameManager.getTurns(gameId);
        return turns;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{versionNo}/games/{gameId}/status")
    public
    @ResponseBody
    Game queryStatus(@PathVariable("gameId") Integer gameId) {
        Game game = gameManager.getGame(gameId);
        return game;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{versionNo}/games/{gameId}")
    public Turn createTurn(
            @PathVariable("versionNo") String versionNo,
            @PathVariable("gameId") Integer gameId,
            @RequestParam("object") String playerObject) {
        requireValidVersionNo(versionNo);
        Turn turn = gameManager.addTurn(gameId, playerObject);
        return turn;
    }

    private Integer createGame(int turns, List<String> objectSet) {
        Integer gameId = gameManager.createNewGame(turns, objectSet);
        logger.info("created game with gameId {} and objectSet {}", gameId, objectSet);
        return gameId;
    }

    private void requireValidVersionNo(String versionNo) {
        if (!supportedVersions.contains(versionNo)) {
            throw new IllegalArgumentException(
                    "Parameter 'versionNo' must be in '" + Arrays.toString(supportedVersions.toArray()) + "'");
        }
    }
}
