package com.capgemini.chess.algorithms.implementation.exceptions;

public class NoRookInTheCornerInCastlingException extends InvalidMoveException {

	private static final long serialVersionUID = 6860067962696056731L;


	public NoRookInTheCornerInCastlingException() {
		super("No rook in the corner during castling!!!");
	}

}