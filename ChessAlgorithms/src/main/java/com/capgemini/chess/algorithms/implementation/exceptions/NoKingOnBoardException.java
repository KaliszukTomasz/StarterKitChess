package com.capgemini.chess.algorithms.implementation.exceptions;

public class NoKingOnBoardException extends InvalidMoveException {

	
	private static final long serialVersionUID = -830153365129095256L;


	public NoKingOnBoardException() {
		super("King isn't on board!!!");
	}

	public NoKingOnBoardException(String message) {
		super("King isn't on board!!! " + message);
	}
}
