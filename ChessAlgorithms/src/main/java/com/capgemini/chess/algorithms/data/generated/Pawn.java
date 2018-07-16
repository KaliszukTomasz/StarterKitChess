package com.capgemini.chess.algorithms.data.generated;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.implementation.exceptions.OtherPieceOnRoadFromToException;
import com.capgemini.chess.algorithms.implementation.exceptions.SomethinkWithValidationOfPawnException;

public class Pawn implements PieceForm {

	@Override
	public boolean validMove(Move move) throws SomethinkWithValidationOfPawnException {
		int fromX = move.getFrom().getX();
		int toX = move.getTo().getX();
		int fromY = move.getFrom().getY();
		int toY = move.getTo().getY();
		boolean capture = (move.getType() == MoveType.CAPTURE);
		boolean firstMove = ((move.getMovedPiece().getColor() == Color.WHITE && move.getFrom().getY() == 1)
				|| (move.getMovedPiece().getColor() == Color.BLACK && move.getFrom().getY() == 6));

		if (validDirection(fromY, toY, move.getMovedPiece().getColor())) {
			if (firstMove && !capture) {
				return ((((Math.abs(toY - fromY) == 2) || Math.abs(toY - fromY) == 1)) && Math.abs(toX - fromX) == 0);

			} else if (!firstMove && !capture) {
				return (Math.abs(toY - fromY) == 1 && Math.abs(toX - fromX) == 0);
			} else if (capture) {
				return (Math.abs(toX - fromX) == 1 && Math.abs(toY - fromY) == 1);
			} else {
				throw new SomethinkWithValidationOfPawnException();
			}

		} else {
			return false;
		}
	}

	private boolean validDirection(int fromY, int toY, Color colorToValid) {
		return (colorToValid == Color.WHITE) ? (fromY <= toY) : (toY <= fromY);
	}

	@Override
	public boolean checkRoadFromTo(Move move, Board board)
			throws OtherPieceOnRoadFromToException, SomethinkWithValidationOfPawnException {

		int fromX = move.getFrom().getX();
		int fromY = move.getFrom().getY();
		int toY = move.getTo().getY();

		int moveAmount = Math.abs(toY - fromY);
		int directionOfMoveY = (toY - fromY) / moveAmount;

		if (moveAmount == 2) {
			if (board.getPieceAt(new Coordinate(fromX, fromY + directionOfMoveY)) == null) {
				return true;
			} else {
				throw new OtherPieceOnRoadFromToException();
			}
		}

		else if (moveAmount == 1) {
			return true;
		} else {
			throw new SomethinkWithValidationOfPawnException("Somethink is valid");

		}
	}
}
