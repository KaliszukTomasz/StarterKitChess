package com.capgemini.chess.algorithms.implementation.exceptions;

public class KingWillBeBeatenDuringCastling extends InvalidMoveException {

	private static final long serialVersionUID = -3256416505450077169L;

	public KingWillBeBeatenDuringCastling() {
		super("King will be beaten during castling!");
	}

}