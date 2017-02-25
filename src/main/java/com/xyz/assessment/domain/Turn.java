package com.xyz.assessment.domain;

public class Turn {

	private String playerObject;
	private String botObject;
	private TurnResult result;
	private String turnResultDescription;

	public Turn(String playerObject, String botObject, TurnResult result) {
		this.playerObject = playerObject;
		this.botObject = botObject;
		this.result = result;
		this.turnResultDescription = result.getDescription();
	}

	public String getPlayerObject() {
		return playerObject;
	}

	public void setPlayerObject(String playerObject) {
		this.playerObject = playerObject;
	}

	public String getBotObject() {
		return botObject;
	}

	public void setBotObject(String botObject) {
		this.botObject = botObject;
	}

	public TurnResult getResult() {
		return result;
	}

	public void setResult(TurnResult result) {
		this.result = result;
	}

	public String getTurnResultDescription() {
		return turnResultDescription;
	}

	@Override
	public String toString() {
		return "Turn [playerObject=" + playerObject + ", botObject=" + botObject + ", result=" + result
				+ ", turnResultDescription=" + turnResultDescription + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((botObject == null) ? 0 : botObject.hashCode());
		result = prime * result + ((playerObject == null) ? 0 : playerObject.hashCode());
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((turnResultDescription == null) ? 0 : turnResultDescription.hashCode());
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
		Turn other = (Turn) obj;
		if (botObject == null) {
			if (other.botObject != null)
				return false;
		} else if (!botObject.equals(other.botObject))
			return false;
		if (playerObject == null) {
			if (other.playerObject != null)
				return false;
		} else if (!playerObject.equals(other.playerObject))
			return false;
		if (result != other.result)
			return false;
		if (turnResultDescription == null) {
			if (other.turnResultDescription != null)
				return false;
		} else if (!turnResultDescription.equals(other.turnResultDescription))
			return false;
		return true;
	}

}
