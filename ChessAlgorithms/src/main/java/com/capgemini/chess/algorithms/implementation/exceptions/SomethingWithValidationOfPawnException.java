package com.capgemini.chess.algorithms.implementation.exceptions;

public class SomethingWithValidationOfPawnException extends InvalidMoveException{
	
	private static final long serialVersionUID = -2971960721549326442L;

	public  SomethingWithValidationOfPawnException() {
		super("It came to last else!!!");
	}

	public SomethingWithValidationOfPawnException(String message) {
		super("It came to last else!!! " + message);
	}
}
