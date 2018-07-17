package com.capgemini.chess.algorithms.implementation.exceptions;

public class CoordinatesNotOnBoardException extends InvalidMoveException {

	private static final long serialVersionUID = 3637038467283044429L;

	public CoordinatesNotOnBoardException() {
		super("Coords <0 or >7!!!");
	}


}
