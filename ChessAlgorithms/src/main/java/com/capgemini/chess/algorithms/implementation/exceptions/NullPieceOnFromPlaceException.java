package com.capgemini.chess.algorithms.implementation.exceptions;

public class NullPieceOnFromPlaceException extends InvalidMoveException{
	

	private static final long serialVersionUID = -7975825459579798280L;

	public  NullPieceOnFromPlaceException() {
		super("Place FROM is empty!!!");
	}
}
