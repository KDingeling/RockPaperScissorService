package com.xyz.assessment.exception;

public class GameFinishedException extends RuntimeException {

	private static final long serialVersionUID = 2533576779146362879L;

	public GameFinishedException(String msg) {
		super(msg);
	}
}
