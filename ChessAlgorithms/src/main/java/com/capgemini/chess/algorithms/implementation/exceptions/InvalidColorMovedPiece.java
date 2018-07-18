package com.capgemini.chess.algorithms.implementation.exceptions;

public class InvalidColorMovedPiece extends InvalidMoveException {

	private static final long serialVersionUID = 1272129883278303518L;

	public InvalidColorMovedPiece() {
		super("Invalid color moved piece!");
	}
}
