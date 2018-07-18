package com.capgemini.chess.algorithms.data.generated;

import java.util.ArrayList;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.implementation.exceptions.OtherPieceOnRoadFromToException;

public class Pawn implements PieceForm {

	@Override
	public boolean validMove(Move move)  {
		int fromX = move.getFrom().getX();
		int toX = move.getTo().getX();
		int fromY = move.getFrom().getY();
		int toY = move.getTo().getY();
		boolean enPassant = (move.getType() == MoveType.EN_PASSANT);
		boolean captureOrEnPassant = (move.getType() == MoveType.CAPTURE)|| (move.getType() == MoveType.EN_PASSANT);
		boolean firstMove = ((move.getMovedPiece().getColor() == Color.WHITE && move.getFrom().getY() == 1)
				|| (move.getMovedPiece().getColor() == Color.BLACK && move.getFrom().getY() == 6));
		if (validDirection(fromY, toY, move.getMovedPiece().getColor())) {
			if (enPassant) {
				return true;
			}

			if (firstMove && !captureOrEnPassant) {
				return ((((Math.abs(toY - fromY) == 2) || Math.abs(toY - fromY) == 1)) && Math.abs(toX - fromX) == 0);

			} else if (!firstMove && !captureOrEnPassant) {
				return (Math.abs(toY - fromY) == 1 && Math.abs(toX - fromX) == 0);
			} else if (captureOrEnPassant) {
				return (Math.abs(toX - fromX) == 1 && Math.abs(toY - fromY) == 1);
			} else {
				return false;
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
			throws OtherPieceOnRoadFromToException {

		int fromX = move.getFrom().getX();
		int fromY = move.getFrom().getY();
		int toY = move.getTo().getY();

		int moveAmount = Math.abs(toY - fromY);
		int directionOfMoveY = (toY - fromY) / moveAmount;

		if (move.getType() == MoveType.EN_PASSANT) {
			return true;
		}

		if (moveAmount == 2) {
			if (board.getPieceAt(new Coordinate(fromX, fromY + directionOfMoveY)) == null) {
				return true;
			} else {
				throw new OtherPieceOnRoadFromToException();
			}
		}

		else if (moveAmount == 1) {
			return true;
		}
		return false; 
	}

	@Override
	public ArrayList<Coordinate> giveArrayToCheckIfAnyMoveValid(Coordinate cord) {
		int fromX = cord.getX();
		int fromY = cord.getY();

		ArrayList<Coordinate> arrayList = new ArrayList<>();
		arrayList.add(new Coordinate(fromX, fromY + 2));
		arrayList.add(new Coordinate(fromX, fromY + 1));
		arrayList.add(new Coordinate(fromX - 1, fromY + 1));
		arrayList.add(new Coordinate(fromX + 1, fromY + 1));
		arrayList.add(new Coordinate(fromX, fromY - 2));
		arrayList.add(new Coordinate(fromX, fromY - 1));
		arrayList.add(new Coordinate(fromX - 1, fromY - 1));
		arrayList.add(new Coordinate(fromX + 1, fromY - 1));

		return arrayList;
	}
}
