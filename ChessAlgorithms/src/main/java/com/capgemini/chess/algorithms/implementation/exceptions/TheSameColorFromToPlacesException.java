package com.capgemini.chess.algorithms.implementation.exceptions;

public class TheSameColorFromToPlacesException extends InvalidMoveException {

	private static final long serialVersionUID = 7139042154529486942L;

	public TheSameColorFromToPlacesException() {
		super("Place TO and FROM are the same color!!!");
	}

}