package com.capgemini.chess.algorithms.implementation.exceptions;

public class SomethinkWithValidationOfPawnException extends InvalidMoveException{
	
	private static final long serialVersionUID = -2971960721549326442L;

	public  SomethinkWithValidationOfPawnException() {
		super("It came to last else!!!");
	}

	public SomethinkWithValidationOfPawnException(String message) {
		super("It came to last else!!! " + message);
	}
}
