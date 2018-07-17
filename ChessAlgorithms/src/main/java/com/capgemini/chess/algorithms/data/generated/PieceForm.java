package com.capgemini.chess.algorithms.data.generated;

import java.util.ArrayList;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;
import com.capgemini.chess.algorithms.implementation.exceptions.NoRookInTheCornerInCastlingException;
import com.capgemini.chess.algorithms.implementation.exceptions.OtherPieceOnRoadFromToException;
import com.capgemini.chess.algorithms.implementation.exceptions.SomethingWithValidationOfPawnException;

public interface PieceForm {

	boolean validMove(Move move) throws InvalidMoveException;

	boolean checkRoadFromTo(Move move, Board board)
			throws OtherPieceOnRoadFromToException, SomethingWithValidationOfPawnException, NoRookInTheCornerInCastlingException;

	ArrayList<Coordinate> giveArrayToCheckIfAnyMoveValid(Coordinate cord);
		
}
