package com.capgemini.chess.algorithms.implementation.exceptions;

public class KingMovedAlreadyAndCanstlingIsImpossible extends InvalidMoveException {

	private static final long serialVersionUID = 8347772002180026620L;

	public KingMovedAlreadyAndCanstlingIsImpossible() {
		super("King already moved!");
	}

}