package com.xyz.assessment.domain;

import java.util.ArrayList;
import java.util.List;

public class Game {

	private int gameId;
	private int turnAmount;
	private int currentTurn;
	private List<String> objectSet;
	private List<Turn> allTurns = new ArrayList<Turn>();
	private int botWinCount;
	private int playerWinCount;

	public Game(int gameId, int turnAmount, List<String> objectSet) {
		this.gameId = gameId;
		this.turnAmount = turnAmount;
		this.objectSet = objectSet;
	}

	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public int getTurnAmount() {
		return turnAmount;
	}

	public void setBotWinCount(int botWinCount) {
		this.botWinCount = botWinCount;
	}

	public void setPlayerWinCount(int playerWinCount) {
		this.playerWinCount = playerWinCount;
	}

	public boolean isFinished() {
		return currentTurn == turnAmount;
	}

	public void incrementCurrentTurn() {
		currentTurn++;
	}

	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	public List<String> getObjectSet() {
		return objectSet;
	}

	public void setObjectSet(List<String> objectSet) {
		this.objectSet = objectSet;
	}

	public List<Turn> getAllTurns() {
		return allTurns;
	}

	public void setAllTurns(List<Turn> allTurns) {
		this.allTurns = allTurns;
	}

	public void setTurnAmount(int turnAmount) {
		this.turnAmount = turnAmount;
	}

	public void addTurn(Turn turn) {
		if (TurnResult.BOT_WIN.equals(turn.getResult())) {
			botWinCount++;
		} else if (TurnResult.PLAYER_WIN.equals(turn.getResult())) {
			playerWinCount++;
		} else {
			// draw
			botWinCount++;
			playerWinCount++;
		}
		allTurns.add(turn);
		currentTurn++;
	}

	public int getBotWinCount() {
		return botWinCount;
	}

	public int getPlayerWinCount() {
		return playerWinCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allTurns == null) ? 0 : allTurns.hashCode());
		result = prime * result + botWinCount;
		result = prime * result + currentTurn;
		result = prime * result + gameId;
		result = prime * result + ((objectSet == null) ? 0 : objectSet.hashCode());
		result = prime * result + playerWinCount;
		result = prime * result + turnAmount;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (allTurns == null) {
			if (other.allTurns != null)
				return false;
		} else if (!allTurns.equals(other.allTurns))
			return false;
		if (botWinCount != other.botWinCount)
			return false;
		if (currentTurn != other.currentTurn)
			return false;
		if (gameId != other.gameId)
			return false;
		if (objectSet == null) {
			if (other.objectSet != null)
				return false;
		} else if (!objectSet.equals(other.objectSet))
			return false;
		if (playerWinCount != other.playerWinCount)
			return false;
		if (turnAmount != other.turnAmount)
			return false;
		return true;
	}

	public boolean equalsWithoutRandomFields(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (currentTurn != other.currentTurn)
			return false;
		if (gameId != other.gameId)
			return false;
		if (objectSet == null) {
			if (other.objectSet != null)
				return false;
		} else if (!objectSet.equals(other.objectSet))
			return false;
		if (turnAmount != other.turnAmount)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Game [gameId=" + gameId + ", turnAmount=" + turnAmount + ", currentTurn=" + currentTurn + ", objectSet="
				+ objectSet + ", allTurns=" + allTurns + ", botWinCount=" + botWinCount + ", playerWinCount="
				+ playerWinCount + "]";
	}

}
