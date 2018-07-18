package com.capgemini.chess.algorithms.implementation.exceptions;

public class CastlingCantHappenCauseRookAlreadyMoved extends InvalidMoveException {

	private static final long serialVersionUID = -1664019683377075185L;

	public CastlingCantHappenCauseRookAlreadyMoved() {
		super("Rook moved already!!!");
	}

}