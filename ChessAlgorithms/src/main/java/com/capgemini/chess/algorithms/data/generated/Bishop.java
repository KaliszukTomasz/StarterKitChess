package com.capgemini.chess.algorithms.data.generated;

import java.util.ArrayList;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.implementation.exceptions.OtherPieceOnRoadFromToException;

public class Bishop implements PieceForm {

	@Override
	public boolean validMove(Move move) {
		int fromX = move.getFrom().getX();
		int toX = move.getTo().getX();
		int fromY = move.getFrom().getY();
		int toY = move.getTo().getY();

		return Math.abs(toX - fromX) == Math.abs(toY - fromY);
	}

	@Override
	public boolean checkRoadFromTo(Move move, Board board) throws OtherPieceOnRoadFromToException {
		int fromX = move.getFrom().getX();
		int toX = move.getTo().getX();
		int fromY = move.getFrom().getY();
		int toY = move.getTo().getY();

		int moveAmount = Math.abs(toX - fromX);
		int directionOfMoveX = (toX - fromX) / moveAmount;
		int directionOfMoveY = (toY - fromY) / moveAmount;

		for (int i = 1; i < moveAmount; i++) {
			if (board.getPieceAt(new Coordinate(fromX + directionOfMoveX * i, fromY + directionOfMoveY * i)) != null) {
				throw new OtherPieceOnRoadFromToException();
			}
		}
		return true;
	}

	@Override
	public ArrayList<Coordinate> giveArrayToCheckIfAnyMoveValid(Coordinate cord) {
		int fromX = cord.getX();
		int fromY = cord.getY();
		ArrayList<Coordinate> arrayList = new ArrayList<>();
		arrayList.add(new Coordinate(fromX + 1, fromY + 1));
		arrayList.add(new Coordinate(fromX - 1, fromY + 1));
		arrayList.add(new Coordinate(fromX - 1, fromY - 1));
		arrayList.add(new Coordinate(fromX + 1, fromY - 1));

		return arrayList;

	}
}
