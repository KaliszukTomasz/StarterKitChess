package com.capgemini.chess.algorithms.data.generated;

import java.util.ArrayList;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.implementation.exceptions.CastlingCantHappenCauseRookAlreadyMoved;
import com.capgemini.chess.algorithms.implementation.exceptions.NoRookInTheCornerInCastlingException;
import com.capgemini.chess.algorithms.implementation.exceptions.OtherPieceOnRoadFromToException;

public class King implements PieceForm {

	@Override
	public boolean validMove(Move move) {
		int fromX = move.getFrom().getX();
		int toX = move.getTo().getX();
		int fromY = move.getFrom().getY();
		int toY = move.getTo().getY();

		if (MoveType.CASTLING == move.getType()) {
			return true;
		}
		return (Math.abs(fromX - toX) <= 1 && Math.abs(fromY - toY) <= 1);
	}

	@Override 
	public boolean checkRoadFromTo(Move move, Board board)
			throws OtherPieceOnRoadFromToException, NoRookInTheCornerInCastlingException, CastlingCantHappenCauseRookAlreadyMoved {
		if (MoveType.CASTLING == move.getType()) {
			int directionX = (move.getTo().getX() - move.getFrom().getX()) / 2;
			if (directionX > 0) {
				if (board.getPieceAt(new Coordinate(7, move.getFrom().getY())) == null ? true
						: PieceType.ROOK != board.getPieceAt(new Coordinate(7, move.getFrom().getY())).getType()) {
					throw new NoRookInTheCornerInCastlingException();
				}
			} else {
				if (board.getPieceAt(new Coordinate(0, move.getFrom().getY())) == null ? true
						: PieceType.ROOK != board.getPieceAt(new Coordinate(0, move.getFrom().getY())).getType()) {
					throw new NoRookInTheCornerInCastlingException();
				}
			}

			for (Move moveInHistory : board.getMoveHistory()) {
				if (directionX > 0) {
					if (moveInHistory.getFrom().getX() == 7 && move.getFrom().getY() == moveInHistory.getFrom().getY()
							&& moveInHistory.getMovedPiece().getType() == PieceType.ROOK) {
						throw new CastlingCantHappenCauseRookAlreadyMoved();
					}
				} else {
					if (moveInHistory.getFrom().getX() == 0
							&& moveInHistory.getMovedPiece().getType() == PieceType.ROOK) {
						throw new CastlingCantHappenCauseRookAlreadyMoved();
					}
				}
			}
			if (board.getPieceAt(new Coordinate(move.getFrom().getX() + directionX, move.getFrom().getY())) == null) {
				return true;
			}
			return false;
		} else {
			return true;
		}
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
