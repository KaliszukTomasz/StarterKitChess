package com.capgemini.chess.algorithms.data.generated;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.implementation.exceptions.OtherPieceOnRoadFromToException;

public class Knight implements PieceForm {

	@Override
	public boolean validMove(Move move) {
		int fromX = move.getFrom().getX();
		int toX = move.getTo().getX();
		int fromY = move.getFrom().getY();
		int toY = move.getTo().getY();

		return ((Math.abs(fromX - toX) == 1 && Math.abs(fromY - toY) == 2)
				|| (Math.abs(fromX - toX) == 2 && Math.abs(fromY - toY) == 1));
	}

	@Override
	public boolean checkRoadFromTo(Move move, Board board) throws OtherPieceOnRoadFromToException {
		return true;
	}

}
