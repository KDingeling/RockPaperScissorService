package com.xyz.assessment.exception;

public class UnrecognizedObjectException extends RuntimeException {

	private static final long serialVersionUID = -8317182541132639036L;

	public UnrecognizedObjectException(String msg) {
		super(msg);
	}
}
