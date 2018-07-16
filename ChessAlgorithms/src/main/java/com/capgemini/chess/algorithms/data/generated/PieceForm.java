package com.capgemini.chess.algorithms.data.generated;

import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;
import com.capgemini.chess.algorithms.implementation.exceptions.OtherPieceOnRoadFromToException;
import com.capgemini.chess.algorithms.implementation.exceptions.SomethinkWithValidationOfPawnException;

public interface PieceForm {

	boolean validMove(Move move) throws InvalidMoveException;

	boolean checkRoadFromTo(Move move, Board board)
			throws OtherPieceOnRoadFromToException, SomethinkWithValidationOfPawnException;

}
