package com.capgemini.chess.algorithms.data.generated;

import java.util.ArrayList;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.implementation.exceptions.CastlingCantHappenCauseRookAlreadyMoved;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;
import com.capgemini.chess.algorithms.implementation.exceptions.NoRookInTheCornerInCastlingException;
import com.capgemini.chess.algorithms.implementation.exceptions.OtherPieceOnRoadFromToException;

public interface PieceForm {

	boolean validMove(Move move) throws InvalidMoveException;

	boolean checkRoadFromTo(Move move, Board board)
			throws OtherPieceOnRoadFromToException,  NoRookInTheCornerInCastlingException, CastlingCantHappenCauseRookAlreadyMoved;

	ArrayList<Coordinate> giveArrayToCheckIfAnyMoveValid(Coordinate cord);
		
}
