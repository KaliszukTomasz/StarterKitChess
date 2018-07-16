package com.capgemini.chess.algorithms.implementation.exceptions;

public class OtherPieceOnRoadFromToException extends InvalidMoveException {

	private static final long serialVersionUID = -4080940241380327488L;


	public OtherPieceOnRoadFromToException() {
		super("This is invalid move, cause on his road is other piece!!!");
	}

	public OtherPieceOnRoadFromToException(String message) {
		super("This is invalid move, cause on his road is other piece!!! " + message);
	}
}
