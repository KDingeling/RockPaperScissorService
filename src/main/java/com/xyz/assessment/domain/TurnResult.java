package com.xyz.assessment.domain;

public enum TurnResult {
	PLAYER_WIN("Player wins"), BOT_WIN("Bot wins"), DRAW("Draw");

	private String description;

	private TurnResult(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
