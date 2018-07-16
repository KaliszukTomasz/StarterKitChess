package com.capgemini.chess.algorithms.data.generated;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.implementation.exceptions.OtherPieceOnRoadFromToException;
import com.capgemini.chess.algorithms.implementation.exceptions.SomethinkWithValidationOfPawnException;

public class Queen implements PieceForm {

	@Override
	public boolean validMove(Move move) {
		int fromX = move.getFrom().getX();
		int toX = move.getTo().getX();
		int fromY = move.getFrom().getY();
		int toY = move.getTo().getY();

		return (Math.abs(toX - fromX) == Math.abs(toY - fromY)) || (fromX == toX && fromY != toY)
				|| (fromY == toY && fromX != toX);
	}

	@Override
	public boolean checkRoadFromTo(Move move, Board board)
			throws OtherPieceOnRoadFromToException, SomethinkWithValidationOfPawnException {

		int fromX = move.getFrom().getX();
		int toX = move.getTo().getX();
		int fromY = move.getFrom().getY();
		int toY = move.getTo().getY();

		int moveAmountX = Math.abs(toX - fromX);
		int moveAmountY = Math.abs(toY - fromY);
		boolean isItXMove = moveAmountX >= moveAmountY ? true : false;
		int directionOfMoveX = moveAmountX > 0 ? (toX - fromX) / moveAmountX : 0;
		int directionOfMoveY = moveAmountY > 0 ? (toY - fromY) / moveAmountY : 0;
		int moveDirection = isItXMove ? moveAmountX : moveAmountY;

		for (int i = 1; i < moveDirection; i++) {
			if (board.getPieceAt(new Coordinate(fromX + directionOfMoveX * i, fromY + directionOfMoveY * i)) != null) {
				throw new OtherPieceOnRoadFromToException("Exception by QUEEN!!!");
			}
		}
		return true;

	}

}
