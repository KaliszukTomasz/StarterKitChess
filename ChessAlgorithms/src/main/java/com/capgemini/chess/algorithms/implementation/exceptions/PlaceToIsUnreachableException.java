package com.capgemini.chess.algorithms.implementation.exceptions;

public class PlaceToIsUnreachableException extends InvalidMoveException {

	private static final long serialVersionUID = -3540028449450920518L;

	public PlaceToIsUnreachableException() {
		super("Place TO is unreachable!!!");
	}

	public PlaceToIsUnreachableException(String message) {
		super("Place TO is unreachable!!! " + message);
	}
}