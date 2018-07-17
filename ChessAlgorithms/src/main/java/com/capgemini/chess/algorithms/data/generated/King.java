package com.capgemini.chess.algorithms.data.generated;

import java.util.ArrayList;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.implementation.exceptions.OtherPieceOnRoadFromToException;

public class King implements PieceForm {

	@Override
	public boolean validMove(Move move) {
		int fromX = move.getFrom().getX();
		int toX = move.getTo().getX();
		int fromY = move.getFrom().getY();
		int toY = move.getTo().getY();

		return (Math.abs(fromX - toX) <= 1 && Math.abs(fromY - toY) <= 1);
	}

	@Override
	public boolean checkRoadFromTo(Move move, Board board) throws OtherPieceOnRoadFromToException {

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
		arrayList.add(new Coordinate(fromX, fromY - 1));
		arrayList.add(new Coordinate(fromX + 1, fromY));
		arrayList.add(new Coordinate(fromX, fromY + 1));
		arrayList.add(new Coordinate(fromX - 1, fromY));

		return arrayList;
	}

}
